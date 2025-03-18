package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.constants.TaskLogState;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;

/**
 * Created in 2025-03-18 21:33.
 *
 * @author Horace
 */
public interface AlarmService {
    /**
     * 是否是需要告警的状态
     *
     * @param state 状态值
     * @return
     */
    boolean isAlarmState(TaskLogState state);

    /**
     * 告警方法
     *
     * @param taskLog 任务日志
     */
    void alarm(TaskLogEntity taskLog);

    /**
     * 优雅关闭
     */
    void shutdownGracefully();
}
