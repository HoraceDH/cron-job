package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.constants.TaskLogState;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import cn.horace.cronjob.scheduler.entities.TaskStatisticsEntity;
import cn.horace.cronjob.scheduler.mappers.TaskStatisticsEntityMapper;
import cn.horace.cronjob.scheduler.service.TaskStatisticsService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created in 2025-03-01 20:04.
 *
 * @author Horace
 */
@Service
public class TaskStatisticsServiceImpl implements TaskStatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(TaskStatisticsServiceImpl.class);
    @Resource
    private TaskStatisticsEntityMapper mapper;

    /**
     * 开始统计任务级别的统计日志
     *
     * @param dateScale         日期维度
     * @param dateGroupTaskLogs 按照日期分组的任务日志
     */
    @Override
    public void startStatistics(String dateScale, List<TaskLogEntity> dateGroupTaskLogs) {
        logger.info("start calc task statistics, dateScale:{}, size:{}", dateScale, dateGroupTaskLogs.size());
        List<TaskStatisticsEntity> entities = dateGroupTaskLogs.stream()
                // 按照任务ID分组
                .collect(Collectors.groupingBy(TaskLogEntity::getTaskId))
                // 将按任务ID分组后的任务日志统计计算，将结果保存到TaskStatisticsEntity中
                .entrySet().stream().flatMap(entry -> {
                    // 任务ID
                    Long taskId = entry.getKey();
                    // 按照任务ID分组的任务日志
                    List<TaskLogEntity> taskLogs = entry.getValue();
                    TaskStatisticsEntity entity = new TaskStatisticsEntity();

                    try {
                        // 调度成功数
                        long schedulerSuccess = taskLogs.stream().filter(taskLog -> taskLog.getState() == TaskLogState.EXECUTION_SUCCESS.getValue()).count();

                        // 调度失败数
                        long schedulerFailed = taskLogs.stream().filter(taskLog -> taskLog.getState() == TaskLogState.EXECUTION_FAILED.getValue() || taskLog.getState() == TaskLogState.EXECUTION_FAILED_DISCARD.getValue()).count();

                        // 平均延迟
                        double delayAvg = taskLogs.stream().mapToDouble(taskLog -> {
                            if (taskLog.getRealExecutionTime() == null) {
                                return 0;
                            }
                            long delay = taskLog.getRealExecutionTime().getTime() - taskLog.getExecutionTime().getTime();
                            return delay < 0 ? 0 : delay;
                        }).average().orElse(0.0);

                        // 最大延迟
                        double delayMax = taskLogs.stream().mapToDouble(taskLog -> {
                            if (taskLog.getRealExecutionTime() == null) {
                                return 0;
                            }
                            long delay = taskLog.getRealExecutionTime().getTime() - taskLog.getExecutionTime().getTime();
                            return delay < 0 ? 0 : delay;
                        }).max().orElse(0.0);

                        // 最小延迟
                        double delayMin = taskLogs.stream().mapToDouble(taskLog -> {
                            if (taskLog.getRealExecutionTime() == null) {
                                return 0;
                            }
                            long delay = taskLog.getRealExecutionTime().getTime() - taskLog.getExecutionTime().getTime();
                            return delay < 0 ? 0 : delay;
                        }).min().orElse(0.0);

                        // 平均耗时
                        double elapsedAvg = taskLogs.stream().mapToDouble(taskLog -> {
                            if (taskLog.getElapsedTime() == null) {
                                return 0;
                            }
                            return taskLog.getElapsedTime();
                        }).average().orElse(0.0);

                        // 最大耗时
                        double elapsedMax = taskLogs.stream().mapToDouble(taskLog -> {
                            if (taskLog.getElapsedTime() == null) {
                                return 0;
                            }
                            return taskLog.getElapsedTime();
                        }).max().orElse(0.0);

                        // 最小耗时
                        double elapsedMin = taskLogs.stream().mapToDouble(taskLog -> {
                            if (taskLog.getElapsedTime() == null) {
                                return 0;
                            }
                            return taskLog.getElapsedTime();
                        }).min().orElse(0.0);

                        // 平均提前
                        double beforeAvg = taskLogs.stream().mapToDouble(taskLog -> {
                            if (taskLog.getDispatchTime() == null) {
                                return 0;
                            }
                            return taskLog.getExecutionTime().getTime() - taskLog.getDispatchTime().getTime();
                        }).average().orElse(0.0);

                        // 最大提前
                        double beforeMax = taskLogs.stream().mapToDouble(taskLog -> {
                            if (taskLog.getDispatchTime() == null) {
                                return 0;
                            }
                            return taskLog.getExecutionTime().getTime() - taskLog.getDispatchTime().getTime();
                        }).max().orElse(0.0);

                        // 最小提前
                        double beforeMin = taskLogs.stream().mapToDouble(taskLog -> {
                            if (taskLog.getDispatchTime() == null) {
                                return 0;
                            }
                            return taskLog.getExecutionTime().getTime() - taskLog.getDispatchTime().getTime();
                        }).min().orElse(0.0);

                        entity.setDateScale(DateUtils.parseDate(dateScale, "yyyy-MM-dd HH:mm"));
                        entity.setTaskId(taskId);
                        entity.setTaskName(taskLogs.get(0).getName());
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
                    } catch (Exception e) {
                        logger.error("calc task statistics error, msg:{}", e.getMessage(), e);
                    }
                    return Stream.of(entity);
                })
                // 将统计结果收集为List
                .collect(Collectors.toList());

        // 批量插入统计结果
        this.mapper.batchInsert(entities);
        logger.info("end calc task statistics, dateScale:{}, entitySize:{}", dateScale, entities.size());
    }
}