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
public interface AlarmHandler {
    /**
     * 获取告警通道
     *
     * @return
     */
    public AlarmType getAlarmChannel();

    /**
     * 获取消息类型
     *
     * @return
     */
    public String getMsgType();

    /**
     * 是否可用
     *
     * @return
     */
    public boolean isAvailable();

    /**
     * 获取告警群列表
     *
     * @return
     */
    public Result<List<AlarmGroup>> getGroupList();

    /**
     * 发送消息
     *
     * @param message 消息对象
     * @return
     */
    public Result<Void> sendMessage(Message message);

    /**
     * 构建消息
     *
     * @param params
     * @return
     */
    String buildMessage(SendAlarmParams params);
}
