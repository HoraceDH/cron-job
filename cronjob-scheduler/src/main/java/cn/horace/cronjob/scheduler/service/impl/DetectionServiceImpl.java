package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.constants.AppState;
import cn.horace.cronjob.commons.constants.ExecutorState;
import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.constants.Locks;
import cn.horace.cronjob.scheduler.entities.AppEntity;
import cn.horace.cronjob.scheduler.entities.ExecutorEntity;
import cn.horace.cronjob.scheduler.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 系统内的一些检测服务类
 * <p>
 * Created in 2025-02-21 21:39.
 */
@Service
public class DetectionServiceImpl implements DetectionService {
    private static final Logger logger = LoggerFactory.getLogger(DetectionServiceImpl.class);
    @Resource
    private AppConfig appConfig;
    @Resource
    private AppService appService;
    @Resource
    private TaskService taskService;
    @Resource
    private ExecutorService executorService;
    @Resource
    private LocksService locksService;

    /**
     * 定期检测应用的执行器状态，如果所有的执行器离线太久，则自动停止应用，避免无意义的调度
     */
    @Override
    public void detectionAndStopApp() {
        String lockId = Locks.LOCK_DETECTION_AND_STOP_APP.name();
        // 获取分布式锁
        String lockOwner = this.locksService.getLockOwner();
        boolean lock = this.locksService.lock(lockId, lockOwner, 60 * 3, Locks.LOCK_DETECTION_AND_STOP_APP.getMsg());
        if (!lock) {
            logger.info("not need start detection and stop app, no lock was obtained.");
            return;
        }
        try {
            logger.info("start detection and stop app.");

            long lastAppId = 0;
            while (true) {
                List<AppEntity> appEntities = this.appService.getAppList(lastAppId, 100, AppState.RUN);
                if (appEntities == null || appEntities.isEmpty()) {
                    break;
                }
                lastAppId = appEntities.get(appEntities.size() - 1).getId();

                for (AppEntity appEntity : appEntities) {
                    this.detectionAndStopApp(appEntity.getId());
                }

                // 避免频繁扫数据库
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
            }

        } catch (Exception e) {
            logger.error("detection and stop app error, msg:{}", e.getMessage(), e);
        } finally {
            this.locksService.releaseLock(lockId, lockOwner);
        }
    }

    /**
     * 检测是否需要停止应用
     *
     * @param appId 应用ID
     */
    private void detectionAndStopApp(Long appId) {
        List<ExecutorEntity> onlineExecutorList = this.executorService.getExecutorList(appId, ExecutorState.ONLINE);
        // 如果有在线的执行器，则不需要停止应用
        if (onlineExecutorList != null && !onlineExecutorList.isEmpty()) {
            return;
        }

        List<ExecutorEntity> offlineExecutorList = this.executorService.getExecutorList(appId, ExecutorState.OFFLINE);
        if (offlineExecutorList == null || offlineExecutorList.isEmpty()) {
            return;
        }

        // 按照修改时间倒序排序
        offlineExecutorList.sort((a, b) -> (int) (b.getModifyTime().getTime() - a.getModifyTime().getTime()));
        Date modifyTime = offlineExecutorList.get(0).getModifyTime();
        long diff = System.currentTimeMillis() - modifyTime.getTime();
        if (diff >= TimeUnit.MINUTES.toMillis(this.appConfig.getAutoStopAppMinutes())) {
            this.appService.updateState(appId, AppState.STOP);
            this.taskService.stopAllTask(appId);
            logger.warn("detection and stop app, the app is offline for a long time, stop the app, offlineExecutors:{}", offlineExecutorList);
        }
    }
}