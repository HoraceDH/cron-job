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

}
