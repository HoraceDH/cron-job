/**
 * 租户
 * @author Horace
 */

import {Commons} from "./commons";
import {TenantBeans} from "@/typings/tenant";

/**
 * 应用
 */
declare namespace AppBeans {
    import TenantItem = TenantBeans.TenantItem;

    // 应用实体
    type AppItem = {
        id: string, // 应用ID
        appName: string, // 应用名
        state: number,  // 状态码，0：正常，1：已下线
        tenantItem: TenantItem, // 租户信息
        executorCount: number,  // 在线执行器数
        taskCount: number,  // 任务数
        createTime: string, // 创建时间
    };

    // 获取所有租户的结果
    type AppListResult = Commons.MsgObject & {
        data?: AppItem[],
        total: number,
        success: true,
        pageSize: number,
        current: number
    }
}
