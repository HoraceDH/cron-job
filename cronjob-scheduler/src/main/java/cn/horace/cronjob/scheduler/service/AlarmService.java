package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.TaskLogState;
import cn.horace.cronjob.scheduler.bean.params.GetGroupListParams;
import cn.horace.cronjob.scheduler.bean.params.SendAlarmParams;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;

import java.util.List;

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

    /**
     * 获取告警渠道列表，提供给搜索框用
     *
     * @return
     */
    Result<List<SearchItem>> getSearchList();

    /**
     * 获取告警渠道的群组列表，提供给搜索框用
     *
     * @param params 参数
     * @return
     */
    Result<List<SearchItem>> getGroupList(GetGroupListParams params);

    /**
     * 发送告警
     *
     * @param params 参数
     * @return
     */
    Result<Void> sendAlarm(SendAlarmParams params);
}
