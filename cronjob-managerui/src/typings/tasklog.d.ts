/**
 * 任务日志
 * @author Horace
 */

import {Commons} from "./commons";

/**
 * 任务日志
 */
declare namespace TaskLogBeans {
    // 任务实体
    type TaskLogItem = {
        id: string, // 任务ID
        taskId: string, // 任务ID
        tenant: string, // 租户编码
        appName: string, // 应用名
        owner: string, // 负责人
        method: string, // 方法全路径
        taskName: string, // 任务名
        cron: string, // cron表达式
        executorAddress: string, // 执行器地址
        executorHostName: string, // 执行器主机名
        elapsedTime: number, // 执行耗时
        tag: string, // 任务标签
        routerStrategy: number, // 路由策略，1：随机策略，2：分片策略
        expiredStrategy: number, // 过期策略，1：过期丢弃，2：过期执行
        expiredTime: number, // 过期时间，任务超过过期时间调度时，则走过期策略
        failureStrategy: number, // 失败策略，1：失败重试，2：失败丢弃
        maxRetryCount: number, // 最大重试次数
        retryCount: number, // 实际重试次数
        failureRetryInterval: number, // 失败重试间隔，秒
        schedulersAddress: string, // 调度器地址
        state: number, // 任务日志状态，1：初始化，2：队列中，3：调度中，4：执行成功，5：执行失败，6：取消执行（预生成日志后，任务取消等情况），7：任务过期（超过执行时间而未被调度），8：执行失败，已丢弃，9：执行失败，重试中
        params: string, // 任务参数
        remark: string, // 任务备注
        failedReason: string, // 失败原因
        firstExecutionTime: string, // 首次执行时间
        executionTime: string, // 预计执行时间
        dispatcherTime: string, // 调度时间
        realExecutionTime: string, // 实际执行时间
        createTime: string, // 创建时间
        modifyTime: string, // 修改时间
        delayTime: number,  // 延迟时间，毫秒
        page: number,  // 页码
        total: number,  // 总页数
    };

    // 获取任务日志的结果
    type TaskLogResult = Commons.MsgObject & {
        data?: TaskLogItem,
    }

    // 获取所有任务的结果
    type TaskLogListResult = Commons.MsgObject & {
        data?: TaskLogItem[],
        total: number,
        success: true,
        pageSize: number,
        current: number
    }
}
