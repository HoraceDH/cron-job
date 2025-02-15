package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.ExecutorState;
import cn.horace.cronjob.scheduler.bean.params.ExecutorHeartbeatParams;
import cn.horace.cronjob.scheduler.bean.params.ExecutorRegisterParams;
import cn.horace.cronjob.scheduler.bean.params.ExecutorUnregisterParams;
import cn.horace.cronjob.scheduler.bean.params.GetExecutorListParams;
import cn.horace.cronjob.scheduler.bean.result.ExecutorListResult;
import cn.horace.cronjob.scheduler.entities.ExecutorEntity;

import java.util.List;

/**
 * 执行器服务类
 * <p>
 *
 * @author Horace
 */
public interface ExecutorService {
    /**
     * 注册执行器
     *
     * @param params 参数
     * @return
     */
    Result<Void> register(ExecutorRegisterParams params);

    /**
     * 心跳
     *
     * @param params 心跳参数
     * @return
     */
    Result<Void> heartbeat(ExecutorHeartbeatParams params);

    /**
     * 获取指定应用下的在线执行器数量
     *
     * @param appId 应用ID
     * @return
     */
    int getOnlineCount(long appId);

    /**
     * 获取执行器列表
     *
     * @param userId 当前用户ID
     * @param params 查询参数
     * @return
     */
    Result<ExecutorListResult> getExecutorList(long userId, GetExecutorListParams params);

    /**
     * 获取执行器实体
     *
     * @param address 执行器地址
     * @return
     */
    ExecutorEntity getExecutor(String address);

    /**
     * 删除过期的下线的执行器
     *
     * @param maxRetainDays 最大保留天数
     */
    void deleteExpiredExecutors(int maxRetainDays);

    /**
     * 获取执行器列表
     *
     * @param appId 应用ID
     * @param state 执行器状态
     * @return
     */
    List<ExecutorEntity> getExecutorList(long appId, ExecutorState state);

    /**
     * 下线超时的执行器
     */
    void offlineExpiredExecutor();

    /**
     * 更新执行器状态
     *
     * @param address 执行器地址，主键
     * @param state   状态
     * @return
     */
    boolean updateState(String address, ExecutorState state);

    /**
     * 根据状态获取执行器数量
     *
     * @param state 状态
     * @return
     */
    int getExecutorCount(ExecutorState state);

    /**
     * 注销下线执行器
     *
     * @param params 参数
     * @return
     */
    Result<Void> unregister(ExecutorUnregisterParams params);
}
