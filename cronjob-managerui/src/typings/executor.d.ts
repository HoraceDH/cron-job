/**
 * 执行器
 * @author Horace
 */

import {Commons} from "./commons";

/**
 * 执行器
 */
declare namespace ExecutorBeans {
    // 执行器实体
    type ExecutorItem = {
        exeId: string, // 执行器ID，由执行器端生成
        address: string, // 执行器地址
        schedulersAddress: string, // 连接的调度器地址
        tenantCode: string, // 租户编码
        appName: string, // 应用名
        hostName: string, // 主机名
        name: string, // 执行器名
        tag: string, // 执行器标签
        state: number, // 执行器状态
        version: string, // 版本号
        createTime: string, // 创建时间
        modifyTime: string, // 修改时间
    };

    // 获取所有执行器的结果
    type ExecutorListResult = Commons.MsgObject & {
        data?: ExecutorItem[],
        total: number,
        success: true,
        pageSize: number,
        current: number
    }
}
