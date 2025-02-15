/**
 * 租户
 * @author Horace
 */

import {Commons} from "./commons";

/**
 * 租户
 */
declare namespace TenantBeans {

    // 租户实体
    type TenantItem = {
        key: string, // 租户ID
        name: string, // 租户名称
        tenant: string, // 租户编码
        appCount: number,  // 应用数
        remark: string, // 租户描述
        createTime: string, // 创建时间
    };

    // 获取所有租户的结果
    type AllTenantResult = Commons.MsgObject & {
        data?: TenantItem[],
        total: number,
        success: true,
        pageSize: number,
        current: number
    }
}
