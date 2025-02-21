package cn.horace.cronjob.scheduler.service;

/**
 * 系统内的一些检测服务类
 * <p>
 * Created in 2025-02-21 21:39.
 */
public interface DetectionService {
    /**
     * 定期检测应用的执行器状态，如果所有的执行器离线太久，则自动停止应用，避免无意义的调度
     */
    void detectionAndStopApp();
}
