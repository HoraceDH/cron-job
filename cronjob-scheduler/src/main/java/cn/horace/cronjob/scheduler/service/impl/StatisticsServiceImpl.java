package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.*;
import cn.horace.cronjob.scheduler.bean.params.GetLineDataParams;
import cn.horace.cronjob.scheduler.bean.result.LineDataItem;
import cn.horace.cronjob.scheduler.bean.result.SummaryDataResult;
import cn.horace.cronjob.scheduler.constants.Locks;
import cn.horace.cronjob.scheduler.entities.StatisticsEntity;
import cn.horace.cronjob.scheduler.entities.StatisticsEntityExample;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import cn.horace.cronjob.scheduler.mappers.StatisticsEntityMapper;
import cn.horace.cronjob.scheduler.service.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created in 2024-11-06 14:12.
 *
 * @author Horace
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    @Resource
    private StatisticsEntityMapper mapper;
    @Resource
    private SchedulerInstanceService schedulerInstanceService;
    @Resource
    private TenantService tenantService;
    @Resource
    private AppService appService;
    @Resource
    private ExecutorService executorService;
    @Resource
    private TaskService taskService;
    @Resource
    private TaskLogService taskLogService;
    @Resource
    private LocksService locksService;
    @Resource
    private TaskStatisticsService taskStatisticsService;

    /**
     * 获取概要统计数据
     *
     * @return
     */
    @Override
    public Result<SummaryDataResult> getSummaryData() {
        Result<SummaryDataResult> result = Result.success();
        SummaryDataResult summaryDataResult = new SummaryDataResult();
        summaryDataResult.setOnlineSchedulerCount(this.schedulerInstanceService.getSchedulerInstanceCount(SchedulerInstanceState.ONLINE));
        summaryDataResult.setTotalTenantCount(this.tenantService.getAllTenantIds().size());
        summaryDataResult.setOnlineAppCount(this.appService.getAppCount(AppState.RUN));
        summaryDataResult.setOnlineExecutorCount(this.executorService.getExecutorCount(ExecutorState.ONLINE));
        summaryDataResult.setTotalTaskCount(this.taskService.getTaskCount(null));
        summaryDataResult.setOnlineTaskCount(this.taskService.getTaskCount(TaskRunState.STARTUP));
        result.setData(summaryDataResult);
        return result;
    }

    @Override
    public Result<List<LineDataItem>> getLineData(GetLineDataParams params) {
        try {
            Date startDate = DateUtils.parseDate(params.getStartDate(), "yyyy-MM-dd HH:mm:ss");
            Date endDate = DateUtils.parseDate(params.getEndDate(), "yyyy-MM-dd HH:mm:ss");
            long diff = endDate.getTime() - startDate.getTime();
            long millis = TimeUnit.DAYS.toMillis(7);
            if (diff > millis) {
                return Result.msgCodes(MsgCodes.ERROR_QUERY_RANGE);
            }

            Result<List<LineDataItem>> result = Result.success();
            StatisticsEntityExample example = new StatisticsEntityExample();
            example.or().andDateScaleGreaterThanOrEqualTo(startDate)
                    .andDateScaleLessThanOrEqualTo(endDate);
            List<StatisticsEntity> entities = this.mapper.selectByExample(example);
            List<LineDataItem> items = new ArrayList<>();
            for (StatisticsEntity entity : entities) {
                String dateScale = DateFormatUtils.format(entity.getDateScale(), "yyyy-MM-dd HH:mm:ss");
                items.add(new LineDataItem("调度成功", dateScale, entity.getSchedulerSuccess()));
                items.add(new LineDataItem("调度失败", dateScale, entity.getSchedulerFailed()));
                items.add(new LineDataItem("平均延迟(ms)", dateScale, entity.getDelayAvg()));
                items.add(new LineDataItem("最大延迟(ms)", dateScale, entity.getDelayMax()));
                items.add(new LineDataItem("最小延迟(ms)", dateScale, entity.getDelayMin()));
                items.add(new LineDataItem("平均耗时(ms)", dateScale, entity.getElapsedAvg()));
                items.add(new LineDataItem("最大耗时(ms)", dateScale, entity.getElapsedMax()));
                items.add(new LineDataItem("最小耗时(ms)", dateScale, entity.getElapsedMin()));
                items.add(new LineDataItem("平均提前(ms)", dateScale, entity.getBeforeAvg()));
                items.add(new LineDataItem("最大提前(ms)", dateScale, entity.getBeforeMax()));
                items.add(new LineDataItem("最小提前(ms)", dateScale, entity.getBeforeMin()));
            }
            result.setData(items);
            return result;
        } catch (Exception e) {
            logger.error("get line data error, params:{}, msg:{}", params, e.getMessage(), e);
            return Result.msgCodes(MsgCodes.ERROR_SYSTEM);
        }
    }

    @Override
    public void startStatistics() {
        String lockId = Locks.LOCK_GENERATE_STATISTICS.name();
        // 获取分布式锁
        String lockOwner = this.locksService.getLockOwner();
        boolean lock = this.locksService.lock(lockId, lockOwner, 50, Locks.LOCK_GENERATE_STATISTICS.getMsg());
        if (!lock) {
            logger.info("not need start statistics, no lock was obtained.");
            return;
        }

        Date startDate = null;
        Date endDate = null;
        int count = 0;
        try {
            StatisticsEntityExample example = new StatisticsEntityExample();
            example.setOrderByClause("`date_scale` desc limit 1");
            List<StatisticsEntity> entities = this.mapper.selectByExample(example);
            if (entities != null && !entities.isEmpty()) {
                startDate = entities.get(0).getDateScale();
                startDate = DateUtils.addMinutes(startDate, 1);
            }

            // 只统计3分钟前的数据
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, -3);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            endDate = calendar.getTime();
            if (startDate != null && endDate.getTime() <= startDate.getTime()) {
                return;
            }
            logger.info("start statistics, startDate:{}, endDate:{}", startDate, endDate);
            List<TaskLogEntity> taskLogs = this.taskLogService.getTaskLogList(startDate, endDate);

            // 分组统计
            List<StatisticsEntity> statisticsEntityList = taskLogs.stream().collect(Collectors.groupingBy(taskLog -> DateFormatUtils.format(taskLog.getExecutionTime(), "yyyy-MM-dd HH:mm"))).entrySet().stream().flatMap(entry -> {
                List<StatisticsEntity> statisticsEntities = new ArrayList<>();
                try {
                    String dateScale = entry.getKey();
                    List<TaskLogEntity> groupTaskLogs = entry.getValue();
                    this.taskStatisticsService.startStatistics(dateScale, groupTaskLogs);

                    // 调度成功数
                    long schedulerSuccess = groupTaskLogs.stream().filter(taskLog -> taskLog.getState() == TaskLogState.EXECUTION_SUCCESS.getValue()).count();

                    // 调度失败数
                    long schedulerFailed = groupTaskLogs.stream().filter(taskLog -> taskLog.getState() == TaskLogState.EXECUTION_FAILED.getValue() || taskLog.getState() == TaskLogState.EXECUTION_FAILED_DISCARD.getValue()).count();

                    // 平均延迟
                    double delayAvg = groupTaskLogs.stream().mapToDouble(taskLog -> {
                        if (taskLog.getRealExecutionTime() == null) {
                            return 0;
                        }
                        long delay = taskLog.getRealExecutionTime().getTime() - taskLog.getExecutionTime().getTime();
                        return delay < 0 ? 0 : delay;
                    }).average().orElse(0.0);

                    // 最大延迟
                    double delayMax = groupTaskLogs.stream().mapToDouble(taskLog -> {
                        if (taskLog.getRealExecutionTime() == null) {
                            return 0;
                        }
                        long delay = taskLog.getRealExecutionTime().getTime() - taskLog.getExecutionTime().getTime();
                        return delay < 0 ? 0 : delay;
                    }).max().orElse(0.0);

                    // 最小延迟
                    double delayMin = groupTaskLogs.stream().mapToDouble(taskLog -> {
                        if (taskLog.getRealExecutionTime() == null) {
                            return 0;
                        }
                        long delay = taskLog.getRealExecutionTime().getTime() - taskLog.getExecutionTime().getTime();
                        return delay < 0 ? 0 : delay;
                    }).min().orElse(0.0);

                    // 平均耗时
                    double elapsedAvg = groupTaskLogs.stream().mapToDouble(taskLog -> {
                        if (taskLog.getElapsedTime() == null) {
                            return 0;
                        }
                        return taskLog.getElapsedTime();
                    }).average().orElse(0.0);

                    // 最大耗时
                    double elapsedMax = groupTaskLogs.stream().mapToDouble(taskLog -> {
                        if (taskLog.getElapsedTime() == null) {
                            return 0;
                        }
                        return taskLog.getElapsedTime();
                    }).max().orElse(0.0);

                    // 最小耗时
                    double elapsedMin = groupTaskLogs.stream().mapToDouble(taskLog -> {
                        if (taskLog.getElapsedTime() == null) {
                            return 0;
                        }
                        return taskLog.getElapsedTime();
                    }).min().orElse(0.0);

                    // 平均提前
                    double beforeAvg = groupTaskLogs.stream().mapToDouble(taskLog -> {
                        if (taskLog.getDispatchTime() == null) {
                            return 0;
                        }
                        return taskLog.getExecutionTime().getTime() - taskLog.getDispatchTime().getTime();
                    }).average().orElse(0.0);

                    // 最大提前
                    double beforeMax = groupTaskLogs.stream().mapToDouble(taskLog -> {
                        if (taskLog.getDispatchTime() == null) {
                            return 0;
                        }
                        return taskLog.getExecutionTime().getTime() - taskLog.getDispatchTime().getTime();
                    }).max().orElse(0.0);

                    // 最小提前
                    double beforeMin = groupTaskLogs.stream().mapToDouble(taskLog -> {
                        if (taskLog.getDispatchTime() == null) {
                            return 0;
                        }
                        return taskLog.getExecutionTime().getTime() - taskLog.getDispatchTime().getTime();
                    }).min().orElse(0.0);

                    StatisticsEntity entity = new StatisticsEntity();
                    entity.setDateScale(DateUtils.parseDate(dateScale, "yyyy-MM-dd HH:mm"));
                    entity.setSchedulerSuccess((int) schedulerSuccess);
                    entity.setSchedulerFailed((int) schedulerFailed);
                    entity.setDelayAvg(delayAvg);
                    entity.setDelayMax(delayMax);
                    entity.setDelayMin(delayMin);
                    entity.setElapsedAvg(elapsedAvg);
                    entity.setElapsedMax(elapsedMax);
                    entity.setElapsedMin(elapsedMin);
                    entity.setBeforeAvg(beforeAvg);
                    entity.setBeforeMax(beforeMax);
                    entity.setBeforeMin(beforeMin);
                    entity.setCreateTime(new Date());
                    entity.setModifyTime(new Date());
                    statisticsEntities.add(entity);
                } catch (Exception e) {
                    logger.error("calc statistics error, msg:{}", e.getMessage(), e);
                }
                return statisticsEntities.stream();
            }).collect(Collectors.toList());

            if (!statisticsEntityList.isEmpty()) {
                count = this.mapper.batchInsert(statisticsEntityList);
            }
        } catch (Exception e) {
            logger.error("handler statistics error, startDate:{}, endDate:{}, msg:{}", startDate, endDate, e.getMessage(), e);
        } finally {
            this.locksService.releaseLock(lockId, lockOwner);
        }
        logger.info("end statistics, count:{}, startDate:{}, endDate:{}", count, startDate, endDate);
    }

    @Override
    public void deleteExpiredStatistics(int maxRetainDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -maxRetainDays);
        Date date = calendar.getTime();
        StatisticsEntityExample example = new StatisticsEntityExample();
        example.or().andDateScaleLessThan(date);
        int count = this.mapper.deleteByExample(example);
        if (count > 0) {
            logger.info("delete expired statistics log, date:{}, count:{}", DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), count);
        }
    }
}