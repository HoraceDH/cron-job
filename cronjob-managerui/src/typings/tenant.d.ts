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
        type: string, // 告警类型，0：不告警，1：飞书告警，2：企微告警，3：邮件告警
        groupName: string, // 告警群名称
        chatId: string, // 告警群ID
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
