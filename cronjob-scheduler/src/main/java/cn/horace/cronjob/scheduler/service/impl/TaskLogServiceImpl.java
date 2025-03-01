package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.commons.constants.TaskLogState;
import cn.horace.cronjob.commons.cron.CronExpression;
import cn.horace.cronjob.scheduler.adapter.TaskLogAdapter;
import cn.horace.cronjob.scheduler.bean.params.GetCronTaskLogListParams;
import cn.horace.cronjob.scheduler.bean.result.TaskLogItem;
import cn.horace.cronjob.scheduler.bean.result.TaskLogListResult;
import cn.horace.cronjob.scheduler.constants.Constants;
import cn.horace.cronjob.scheduler.constants.TaskLogExeType;
import cn.horace.cronjob.scheduler.entities.TaskEntity;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import cn.horace.cronjob.scheduler.entities.TaskLogEntityExample;
import cn.horace.cronjob.scheduler.mappers.TaskLogEntityMapper;
import cn.horace.cronjob.scheduler.service.TaskLogService;
import cn.horace.cronjob.scheduler.service.UserTenantService;
import cn.horace.cronjob.scheduler.utils.LikeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 任务日志
 * <p>
 *
 * @author Horace
 */
@Service
public class TaskLogServiceImpl implements TaskLogService {
    private static final Logger logger = LoggerFactory.getLogger(TaskLogServiceImpl.class);
    @Resource
    private TaskLogEntityMapper mapper;
    @Resource
    private CronExpression cronExpression;
    @Resource
    private GuidGenerate guidGenerate;
    @Resource
    private TaskLogAdapter taskLogAdapter;
    @Resource
    private UserTenantService userTenantService;

    /**
     * 将任务入库
     *
     * @param task              任务对象
     * @param lastExecutionTime 最新时间
     * @return
     */
    @Override
    public long addTaskLog(TaskEntity task, long lastExecutionTime) {
        TaskLogEntity taskLogEntity = this.convertToCronTaskLog(task);
        taskLogEntity.setState(TaskLogState.INITIALIZE.getValue());
        Date executionTime = this.cronExpression.getNextExecutionTime(task.getCron(), lastExecutionTime);
        // 用于计算下一次时间
        lastExecutionTime = executionTime.getTime();
        taskLogEntity.setExecutionTime(executionTime);
        this.mapper.insertSelective(taskLogEntity);
        return lastExecutionTime;
    }

    /**
     * 添加一个任务日志，并返回任务日志ID
     *
     * @param task          任务
     * @param executionTime
     * @param exeType       执行类型
     * @return
     */
    @Override
    public long addNowExecuteTaskLog(TaskEntity task, long executionTime, TaskLogExeType exeType) {
        TaskLogEntity taskLog = this.convertToCronTaskLog(task);
        Date date = new Date();
        taskLog.setExecutionTime(new Date(executionTime));
        taskLog.setCreateTime(date);
        taskLog.setModifyTime(date);
        taskLog.setExeType(exeType.getValue());
        int count = this.mapper.insertSelective(taskLog);
        if (count > 0) {
            return taskLog.getId();
        }
        return -1;
    }

