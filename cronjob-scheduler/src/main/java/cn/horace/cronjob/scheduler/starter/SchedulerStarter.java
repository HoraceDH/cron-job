package cn.horace.cronjob.scheduler.starter;

import cn.horace.cronjob.commons.constants.SchedulerInstanceState;
import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import cn.horace.cronjob.commons.utils.executor.GracefulScheduledThreadPoolExecutor;
import cn.horace.cronjob.commons.utils.shutdown.ShutdownHook;
import cn.horace.cronjob.commons.utils.shutdown.ShutdownHookManager;
import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.entities.SchedulerInstanceEntity;
import cn.horace.cronjob.scheduler.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度入口
 * <p>
 *
 * @author Horace
 */
@Component
public class SchedulerStarter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerStarter.class);
    private GracefulScheduledThreadPoolExecutor threadScheduledExecutor = new GracefulScheduledThreadPoolExecutor(11, new DefaultThreadFactory("scheduler-thread"), false);
    @Resource
    private AppConfig appConfig;
    @Resource
    private ExecutorService executorService;
    @Resource
    private TaskLogGenerateService generateService;
    @Resource
    private TaskLogService taskLogService;
    @Resource
    private SchedulerService schedulerService;
    @Resource
    private SchedulerInstanceService schedulerInstanceService;
    @Resource
    private StatisticsService statisticsService;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化ShutdownHook
        this.initShutdownHook();

        // 检测调度器是否重复
        SchedulerInstanceEntity schedulerInstance = this.schedulerInstanceService.getSchedulerInstance(this.appConfig.getServerId());
        if (schedulerInstance != null && schedulerInstance.getState() == SchedulerInstanceState.ONLINE.getValue()) {
            String currentAddress = this.schedulerInstanceService.getCurrentAddress();
            if (!currentAddress.equals(schedulerInstance.getAddress())) {
                logger.error("scheduler start error, serverId repeated, serverId:{}, address:{}", this.appConfig.getServerId(), currentAddress);
                throw new RuntimeException("scheduler start error, serverId repeated");
            }
        }

        // 每10秒检查并统计一次数据
        this.threadScheduledExecutor.scheduleAtFixedRate(this.statisticsService::startStatistics, 0, 10, TimeUnit.SECONDS);

        // 删除过期的统计数据
        this.threadScheduledExecutor.scheduleAtFixedRate(() -> this.statisticsService.deleteExpiredStatistics(this.appConfig.getMaxRetainDays()), 0, 1, TimeUnit.MINUTES);

        // 每分钟检查并下线过期的调度器
        this.threadScheduledExecutor.scheduleAtFixedRate(this.schedulerInstanceService::offlineExpiredSchedulerInstance, 0, 3, TimeUnit.SECONDS);

        // 调度器心跳
        this.threadScheduledExecutor.scheduleAtFixedRate(this.schedulerInstanceService::heartbeat, 0, 3, TimeUnit.SECONDS);

        // 定期删除下线的执行器
        this.threadScheduledExecutor.scheduleAtFixedRate(() -> this.executorService.deleteExpiredExecutors(this.appConfig.getMaxRetainDays()), 0, 10, TimeUnit.MINUTES);

        // 定期检查执行器状态，对超时的执行器设置为下线状态
        this.threadScheduledExecutor.scheduleAtFixedRate(() -> this.executorService.offlineExpiredExecutor(), 0, 3, TimeUnit.SECONDS);

        // 定期清理过期的任务日志
        this.threadScheduledExecutor.scheduleAtFixedRate(() -> this.taskLogService.deleteExpiredTaskLogs(this.appConfig.getMaxRetainDays()), 0, 1, TimeUnit.MINUTES);

        // 定期生成任务日志，晚一点再开始生成任务日志，系统启动需要一定时间预热
        this.threadScheduledExecutor.scheduleAtFixedRate(this.generateService::generate, 10, 5, TimeUnit.SECONDS);

        // 开始更新任务队列，从数据库记录中获取任务日志到内存队列中
        this.threadScheduledExecutor.schedule(() -> this.schedulerService.startUpdateTaskQueue(), 5, TimeUnit.SECONDS);

        // 开始派发任务，从任务队列取出任务并派发给执行器
        this.threadScheduledExecutor.schedule(() -> this.schedulerService.startDispatcherTask(), 0, TimeUnit.SECONDS);

        // 定期将超时的任务日志设置为合适的状态
        this.threadScheduledExecutor.scheduleAtFixedRate(() -> this.schedulerService.handlerTimeoutTaskLog(), 0, 3, TimeUnit.SECONDS);
    }

    /**
     * 初始化关闭钩子
     */
    private void initShutdownHook() {
        // 服务关闭时，优雅的关闭各项调度任务
        ShutdownHookManager.addShutdownHook(new ShutdownHook("scheduler-shutdown", Integer.MIN_VALUE) {
            @Override
            public void run() {
                SchedulerStarter.this.shutdown();
            }
        });
    }

    /**
     * 关闭服务
     */
    private void shutdown() {
        logger.info("scheduler start shutdown");
        // 可以考虑跟Nginx联动，将入口摘除，避免接口流量再进来
        // 停止任务日志生成
        this.generateService.stopGenerateTaskLog();
        // 停止获取任务日志
        this.schedulerService.stopUpdateTaskQueue();
        // 停止派发任务，队列中的任务依然等待调度完成
        this.schedulerService.stopDispatcherTask();
        // 停止心跳，并将调度器设置为下线状态
        this.schedulerInstanceService.stopSchedulerInstance();
        this.threadScheduledExecutor.shutdownGracefully();
        logger.info("scheduler shutdown complete");
    }
}