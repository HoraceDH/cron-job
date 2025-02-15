package cn.horace.cronjob.scheduler.service;

/**
 * 调度服务类
 * <p>
 *
 * @author Horace
 */
public interface SchedulerService {
    /**
     * 开始更新任务队列，从数据库记录中获取任务日志到内存队列中
     */
    void startUpdateTaskQueue();

    /**
     * 开始派发任务，从任务队列取出任务并派发给执行器
     */
    void startDispatcherTask();

    /**
     * 处理超时的任务日志
     */
    void handlerTimeoutTaskLog();

    /**
     * 停止从数据库获取任务日志
     */
    void stopUpdateTaskQueue();

    /**
     * 停止派发任务，队列中的任务依然等待调度完成
     */
    void stopDispatcherTask();
}
