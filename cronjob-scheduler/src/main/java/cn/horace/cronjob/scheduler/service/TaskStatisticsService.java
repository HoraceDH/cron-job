package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.params.GetLineDataParams;
import cn.horace.cronjob.scheduler.bean.result.LineDataItem;
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

    /**
     * 获取概要统计数据
     *
     * @param params 请求参数
     * @return
     */
    Result<List<LineDataItem>> getLineData(GetLineDataParams params);

    /**
     * 删除过期的统计数据
     *
     * @param maxRetainDays 最大保留天数
     */
    void deleteExpiredStatistics(int maxRetainDays);
}
