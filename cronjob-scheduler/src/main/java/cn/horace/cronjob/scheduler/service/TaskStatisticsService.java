package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.scheduler.entities.TaskLogEntity;

import java.util.List;

/**
 * Created in 2025-03-01 20:03.
 *
 * @author Horace
 */
public interface TaskStatisticsService {
    /**
     * 开始统计任务级别的统计日志
     *
     * @param dateScale         日期维度
     * @param dateGroupTaskLogs 按照日期分组的任务日志
     */
    void startStatistics(String dateScale, List<TaskLogEntity> dateGroupTaskLogs);
}
