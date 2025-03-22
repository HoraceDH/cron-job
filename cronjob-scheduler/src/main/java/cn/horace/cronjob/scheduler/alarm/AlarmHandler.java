package cn.horace.cronjob.scheduler.alarm;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.Message;
import cn.horace.cronjob.scheduler.bean.params.SendAlarmParams;
import cn.horace.cronjob.scheduler.bean.result.AlarmGroup;
import cn.horace.cronjob.scheduler.constants.AlarmType;

import java.util.List;

/**
 * 告警处理器
 * <p>
 * Created in 2025-03-18 23:35.
 *
 * @author Horace
 */
public abstract class AlarmHandler {
    protected boolean available;

    /**
     * 获取告警通道
     *
     * @return
     */
    public abstract AlarmType getAlarmType();

    /**
     * 获取消息类型
     *
     * @return
     */
    public abstract String getMsgType();

    /**
     * 是否可用
     *
     * @return
     */
    public boolean isAvailable() {
        return this.available;
    }

    /**
     * 获取告警群列表
     *
     * @return
     */
    public abstract Result<List<AlarmGroup>> getGroupList();

    /**
     * 发送消息
     *
     * @param message 消息对象
     * @return
     */
    public abstract Result<Void> sendMessage(Message message);

    /**
     * 构建消息
     *
     * @param params
     * @return
     */
    public abstract String buildMessage(SendAlarmParams params);
}
