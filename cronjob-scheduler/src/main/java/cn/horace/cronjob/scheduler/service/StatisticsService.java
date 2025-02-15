package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.params.GetLineDataParams;
import cn.horace.cronjob.scheduler.bean.result.LineDataItem;
import cn.horace.cronjob.scheduler.bean.result.SummaryDataResult;

import java.util.List;

/**
 * Created in 2024-11-05 23:12.
 *
 * @author Horace
 */
public interface StatisticsService {
    /**
     * 获取概要统计数据
     *
     * @return
     */
    Result<SummaryDataResult> getSummaryData();

    /**
     * 获取曲线图数据
     *
     * @param params 请求参数
     * @return
     */
    Result<List<LineDataItem>> getLineData(GetLineDataParams params);

    /**
     * 开始统计
     */
    void startStatistics();

    /**
     * 删除过期的统计数据
     *
     * @param maxRetainDays 最大保留的天数
     */
    void deleteExpiredStatistics(int maxRetainDays);
}
