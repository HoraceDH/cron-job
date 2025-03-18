package cn.horace.cronjob.scheduler.alarm;

import cn.horace.cronjob.scheduler.bean.result.AlarmGroup;
import cn.horace.cronjob.scheduler.constants.AlarmChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 飞书或者Lark告警
 * <p>
 * Created in 2025-03-18 23:40.
 *
 * @author Horace
 */
@Service
public class LarkAlarmHandlerImpl implements AlarmHandler {
    private static final Logger logger = LoggerFactory.getLogger(LarkAlarmHandlerImpl.class);

    /**
     * 获取告警通道
     *
     * @return
     */
    @Override
    public AlarmChannel getAlarmChannel() {
        return AlarmChannel.Lark;
    }

    /**
     * 获取告警群列表
     *
     * @return
     */
    @Override
    public List<AlarmGroup> getGroupList() {
        return Collections.emptyList();
    }
}