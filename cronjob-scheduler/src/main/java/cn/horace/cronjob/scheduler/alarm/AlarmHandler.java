package cn.horace.cronjob.scheduler.alarm;

import cn.horace.cronjob.scheduler.bean.result.AlarmGroup;
import cn.horace.cronjob.scheduler.constants.AlarmChannel;

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
    public AlarmChannel getAlarmChannel();

    /**
     * 获取告警群列表
     *
     * @return
     */
    public List<AlarmGroup> getGroupList();
}
