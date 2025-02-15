package cn.horace.cronjob.scheduler.service;

/**
 * 生成任务日志
 * <p>
 *
 * @author Horace
 */
public interface TaskLogGenerateService {
    /**
     * 生成任务日志
     */
    void generate();

    /**
     * 停止生成任务日志
     */
    void stopGenerateTaskLog();
}
