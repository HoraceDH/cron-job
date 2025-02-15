/**
 * 任务
 * @author Horace
 */

import {Commons} from "./commons";

/**
 * 任务
 */
declare namespace TaskBeans {
    // 任务实体
    type TaskItem = {
        id: string, // 任务ID
        tenant: string, // 租户编码
        appName: string, // 应用名
        owner: string, // 负责人
        appDesc: string, // 应用描述
        name: string, // 任务名
        cron: string, // cron表达式
        tag: string, // 执行器标签
        onlineState: number, // 在线状态
        runState: number, // 运行状态
        method: string, // 方法全路径
        routerStrategy: number, // 路由策略，1：随机策略，2：分片策略
        expiredStrategy: number, // 过期策略，1：过期丢弃，2：过期执行
        expiredTime: number, // 过期时间，任务超过过期时间调度时，则走过期策略
        failureStrategy: number, // 失败策略，1：失败重试，2：失败丢弃
        maxRetryCount: number, // 最大重试次数
        failureRetryInterval: number, // 失败重试间隔，秒
        params: string, // 任务参数
        remark: string, // 任务备注
        createTime: string, // 创建时间
        modifyTime: string, // 修改时间
    };

    // 获取任务的结果
    type TaskResult = Commons.MsgObject & {
        data?: TaskItem,
    }

    // 获取所有任务的结果
    type TaskListResult = Commons.MsgObject & {
        data?: TaskItem[],
        total: number,
        success: true,
        pageSize: number,
        current: number
    }

    // 检查表达式的结果
    type CheckResult = Commons.MsgObject & {
        data?: string[],
    }
}
