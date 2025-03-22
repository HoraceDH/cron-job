/**
 * 告警相关
 * @author Horace
 */
declare namespace AlarmBeans {
    /**
     * 告警配置
     */
    type AlarmConfig = {
        // 告警类型
        type?: number;
        // 告警群ID
        chatId?: string;
    };

    // 告警实体
    type AlarmItem = {
        taskLogId?: string, // 任务日志ID
        appName?: string, // 应用名称
        taskName?: string, // 任务名称
        executorAddress?: string,  // 执行器地址
        executorHostName?: string, // 执行器主机名
        method?: string, // 任务方法
        state?: number, // 告警状态
        alarmType?: number, // 告警方式
        alarmGroupName?: string, // 告警群名
        createTime?: string, // 创建时间
    };

    // 获取告警列表的结果
    type AlarmListResult = Commons.MsgObject & {
        data?: AlarmItem[],
        total: number,
        success: true,
        pageSize: number,
        current: number
    }
}
