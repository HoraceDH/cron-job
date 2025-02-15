package cn.horace.cronjob.executor;

import ch.qos.logback.classic.LoggerContext;
import cn.horace.cronjob.commons.httpclient.HttpClient;
import cn.horace.cronjob.commons.logger.LoggerFilter;
import cn.horace.cronjob.commons.utils.shutdown.ShutdownHook;
import cn.horace.cronjob.commons.utils.shutdown.ShutdownHookManager;
import cn.horace.cronjob.executor.config.ExecutorConfig;
import cn.horace.cronjob.executor.constants.OpenApis;
import cn.horace.cronjob.executor.httpserver.HttpServer;
import cn.horace.cronjob.executor.service.DispatcherService;
import cn.horace.cronjob.executor.service.HeartbeatService;
import cn.horace.cronjob.executor.service.RegisterService;
import cn.horace.cronjob.executor.service.TaskService;
import cn.horace.cronjob.executor.utils.ThreadPoolUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Horace
 */
public class CronJobExecutorClient {
    private static Logger logger = LoggerFactory.getLogger(CronJobExecutorClient.class);
    private ExecutorConfig config;
    private static CronJobExecutorClient INSTANCE = null;
    private volatile boolean shutdown = false;

    private CronJobExecutorClient(ExecutorConfig config) {
        this.config = config;
        OpenApis.HOST = config.getAddress();
        ShutdownHookManager.addShutdownHook(new ShutdownHook("shutdown-executor", Integer.MIN_VALUE) {
            @Override
            public void run() {
                shutdown = true;

                // 向调度器发送下线的请求
                RegisterService.getInstance().unregister(HttpServer.getInstance().getAddress());
                // 关闭调度任务并等待完成
                DispatcherService.getInstance().stop();
                // 停止心跳
                HeartbeatService.getInstance().stop();
                // 关闭任务相关线程池
                TaskService taskService = TaskService.getInstance();
                if (taskService != null) {
                    taskService.stop();
                }
            }
        });
    }

    /**
     * 是否已经停止
     * @return
     */
    public boolean isShutdown() {
        return shutdown;
    }

    public ExecutorConfig getConfig() {
        return config;
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static CronJobExecutorClient getInstance() {
        return INSTANCE;
    }

    /**
     * 获取实例对象
     *
     * @param config 配置对象
     * @return
     */
    public static CronJobExecutorClient init(ExecutorConfig config) {
        if (INSTANCE == null) {
            synchronized (CronJobExecutorClient.class) {
                if (INSTANCE == null) {
                    // 配置检查
                    if (config == null || StringUtils.isBlank(config.getAddress())
                            || StringUtils.isBlank(config.getTenant()) || StringUtils.isBlank(config.getAppName())
                            || StringUtils.isBlank(config.getAppDesc()) || StringUtils.isBlank(config.getTag())) {
                        logger.error("create executor instance error, params error, config:{}", config);
                        throw new IllegalArgumentException("create executor instance error, params error, config:" + config);
                    }
                    INSTANCE = new CronJobExecutorClient(config);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 启动
     */
    public void start() {
        // 添加日志过滤器
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.addTurboFilter(new LoggerFilter());

        // 初始化HttpClient
        HttpClient.init(HttpClient.getHttpClientBuilder(this.config.getSignKey()));

        // 初始化任务对象集合
        TaskService.init(this.config);

        // 启动WEB服务
        HttpServer httpServer = HttpServer.getInstance();
        httpServer.start();

        // 等待HTTP服务启动成功
        while (!httpServer.isStarted()) {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(200));
        }

        ThreadPoolUtils.threadScheduledExecutor.scheduleAtFixedRate(() -> {
            if (shutdown) {
                return;
            }
            // 注册执行器
            RegisterService.getInstance().register(this.config, httpServer.getAddress());
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
            // 注册任务
            TaskService.getInstance().register(httpServer.getAddress());
        }, 0, 30, TimeUnit.SECONDS);

        // 开始心跳，如果执行器未注册成功，则不会开始心跳
        HeartbeatService.getInstance().start(httpServer.getAddress());
    }
}
