package cn.horace.cronjob.executor.service;

import cn.horace.cronjob.executor.CronJobExecutorClient;
import cn.horace.cronjob.executor.utils.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Horace
 */
public class HeartbeatService {
    private static final Logger logger = LoggerFactory.getLogger(HeartbeatService.class);
    private static HeartbeatService INSTANCE = new HeartbeatService();
    private ScheduledFuture<?> heartbeatFuture;

    private HeartbeatService() {
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static HeartbeatService getInstance() {
        return INSTANCE;
    }

    /**
     * 停止心跳
     */
    public void stop() {
        if (this.heartbeatFuture != null) {
            this.heartbeatFuture.cancel(true);
        }
    }

    /**
     * 开始心跳
     *
     * @param address 执行器地址
     */
    public void start(String address) {
        this.heartbeatFuture = ThreadPoolUtils.threadScheduledExecutor.scheduleAtFixedRate(() -> {
            // 如果已经停止，则不再重试
            if (CronJobExecutorClient.getInstance().isShutdown()) {
                logger.warn("cronjob executor is shutdown, don't heartbeat, address:{}", address);
                return;
            }
            if (!RegisterService.getInstance().isRegisterSuccess()) {
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
                return;
            }
            OpenApiService.getInstance().heartbeat(address);
        }, 0, 3, TimeUnit.SECONDS);
    }
}