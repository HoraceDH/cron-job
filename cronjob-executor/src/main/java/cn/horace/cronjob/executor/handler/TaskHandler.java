package cn.horace.cronjob.executor.handler;

import cn.horace.cronjob.commons.bean.TaskParams;
import cn.horace.cronjob.executor.bean.HandlerResult;

/**
 * 任务处理接口
 * <p>
 * Created in 2025-01-09 22:05.
 *
 * @author Horace
 */
public interface TaskHandler {
    /**
     * 执行任务的方法
     *
     * @param params 任务参数
     * @return 任务执行结果，如果执行成功，则返回HandlerResult.success()，如果执行失败，则返回HandlerResult.fail()，返回null，也判定是失败
     */
    public HandlerResult handle(TaskParams params);
}