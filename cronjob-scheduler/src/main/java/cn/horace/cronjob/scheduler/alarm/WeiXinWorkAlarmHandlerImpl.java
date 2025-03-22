package cn.horace.cronjob.scheduler.alarm;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.Message;
import cn.horace.cronjob.scheduler.bean.params.SendAlarmParams;
import cn.horace.cronjob.scheduler.bean.result.AlarmGroup;
import cn.horace.cronjob.scheduler.constants.AlarmType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 企业微信告警
 * <p>
 * Created in 2025-03-19 23:06.
 *
 * @author Horace
 */
@Service
public class WeiXinWorkAlarmHandlerImpl extends AlarmHandler {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinWorkAlarmHandlerImpl.class);

    /**
     * 获取告警通道
     *
     * @return
     */
    @Override
    public AlarmType getAlarmType() {
        return AlarmType.WEIXIN_WORK;
    }

    /**
     * 获取消息类型
     *
     * @return
     */
    @Override
    public String getMsgType() {
        // 卡片消息
        return "text";
    }

    /**
     * 是否可用
     *
     * @return
     */
    @Override
    public boolean isAvailable() {
        return false;
    }

    /**
     * 获取告警群列表
     *
     * @return
     */
    @Override
    public Result<List<AlarmGroup>> getGroupList() {
        return null;
    }

    /**
     * 发送消息
     *
     * @param message 消息对象
     * @return
     */
    @Override
    public Result<Void> sendMessage(Message message) {
        return null;
    }

    /**
     * 构建消息
     *
     * @param params
     * @return
     */
    @Override
    public String buildMessage(SendAlarmParams params) {
        return "";
    }
}