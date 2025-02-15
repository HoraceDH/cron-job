package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.SchedulerInstanceState;
import cn.horace.cronjob.scheduler.bean.params.GetSchedulerInstanceListParams;
import cn.horace.cronjob.scheduler.bean.result.SchedulerInstanceListResult;
import cn.horace.cronjob.scheduler.entities.SchedulerInstanceEntity;

import java.util.List;

/**
 * 调度器服务类
 *
 * @author Horace
 */
public interface SchedulerInstanceService {
    /**
     * 调度器实例心跳
     */
    void heartbeat();

    /**
     * 注册调度器实例到数据库中
     *
     * @param serverId 服务实例ID
     * @param host     主机地址，例如：localhost:9527
     * @param state    实例状态
     */
    void updateState(int serverId, String host, SchedulerInstanceState state);

    /**
     * 下线过期的调度器
     */
    void offlineExpiredSchedulerInstance();

    /**
     * 获取全部的调度器
     *
     * @return
     */
    List<SchedulerInstanceEntity> getAllSchedulerInstance();

    /**
     * 根据服务ID获取调度器信息
     *
     * @param serverId 服务ID
     * @return
     */
    SchedulerInstanceEntity getSchedulerInstance(int serverId);

    /**
     * 获取调度器列表
     *
     * @param params 查询参数
     * @return
     */
    Result<SchedulerInstanceListResult> getSchedulerInstanceList(GetSchedulerInstanceListParams params);

    /**
     * 获取当前实例的调度器地址
     *
     * @return
     */
    String getCurrentAddress();

    /**
     * 根据调度器地址获取调度器实例
     *
     * @param address 调度器地址
     * @return
     */
    SchedulerInstanceEntity getSchedulerInstance(String address);

    /**
     * 根据状态获取执行器数量
     *
     * @param state 状态
     * @return
     */
    int getSchedulerInstanceCount(SchedulerInstanceState state);

    /**
     * 停止心跳，并将调度器设置为下线状态
     */
    void stopSchedulerInstance();
}
