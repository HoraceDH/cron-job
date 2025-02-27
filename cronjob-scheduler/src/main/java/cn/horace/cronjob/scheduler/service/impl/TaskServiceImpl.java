package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.bean.TaskResult;
import cn.horace.cronjob.commons.constants.*;
import cn.horace.cronjob.commons.cron.CronExpression;
import cn.horace.cronjob.scheduler.adapter.TaskAdapter;
import cn.horace.cronjob.scheduler.bean.params.ExecuteNowParams;
import cn.horace.cronjob.scheduler.bean.params.GetTaskListParams;
import cn.horace.cronjob.scheduler.bean.params.TaskRegisterParams;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.bean.result.TaskItem;
import cn.horace.cronjob.scheduler.bean.result.TaskListResult;
import cn.horace.cronjob.scheduler.constants.TaskLogExeType;
import cn.horace.cronjob.scheduler.entities.*;
import cn.horace.cronjob.scheduler.mappers.TaskEntityMapper;
import cn.horace.cronjob.scheduler.service.*;
import cn.horace.cronjob.scheduler.utils.LikeUtils;
import cn.horace.cronjob.scheduler.utils.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 任务服务类
 * <p>
 *
 * @author Horace
 */
@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Resource
    private TaskEntityMapper mapper;
    @Resource
    private TaskAdapter taskAdapter;
    @Resource
    private TenantService tenantService;
    @Resource
    private AppService appService;
    @Resource
    private UserTenantService userTenantService;
    @Resource
    private TaskLogService taskLogService;
    @Resource
    private CronExpression cronExpression;
    @Resource
    private GuidGenerate guidGenerate;

    @Override
    public Result<Void> register(List<TaskRegisterParams> params) {
        Result<Void> result = ValidationUtils.validate(params);
        if (!result.isSuccess()) {
            logger.error("task register failed, params error, result:{}, params:{}", result, params);
            return result;
        }
        TaskRegisterParams taskParams = params.get(0);
        // 获取租户、应用
        TenantEntity tenant = this.tenantService.getOrInitTenant(taskParams.getTenant());
        if (tenant == null) {
            logger.error("task register failed, not found tenant, result:{}, params:{}", result, params);
            return result;

        }
        AppEntity app = this.appService.getApp(tenant.getId(), taskParams.getAppName());
        if (app == null) {
            logger.error("task register failed, not found app, result:{}, params:{}", result, params);
            return result;
        }

        int _5minutes = (int) TimeUnit.MINUTES.toMillis(5);
        boolean success = true;
        for (TaskRegisterParams taskParam : params) {
            try {
                // 如果不存在，则入库，存在则忽略
                TaskEntityExample example = new TaskEntityExample();
                example.or().andTenantIdEqualTo(tenant.getId()).andAppIdEqualTo(app.getId()).andMethodEqualTo(taskParam.getMethod());
                List<TaskEntity> taskEntities = this.mapper.selectByExample(example);
                if (taskEntities == null || taskEntities.isEmpty()) {
                    TaskEntity entity = new TaskEntity();
                    entity.setId(this.guidGenerate.genId());
                    entity.setAppId(app.getId());
                    entity.setTenantId(tenant.getId());
                    entity.setAppName(taskParam.getAppName());
                    entity.setAppDesc(taskParam.getAppDesc());
                    entity.setName(taskParam.getName());
                    entity.setOwner(StringUtils.isBlank(taskParam.getOwner()) ? "-" : taskParam.getOwner());
                    entity.setCron(taskParam.getCron());
                    entity.setTag(taskParam.getTag());
                    entity.setRunState(TaskRunState.STOP.getValue());
                    entity.setMethod(taskParam.getMethod());
                    entity.setRemark(taskParam.getRemark());
                    entity.setRouterStrategy(taskParam.getRouterStrategy());
                    entity.setExpiredStrategy(taskParam.getExpiredStrategy());
                    entity.setExpiredTime(Math.min(taskParam.getExpiredTime(), _5minutes));
                    entity.setFailureStrategy(taskParam.getFailureStrategy());
                    entity.setMaxRetryCount(taskParam.getMaxRetryCount());
                    entity.setFailureRetryInterval(taskParam.getFailureRetryInterval());
                    entity.setTimeout(Math.min(taskParam.getTimeout(), 10000));
                    int count = this.mapper.insertSelective(entity);
                    if (count > 0) {
                        logger.info("task register success, {}", entity);
                    } else {
                        success = false;
                        logger.error("task register failed, insert error, insertCount:{}, taskParams:{}", count, taskParam);
                    }
                }
            } catch (Exception e) {
                success = false;
                logger.error("task register error, taskParams:{}, msg:{}", taskParam, e.getMessage(), e);
            }
        }

        if (!success) {
            return Result.msgCodes(MsgCodes.ERROR_SYSTEM);
        }
        return result;
    }


    /**
     * 获取任务数
     *
     * @param appId 应用ID
     * @return
     */
    @Override
    public int getTaskCount(long appId) {
        TaskEntityExample example = new TaskEntityExample();
        example.or().andAppIdEqualTo(appId);
        return (int) this.mapper.countByExample(example);
    }

    /**
     * 查询任务列表
     *
     * @param userId 当前用户ID
     * @param params 查询参数
     * @return
     */
    @Override
    public Result<TaskListResult> getTaskList(long userId, GetTaskListParams params) {
        Result<TaskListResult> result = Result.success();
        TaskEntityExample example = new TaskEntityExample();
        long id = 0;
        if (StringUtils.isNotBlank(params.getId())) {
            id = Long.parseLong(params.getId());
        }

        // 条件设置
        TaskEntityExample.Criteria criteria = example.or();
        // 处理用户的租户权限
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (tenantIds == null || tenantIds.isEmpty()) {
            return Result.success();
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

        if (id > 0) {
            criteria.andIdEqualTo(id);
        }
        if (StringUtils.isNotBlank(params.getAppName())) {
            criteria.andAppNameEqualTo(params.getAppName());
        }
        if (StringUtils.isNotBlank(params.getAppDesc())) {
            criteria.andAppDescEqualTo(LikeUtils.toLikeString(params.getAppDesc()));
        }
        if (StringUtils.isNotBlank(params.getTag())) {
            criteria.andTagLike(LikeUtils.toLikeString(params.getTag()));
        }
        if (params.getRunState() > 0) {
            criteria.andRunStateEqualTo(params.getRunState());
        }
        if (StringUtils.isNotBlank(params.getMethod())) {
            criteria.andMethodLike(LikeUtils.toLikeString(params.getMethod()));
        }
        if (StringUtils.isNotBlank(params.getRemark())) {
            criteria.andRemarkLike(LikeUtils.toLikeString(params.getRemark()));
        }

        TaskListResult taskListResult = new TaskListResult();
        taskListResult.setTotal(this.mapper.countByExample(example));
        int offset = (params.getCurrent() - 1) * params.getPageSize();
        int limit = params.getPageSize();
        example.setOrderByClause("`create_time` desc limit " + offset + ", " + limit);
        List<TaskEntity> entityList = this.mapper.selectByExample(example);
        taskListResult.setCurrent(params.getCurrent());
        taskListResult.setPageSize(params.getPageSize());
        taskListResult.setData(this.taskAdapter.convert(entityList));
        result.setData(taskListResult);
        return result;
    }

    /**
     * 查询任务详情
     *
     * @param userId 当前用户ID
     * @param id     任务ID
     * @return
     */
    @Override
    public Result<TaskItem> getTaskDetail(long userId, long id) {
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (tenantIds == null || tenantIds.isEmpty()) {
            return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }

        TaskEntityExample example = new TaskEntityExample();
        example.or().andIdEqualTo(id).andTenantIdIn(tenantIds);
        List<TaskEntity> list = this.mapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return Result.success();
        }
        TaskItem taskItem = this.taskAdapter.convert(list.get(0));
        return Result.success(taskItem);
    }

    /**
     * 更新任务运行状态
     *
     * @param userId   当前用户ID
     * @param id       任务ID
     * @param runState 运行状态
     * @return
     */
    @Override
    public Result<Void> updateRunState(long userId, long id, TaskRunState runState) {
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (tenantIds == null || tenantIds.isEmpty()) {
            return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }
        TaskEntity cronTask = this.getTaskDetail(id);
        if (cronTask == null) {
            return Result.msgCodes(MsgCodes.ERROR_NOT_FOUND_RECORD);
        }
        // 操作的不是自己租户范围内的任务
        if (!tenantIds.contains(cronTask.getTenantId())) {
            return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }
        return updateRunState(id, runState);
    }

    /**
     * 更新任务的运行状态
     *
     * @param taskId   任务Id
     * @param runState 任务状态
     * @return
     */
    private Result<Void> updateRunState(long taskId, TaskRunState runState) {
        TaskEntity temp = new TaskEntity();
        temp.setId(taskId);
        temp.setRunState(runState.getValue());
        int update = this.mapper.updateByPrimaryKeySelective(temp);
        if (update > 0) {
            if (runState == TaskRunState.STOP) {
                this.taskLogService.cancelExecute(taskId);
            }
            return Result.success();
        }
        return Result.msgCodes(MsgCodes.ERROR_UPDATE_FAILED);
    }

    /**
     * 根据任务ID获取任务详情
     *
     * @param id 任务ID
     * @return
     */
    @Override
    public TaskEntity getTaskDetail(long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 立即执行一次任务
     *
     * @param userId 当前用户ID
     * @param params 任务ID
     * @return
     */
    @Override
    public Result<String> executeTaskNow(long userId, ExecuteNowParams params) {
        // 判断该用户是否有操作这个任务的权限
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (tenantIds == null || tenantIds.isEmpty()) {
            return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }
        TaskEntity taskEntity = this.getTaskDetail(Long.parseLong(params.getTaskId()));
        if (taskEntity == null) {
            return Result.msgCodes(MsgCodes.ERROR_NOT_FOUND_RECORD);
        }
        // 操作的不是自己租户范围内的任务
        if (!tenantIds.contains(taskEntity.getTenantId())) {
            return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }

        // 添加后台参数
        taskEntity.setTaskParams(params.getTaskParams());
        long executionTime = new Date().getTime() + 3000;
        long id = this.taskLogService.addNowExecuteTaskLog(taskEntity, executionTime, TaskLogExeType.MANAGER_NOW);
        if (id <= 0) {
            return Result.msgCodes(MsgCodes.ERROR_SYSTEM);
        }
        return Result.success(String.valueOf(id));
    }

    /**
     * 获取任务列表，提供给搜索框用
     *
     * @param userId   用户ID
     * @param tenantId 租户ID
     * @param appName  应用名
     * @return
     */
    @Override
    public Result<List<SearchItem>> getSearchList(long userId, String tenantId, String appName) {
        List<SearchItem> items = new ArrayList<>();
        items.add(new SearchItem("全部", ""));
        TaskEntityExample example = new TaskEntityExample();
        TaskEntityExample.Criteria criteria = example.or();
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        long tenantIdLong = 0;
        if (StringUtils.isNotBlank(tenantId)) {
            tenantIdLong = Long.parseLong(tenantId);
        }
        if (tenantIds == null || tenantIds.isEmpty()) {
            return Result.success(items);
        }
        if (tenantIdLong > 0 && !tenantIds.contains(tenantIdLong)) {
            return Result.success(items);
        }
        if (tenantIdLong > 0) {
            criteria.andTenantIdEqualTo(tenantIdLong);
        } else {
            criteria.andTenantIdIn(tenantIds);
        }
        if (StringUtils.isNotBlank(appName)) {
            criteria.andAppNameEqualTo(appName);
        }
        List<TaskEntity> cronTasks = this.mapper.selectByExample(example);
        for (TaskEntity cronTask : cronTasks) {
            String label = cronTask.getAppDesc() + "（" + cronTask.getAppName() + "）";
            items.add(new SearchItem(label, String.valueOf(cronTask.getId())));
        }
        return Result.success(items);
    }

    /**
     * 根据任务ID更新任务信息
     *
     * @param userId 当前用户ID
     * @param params 参数
     * @return
     */
    @Override
    public Result<Void> updateById(long userId, TaskItem params) {
        if (StringUtils.isBlank(params.getId()) || params.getExpiredTime() <= 0 || params.getMaxRetryCount() <= 0 || params.getFailureRetryInterval() <= 0) {
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }

        long id = Long.parseLong(params.getId());
        TaskEntity task = this.getTaskDetail(id);
        if (task == null) {
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }

        // 不能更新不属于自己租户下的任务
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (!tenantIds.contains(task.getTenantId())) {
            return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }

        // 检查CRON表达式是否正确
        boolean valid = this.cronExpression.isValid(params.getCron());
        if (!valid) {
            return Result.msgCodes(MsgCodes.ERROR_CRON_EXPRESSION);
        }

        // 设置可以更新的字段
        task.setAppDesc(params.getAppDesc());
        task.setOwner(params.getOwner());
        task.setCron(params.getCron());
        task.setTag(params.getTag());
        task.setRunState(params.getRunState());
        task.setTaskParams(params.getParams());
        task.setRouterStrategy(params.getRouterStrategy());
        task.setExpiredStrategy(params.getExpiredStrategy());
        task.setExpiredTime(params.getExpiredTime());
        task.setFailureStrategy(params.getFailureStrategy());
        task.setMaxRetryCount(params.getMaxRetryCount());
        task.setFailureRetryInterval(params.getFailureRetryInterval());
        task.setRemark(params.getRemark());
        task.setModifyTime(new Date());
        logger.info("update task, userId:{}, task:{}", userId, task);

        int count = this.mapper.updateByPrimaryKeySelective(task);
        if (count > 0) {
            this.taskLogService.cancelExecute(task.getId());
            return Result.success();
        }
        return Result.msgCodes(MsgCodes.ERROR_UPDATE_FAILED);
    }

    /**
     * 获取最近几次的执行时间
     *
     * @param cron Cron表达式
     * @return
     */
    @Override
    public Result<String[]> getRecentExecutionTime(String cron) {
        // 检查CRON表达式是否正确
        boolean valid = this.cronExpression.isValid(cron);
        if (!valid) {
            return Result.msgCodes(MsgCodes.ERROR_CRON_EXPRESSION);
        }
        List<Date> nextExecutionTime = this.cronExpression.getNextExecutionTime(cron, 5);
        String[] times = new String[nextExecutionTime.size()];
        for (int i = 0; i < nextExecutionTime.size(); i++) {
            Date date = nextExecutionTime.get(i);
            times[i] = DateFormatUtils.format(date, Constants.DATE_FORMAT);
        }
        return Result.success(times);
    }

    /**
     * 分页查询任务列表
     *
     * @param lastTaskId 最后的taskId，以此作为分页
     * @param count      分页数量
     * @return
     */
    @Override
    public List<TaskEntity> getTaskList(long lastTaskId, int count) {
        TaskEntityExample example = new TaskEntityExample();
        example.or().andIdGreaterThan(lastTaskId).andRunStateEqualTo(TaskRunState.STARTUP.getValue());
        example.setOrderByClause(" id asc limit " + count);
        return this.mapper.selectByExample(example);
    }

    @Override
    public Result<Void> complete(TaskResult params) {
        TaskLogState exeState = TaskLogState.from(params.getState());
        if (exeState == null) {
            logger.error("complete task error, taskExeState error, params:{}", params);
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }

        // 执行器未找到执行的方法，需要将任务停用
        if (exeState == TaskLogState.EXECUTION_FAILED_NOT_FOUND) {
            logger.info("complete task, target object or target method is null, stop the task, params:{}", params);
            Result<Void> voidResult = this.updateRunState(params.getTaskId(), TaskRunState.STOP);
            if (!voidResult.isSuccess()) {
                logger.error("complete task error, update task run state failed, params:{}", params);
            }
        }

        TaskLogEntity entity = this.taskLogService.getTaskLog(params.getTaskLogId());
        if (entity == null) {
            logger.error("complete task error, task log not found, params:{}", params);
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }

        int state = params.getState();
        int retryCount = entity.getRetryCount();
        Date nextExecutionTime = null;

        // 如果还没超过最大重试次数，并且是失败重试
        if (state == TaskLogState.EXECUTION_FAILED.getValue()
                && retryCount < entity.getMaxRetryCount()
                && entity.getFailureStrategy() == FailureStrategy.FAILURE_RETRY.getValue()) {
            state = TaskLogState.EXECUTION_FAILED_RETRYING.getValue();
            retryCount += 1;
            long time = entity.getExecutionTime().getTime() + entity.getFailureRetryInterval();
            nextExecutionTime = new Date(time);
        }

        // 修改任务日志状态
        TaskLogEntity updateEntity = this.buildCompleteEntity(state, params, retryCount, nextExecutionTime);
        boolean success = this.taskLogService.updateByPrimaryKeySelective(updateEntity);
        logger.info("complete task, success:{}, params:{}", success, params);
        if (success) {
            return Result.success();
        }
        logger.error("complete task error, update task log failed, params:{}, updateEntity:{}", params, updateEntity);
        return Result.msgCodes(MsgCodes.ERROR_SYSTEM);
    }

    /**
     * 更新该应用下的所有任务状态
     *
     * @param appId 应用ID
     */
    @Override
    public void stopAllTask(long appId) {
        TaskEntityExample example = new TaskEntityExample();
        example.or().andAppIdEqualTo(appId).andRunStateEqualTo(TaskRunState.STARTUP.getValue());
        List<TaskEntity> entities = this.mapper.selectByExample(example);
        for (TaskEntity entity : entities) {
            this.updateRunState(entity.getId(), TaskRunState.STOP);
        }
    }

    @Override
    public int getTaskCount(TaskRunState state) {
        TaskEntityExample example = new TaskEntityExample();
        if (state != null) {
            example.or().andRunStateEqualTo(state.getValue());
        }
        return (int) this.mapper.countByExample(example);
    }

    /**
     * 构建任务完成时，更新的实体对象
     *
     * @param state             任务日志状态
     * @param params            参数
     * @param retryCount        重试次数
     * @param nextExecutionTime 下次执行时间
     * @return
     */
    private TaskLogEntity buildCompleteEntity(int state, TaskResult params, int retryCount, Date nextExecutionTime) {
        TaskLogEntity entity = new TaskLogEntity();
        entity.setId(params.getTaskLogId());
        entity.setState(state);
        entity.setRetryCount(retryCount);
        if (StringUtils.isNotBlank(params.getFailedReason())) {
            entity.setFailedReason(params.getFailedReason());
        }
        if (nextExecutionTime != null) {
            entity.setExecutionTime(nextExecutionTime);
        }
        entity.setRealExecutionTime(new Date(params.getRealExecutionTime()));
        entity.setElapsedTime(params.getElapsedTime());
        entity.setExecutorAddress(params.getAddress());
        return entity;
    }
}