package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.bean.TaskResult;
import cn.horace.cronjob.commons.constants.TaskRunState;
import cn.horace.cronjob.scheduler.bean.params.ExecuteNowParams;
import cn.horace.cronjob.scheduler.bean.params.GetTaskListParams;
import cn.horace.cronjob.scheduler.bean.params.TaskRegisterParams;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.bean.result.TaskItem;
import cn.horace.cronjob.scheduler.bean.result.TaskListResult;
import cn.horace.cronjob.scheduler.entities.TaskEntity;

import java.util.List;

/**
 * 任务服务类
 * <p>
 *
 * @author Horace
 */
public interface TaskService {
    /**
     * 注册任务
     *
     * @param params 参数
     * @return
     */
    Result<Void> register(List<TaskRegisterParams> params);

    /**
     * 获取任务数
     *
     * @param appId 应用ID
     * @return
     */
    int getTaskCount(long appId);

    /**
     * 查询任务列表
     *
     * @param userId 当前用户ID
     * @param params 查询参数
     * @return
     */
    Result<TaskListResult> getTaskList(long userId, GetTaskListParams params);

    /**
     * 查询任务详情
     *
     * @param userId 当前用户ID
     * @param id     任务ID
     * @return
     */
    Result<TaskItem> getTaskDetail(long userId, long id);

    /**
     * 更新任务运行状态
     *
     * @param userId   当前用户ID
     * @param id       任务ID
     * @param runState 运行状态
     * @return
     */
    Result<Void> updateRunState(long userId, long id, TaskRunState runState);

    /**
     * 根据任务ID获取任务详情
     *
     * @param id 任务ID
     * @return
     */
    TaskEntity getTaskDetail(long id);

    /**
     * 立即执行一次任务
     *
     * @param userId 当前用户ID
     * @param params 任务ID
     * @return
     */
    Result<String> executeTaskNow(long userId, ExecuteNowParams params);

    /**
     * 获取任务列表，提供给搜索框用
     *
     * @param userId   用户ID
     * @param tenantId 租户ID
     * @param appName  应用名
     * @return
     */
    Result<List<SearchItem>> getSearchList(long userId, String tenantId, String appName);

    /**
     * 根据任务ID更新任务信息
     *
     * @param userId 当前用户ID
     * @param params 参数
     * @return
     */
    Result<Void> updateById(long userId, TaskItem params);

    /**
     * 获取最近几次的执行时间
     *
     * @param cron Cron表达式
     * @return
     */
    Result<String[]> getRecentExecutionTime(String cron);

    /**
     * 分页查询任务列表
     *
     * @param lastTaskId 最后的taskId，以此作为分页
     * @param count      分页数量
     * @return
     */
    List<TaskEntity> getTaskList(long lastTaskId, int count);

    /**
     * 任务执行完毕
     *
     * @param result 任务执行结果
     * @return
     */
    Result<Void> complete(TaskResult result);

    /**
     * 更新该应用下的所有任务状态
     *
     * @param appId 应用ID
     */
    void stopAllTask(long appId);

    /**
     * 根据状态获取任务数量
     *
     * @param state 状态
     * @return
     */
    int getTaskCount(TaskRunState state);
}
