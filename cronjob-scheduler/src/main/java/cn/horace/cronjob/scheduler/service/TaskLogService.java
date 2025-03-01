package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.TaskLogState;
import cn.horace.cronjob.scheduler.bean.params.GetCronTaskLogListParams;
import cn.horace.cronjob.scheduler.bean.result.TaskLogItem;
import cn.horace.cronjob.scheduler.bean.result.TaskLogListResult;
import cn.horace.cronjob.scheduler.constants.TaskLogExeType;
import cn.horace.cronjob.scheduler.entities.TaskEntity;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;

import java.util.Date;
import java.util.List;

/**
 * 任务日志
 * <p>
 *
 * @author Horace
 */
public interface TaskLogService {
    /**
     * 获取待执行的任务
     *
     * @param startDate 开始时间
     * @param endDate   最大时间，获取小于等于该时间的任务
     * @param limit     获取的最大数量
     * @return
     */
    List<TaskLogEntity> getPendingTaskList(Date startDate, Date endDate, int limit);

    /**
     * 更新任务日志状态
     *
     * @param id      任务日志ID
     * @param state   任务状态
     * @param version 版本号
     * @return 返回是否更新成功
     */
    public boolean updateTaskLogState(long id, TaskLogState state, int version);

    /**
     * 更新任务日志状态
     *
     * @param id              任务日志ID
     * @param state           任务状态
     * @param version         版本号
     * @param address         调度器地址
     * @param failedReason    失败原因
     * @param executorAddress 执行器地址
     * @return 返回是否更新成功
     */
    boolean updateTaskLogState(long id, TaskLogState state, int version, String address, String failedReason, String executorAddress);

    /**
     * 更新失败任务状态
     *
     * @param id                任务ID
     * @param nextExecutionTime 下次执行时间
     * @param retryCount        当前重试次数
     * @param failureReason     失败原因
     * @param version           版本号
     * @return 返回是否更新成功
     */
    boolean updateRetryTaskLog(long id, Date nextExecutionTime, int retryCount, String failureReason, Integer version);

    /**
     * 获取任务日志列表
     *
     * @param userId 当前用户ID
     * @param params 查询参数
     * @return
     */
    Result<TaskLogListResult> getTaskLogList(long userId, GetCronTaskLogListParams params);

    /**
     * 获取任务日志
     *
     * @param userId 当前用户DI
     * @param id     任务日志ID
     * @return
     */
    Result<TaskLogItem> getTaskLog(long userId, long id);

    /**
     * 获取最新一条记录
     *
     * @param taskId   任务ID
     * @param reverse  true表示按执行时间正序 asc，false表示按执行时间倒序 desc
     * @param state    任务日志状态，如果为null则不指定状态
     * @param notState 排除的任务日志状态，如果为null则不指定状态
     * @return
     */
    TaskLogEntity getLastTaskLog(long taskId, boolean reverse, TaskLogState state, TaskLogState notState);

    /**
     * 取消执行
     *
     * @param taskId
     */
    void cancelExecute(long taskId);

    /**
     * 将任务入库
     *
     * @param task              任务对象
     * @param lastExecutionTime 最新时间
     * @return
     */
    long addTaskLog(TaskEntity task, long lastExecutionTime);

    /**
     * 添加一个任务日志，并返回任务日志ID
     *
     * @param task          任务
     * @param executionTime 执行时间
     * @param exeType       执行类型
     * @return
     */
    long addNowExecuteTaskLog(TaskEntity task, long executionTime, TaskLogExeType exeType);

    /**
     * 清理过期的任务日志
     *
     * @param maxRetainDays 最大保留的日志天数
     */
    void deleteExpiredTaskLogs(int maxRetainDays);

    /**
     * 根据ID查询任务日志
     *
     * @param id 任务日志ID
     * @return
     */
    TaskLogEntity getTaskLog(long id);

    /**
     * 根据主键，更新选择项（不为空的字段）
     *
     * @param updateEntity 更新的实体
     * @return
     */
    boolean updateByPrimaryKeySelective(TaskLogEntity updateEntity);

    /**
     * 获取可能超时的任务日志列表
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    List<TaskLogEntity> getMayBeTimeoutTaskLogList(Date startDate, Date endDate);

    /**
     * 生成子任务并入库
     *
     * @param taskLog 父任务
     * @param page    页码
     * @param total   总数
     * @return
     */
    TaskLogEntity generateChildTaskLog(TaskLogEntity taskLog, int page, int total);

    /**
     * 更新Total字段
     *
     * @param id    taskLogId
     * @param total 总数
     */
    boolean updateTotal(long id, int total);

    /**
     * 根据时间范围查询任务日志
     *
     * @param startDate 开始时间，可能为空
     * @param endDate   结束时间
     * @return
     */
    List<TaskLogEntity> getTaskLogList(Date startDate, Date endDate);
}
