/**
 * 统计相关
 * @author Horace
 */
import {Commons} from "./commons";

declare namespace StatisticsBeans {
    // 概要数据
    type SummaryData = {
        onlineSchedulerCount: number,  // 在线执行器数
        totalTenantCount: number,  // 总的租户数
        onlineAppCount: number,  // 在线的应用数
        onlineExecutorCount: number,  // 在线的执行器数
        totalTaskCount: number,  // 总的任务数
        onlineTaskCount: number,  // 在线的任务数
    };

    // 曲线图数据对象
    type LineData = {
        name: string, // 指标名称
        date: string,  // 时间
        value: number,  // 指标值
    }

    // 获取概要数据
    type SummaryDataResult = Commons.MsgObject & {
        data?: SummaryData,
    }

    // 获取曲线图数据
    type LineDataResult = Commons.MsgObject & {
        data?: LineData[],
    }
}