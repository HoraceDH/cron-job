/**
 * 调度器
 * @author Horace
 */

import {Commons} from "./commons";

/**
 * 调度器
 */
declare namespace SchedulersBeans {

    // 调度器实体
    type SchedulersItem = {
        id: string, // 服务ID
        state: number,  // 状态，1：在线，2：离线
        address: string, // 调度器地址
        hostName: string, // 主机名
        createTime: string, // 创建时间
        modifyTime: string, // 创建时间
    };

    // 获取所有调度器的结果
    type SchedulersListResult = Commons.MsgObject & {
        data?: SchedulersItem[],
        total: number,
        success: true,
        pageSize: number,
        current: number
    }
}