    /**
     * 清理过期的任务日志
     *
     * @param maxRetainDays 最大保留的日志天数
     */
    @Override
    public void deleteExpiredTaskLogs(int maxRetainDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -maxRetainDays);
        Date date = calendar.getTime();
        TaskLogEntityExample example = new TaskLogEntityExample();
        example.or().andCreateTimeLessThan(date);
        int count = this.mapper.deleteByExample(example);
        if (count > 0) {
            logger.info("delete expired cron task log, date:{}, count:{}", DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), count);
        }
    }

    @Override
    public TaskLogEntity getTaskLog(long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键，更新选择项（不为空的字段）
     *
     * @param updateEntity 更新的实体
     * @return
     */
    @Override
    public boolean updateByPrimaryKeySelective(TaskLogEntity updateEntity) {
        updateEntity.setModifyTime(new Date());
        return this.mapper.updateByPrimaryKeySelective(updateEntity) > 0;
    }

    @Override
    public List<TaskLogEntity> getMayBeTimeoutTaskLogList(Date startDate, Date endDate) {
        TaskLogEntityExample example = new TaskLogEntityExample();
        example.or()
                .andExecutionTimeGreaterThanOrEqualTo(startDate)
                .andExecutionTimeLessThanOrEqualTo(endDate)
                .andStateEqualTo(TaskLogState.EXECUTION.getValue());
        return this.mapper.selectByExample(example);
    }

    @Override
    public TaskLogEntity generateChildTaskLog(TaskLogEntity taskLog, int page, int total) {
        TaskLogEntity entity = this.taskLogAdapter.copy(taskLog);
        long id = this.guidGenerate.genId();
        entity.setVersion(0);
        entity.setId(id);
        entity.setParentId(taskLog.getId());
        entity.setPage(page);
        entity.setTotal(total);
        int insert = this.mapper.insert(entity);
        if (insert <= 0) {
            logger.error("generate child task log error, entity:{}", entity);
        }
        logger.debug("generate child task log, entity:{}", entity);
        return entity;
    }

    @Override
    public boolean updateTotal(long id, int total) {
        TaskLogEntity entity = new TaskLogEntity();
        entity.setId(id);
        entity.setTotal(total);
        return this.mapper.updateByPrimaryKeySelective(entity) > 0;
    }

    @Override
    public List<TaskLogEntity> getTaskLogList(Date startDate, Date endDate) {
        TaskLogEntityExample example = new TaskLogEntityExample();
        TaskLogEntityExample.Criteria or = example.or();
        if (startDate != null) {
            or.andExecutionTimeGreaterThanOrEqualTo(startDate);
        }
        or.andExecutionTimeLessThan(endDate);
        return this.mapper.selectByExample(example);
    }

    /**
     * 获取待执行的任务
     *
     * @param startDate 开始时间
     * @param endDate   最大时间，获取小于等于该时间的任务
     * @param limit     获取的最大数量
     * @return
     */
    @Override
    public List<TaskLogEntity> getPendingTaskList(Date startDate, Date endDate, int limit) {
        TaskLogEntityExample example = new TaskLogEntityExample();
        example.or().andExecutionTimeGreaterThanOrEqualTo(startDate)
                .andExecutionTimeLessThanOrEqualTo(endDate)
                .andStateEqualTo(TaskLogState.INITIALIZE.getValue());

        example.or().andExecutionTimeGreaterThanOrEqualTo(startDate)
                .andExecutionTimeLessThanOrEqualTo(endDate)
                .andStateEqualTo(TaskLogState.EXECUTION_FAILED_RETRYING.getValue());

        example.setOrderByClause("execution_time asc limit " + limit);
        return this.mapper.selectByExample(example);
    }

    /**
     * 更新任务日志状态
     *
     * @param id      任务日志ID
     * @param state   任务状态
     * @param version 版本号
     * @return 返回是否更新成功
     */
    @Override
    public boolean updateTaskLogState(long id, TaskLogState state, int version) {
        return this.updateTaskLogState(id, state, version, null, null, null);
    }

    /**
     * 更新任务日志状态
     *
     * @param id              任务日志ID
     * @param state           任务状态
     * @param version         版本号
     * @param address         调度器地址，表示该任务由哪个调度器进行调度
     * @param failedReason    失败原因
     * @param executorAddress 执行器地址
     * @return 返回是否更新成功
     */
    @Override
    public boolean updateTaskLogState(long id, TaskLogState state, int version, String address, String failedReason, String executorAddress) {
        Date date = new Date();
        TaskLogEntity entity = new TaskLogEntity();
        entity.setState(state.getValue());
        entity.setVersion(version + 1);
        if (failedReason != null) {
            entity.setFailedReason(failedReason);
        }
        if (address != null) {
            entity.setSchedulerAddress(address);
        }
        if (executorAddress != null) {
            entity.setExecutorAddress(executorAddress);
        }
        if (state == TaskLogState.EXECUTION) {
            entity.setDispatchTime(date);
        }
        entity.setModifyTime(date);
        TaskLogEntityExample example = new TaskLogEntityExample();
        example.or().andIdEqualTo(id).andVersionEqualTo(version);
        int count = this.mapper.updateByExampleSelective(entity, example);
        return count > 0;
    }

    /**
     * 更新失败任务状态
     *
     * @param id                任务ID
     * @param nextExecutionTime 下次执行时间
     * @param retryCount        当前重试次数
     * @param failureReason     失败原因
     * @param version           版本号
     * @return 返回是否更新成功
     */
    @Override
    public boolean updateRetryTaskLog(long id, Date nextExecutionTime, int retryCount, String failureReason, Integer version) {
        TaskLogEntity entity = new TaskLogEntity();
        entity.setState(TaskLogState.EXECUTION_FAILED_RETRYING.getValue());
        entity.setExecutionTime(nextExecutionTime);
        entity.setRetryCount(retryCount);
        if (failureReason != null) {
            entity.setFailedReason(failureReason);
        }
        entity.setVersion(version + 1);
        entity.setModifyTime(new Date());
        TaskLogEntityExample example = new TaskLogEntityExample();
        example.or().andIdEqualTo(id).andVersionEqualTo(version);
        int count = this.mapper.updateByExampleSelective(entity, example);
        return count > 0;
    }

    /**
     * 获取任务日志列表
     *
     * @param userId 当前用户ID
     * @param params 查询参数
     * @return
     */
    @Override
    public Result<TaskLogListResult> getTaskLogList(long userId, GetCronTaskLogListParams params) {
        Result<TaskLogListResult> result = Result.success();
        TaskLogEntityExample example = new TaskLogEntityExample();

        // 条件设置
        TaskLogEntityExample.Criteria criteria = example.or();
        // 处理用户的租户权限
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (tenantIds == null || tenantIds.isEmpty()) {
            return userId == Constants.ADMIN_USER_ID ? result : Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }
        if (StringUtils.isBlank(params.getTenantId()) || Long.parseLong(params.getTenantId()) <= 0) {
            criteria.andTenantIdIn(tenantIds);
        } else {
            // 如果指定了租户ID，那么需要判断该租户ID是否属于当前用户
            long tenantId = Long.parseLong(params.getTenantId());
            if (tenantIds.contains(tenantId)) {
                criteria.andTenantIdEqualTo(tenantId);
            } else {
                // 租户参数不属于当前用户
                return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
            }
        }

        if (StringUtils.isNotBlank(params.getId())) {
            criteria.andIdEqualTo(Long.parseLong(params.getId()));
        }
        if (StringUtils.isNotBlank(params.getAppName())) {
            criteria.andAppNameLike(params.getAppName());
        }
        if (StringUtils.isNotBlank(params.getTaskId())) {
            criteria.andTaskIdEqualTo(Long.parseLong(params.getTaskId()));
        }
        if (StringUtils.isNotBlank(params.getMethod())) {
            criteria.andMethodLike(LikeUtils.toLikeString(params.getMethod()));
        }
        if (StringUtils.isNotBlank(params.getExecutorAddress())) {
            criteria.andExecutorAddressEqualTo(LikeUtils.toLikeString(params.getExecutorAddress()));
        }
        if (StringUtils.isNotBlank(params.getTag())) {
            criteria.andTagLike(LikeUtils.toLikeString(params.getTag()));
        }
        if (StringUtils.isNotBlank(params.getSchedulersAddress())) {
            criteria.andSchedulerAddressLike(LikeUtils.toLikeString(params.getSchedulersAddress()));
        }
        if (params.getState() > 0) {
            criteria.andStateEqualTo(params.getState());
        }
        if (StringUtils.isNotBlank(params.getParams())) {
            criteria.andTaskParamsLike(LikeUtils.toLikeString(params.getParams()));
        }
        if (StringUtils.isNotBlank(params.getRemark())) {
            criteria.andRemarkLike(LikeUtils.toLikeString(params.getRemark()));
        }

        String[] executionTimeRange = params.getExecutionTimeRange();
        if (executionTimeRange != null && executionTimeRange.length == 2) {
            String startTime = executionTimeRange[0];
            String endTime = executionTimeRange[1];
            try {
                Date startDate = DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm:ss");
                Date endDate = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss");
                long diff = endDate.getTime() - startDate.getTime();
                long millis = TimeUnit.HOURS.toMillis(3);
                if (diff > millis) {
                    return Result.msgCodes(MsgCodes.ERROR_QUERY_RANGE);
                }

                criteria.andExecutionTimeBetween(startDate, endDate);
            } catch (ParseException e) {
                logger.error("parse date time error, params:{}, msg:{}", params, e.getMessage(), e);
            }
        }

        TaskLogListResult cronTaskListResult = new TaskLogListResult();
        cronTaskListResult.setTotal(this.mapper.countByExample(example));
        int offset = (params.getCurrent() - 1) * params.getPageSize();
        int limit = params.getPageSize();
        example.setOrderByClause("`execution_time` desc limit " + offset + ", " + limit);
        List<TaskLogEntity> entityList = this.mapper.selectByExample(example);
        cronTaskListResult.setCurrent(params.getCurrent());
        cronTaskListResult.setPageSize(params.getPageSize());
        cronTaskListResult.setData(this.taskLogAdapter.convert(entityList));
        result.setData(cronTaskListResult);
        return result;
    }

    /**
     * 获取任务日志
     *
     * @param userId 当前用户DI
     * @param id     任务日志ID
     * @return
     */
    @Override
    public Result<TaskLogItem> getTaskLog(long userId, long id) {
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (tenantIds == null || tenantIds.isEmpty()) {
            return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }
        TaskLogEntityExample example = new TaskLogEntityExample();
        example.or().andIdEqualTo(id).andTenantIdIn(tenantIds);
        List<TaskLogEntity> list = this.mapper.selectByExampleWithBLOBs(example);
        if (list == null || list.isEmpty()) {
            return Result.success();
        }
        TaskLogItem cronTaskItem = this.taskLogAdapter.convert(list.get(0));
        return Result.success(cronTaskItem);
    }

    /**
     * 获取最新一条记录
     *
     * @param taskId   任务ID
     * @param reverse  true表示按执行时间正序 asc，false表示按执行时间倒序 desc
     * @param state    任务日志状态，如果为null则不指定状态
     * @param notState 排除的任务日志状态，如果为null则不指定状态
     * @return
     */
    @Override
    public TaskLogEntity getLastTaskLog(long taskId, boolean reverse, TaskLogState state, TaskLogState notState) {
        TaskLogEntityExample example = new TaskLogEntityExample();
        TaskLogEntityExample.Criteria criteria = example.or();
        criteria.andTaskIdEqualTo(taskId);
        if (state != null) {
            criteria.andStateEqualTo(state.getValue());
        }
        if (notState != null) {
            criteria.andStateNotEqualTo(notState.getValue());
        }
        if (reverse) {
            example.setOrderByClause("execution_time asc limit 1");
        } else {
            example.setOrderByClause("execution_time desc limit 1");
        }
        List<TaskLogEntity> entities = this.mapper.selectByExample(example);
        if (entities != null && !entities.isEmpty()) {
            return entities.get(0);
        }
        return null;
    }

    /**
     * 取消执行
     *
     * @param taskId
     */
    @Override
    public void cancelExecute(long taskId) {
        TaskLogEntity row = new TaskLogEntity();
        row.setState(TaskLogState.EXECUTION_CANCEL.getValue());
        TaskLogEntityExample example = new TaskLogEntityExample();
        example.or().andTaskIdEqualTo(taskId).andStateEqualTo(TaskLogState.INITIALIZE.getValue());
        int count = this.mapper.updateByExampleSelective(row, example);
        logger.info("cancel cron task log (initialize state), taskId:{}, count:{}", taskId, count);
    }

    /**
     * 转换为任务日志实体
     *
     * @param task 任务对象
     * @return
     */
    private TaskLogEntity convertToCronTaskLog(TaskEntity task) {
        TaskLogEntity log = new TaskLogEntity();
        log.setId(this.guidGenerate.genId());
        log.setTenantId(task.getTenantId());
        log.setAppId(task.getAppId());
        log.setTaskId(task.getId());
        log.setVersion(0);
        log.setExeType(TaskLogExeType.NORMAL.getValue());
        log.setName(task.getName());
        log.setOwner(task.getOwner());
        log.setAppName(task.getAppName());
        log.setAppDesc(task.getAppDesc());
        log.setCron(task.getCron());
        log.setTag(task.getTag());
        log.setState(TaskLogState.INITIALIZE.getValue());
        log.setMethod(task.getMethod());
        log.setTaskParams(task.getTaskParams());
        log.setRemark(task.getRemark());
        log.setRouterStrategy(task.getRouterStrategy());
        log.setExpiredStrategy(task.getExpiredStrategy());
        log.setExpiredTime(task.getExpiredTime());
        log.setMaxRetryCount(task.getMaxRetryCount());
        log.setFailureStrategy(task.getFailureStrategy());
        log.setFailureRetryInterval(task.getFailureRetryInterval());
        log.setTimeout(task.getTimeout());
        return log;
    }
}