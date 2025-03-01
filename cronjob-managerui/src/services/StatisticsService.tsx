import {StatisticsBeans} from "@/typings/statisticss";
import {request} from "@@/exports";
import Apis from "@/typings/apis";
import MsgCodes from "@/typings/msgcodes";

/**
 * Created in 2024-11-05 22:00.
 * @author Horace
 */
class StatisticsService {
    /**
     * 单例实例对象
     * @private
     */
    private static INSTANCE: StatisticsService = new StatisticsService();

    /**
     * 获取实例对象
     */
    public static getInstance(): StatisticsService {
        return this.INSTANCE;
    }

    /**
     * 获取概要数据
     */
    public async getSummaryData(): Promise<StatisticsBeans.SummaryData | undefined> {
        const summaryResult = await request<StatisticsBeans.SummaryDataResult>(Apis.URI_STATISTICS_GET_SUMMARY_DATA, {
            method: "POST",
            params: {}
        });
        if (summaryResult.code !== MsgCodes.SUCCESS) {
            console.error("get summary data failed, ", summaryResult);
            return undefined;
        }
        return summaryResult.data;
    }

    /**
     * 获取曲线图数据
     *
     * @param taskId 任务ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public async getLineData(taskId: string | undefined, startDate: string, endDate: string): Promise<StatisticsBeans.LineData[] | undefined> {
        const lineDataResult = await request<StatisticsBeans.LineDataResult>(Apis.URI_STATISTICS_GET_LINE_DATA, {
            method: "POST",
            data: {
                taskId: taskId,
                startDate: startDate,
                endDate: endDate,
            }
        });
        if (lineDataResult.code !== MsgCodes.SUCCESS) {
            console.error("get line data failed, ", lineDataResult);
            return [];
        }
        return lineDataResult.data;
    }
}

export default StatisticsService;