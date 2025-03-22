package cn.horace.cronjob.scheduler.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Horace
 */
@Data
@ToString
@Configuration
public class AppConfig {
    @Value("${server.id}")
    private int serverId;
    @Value("${server.port}")
    private int port;
    @Value("${schedulers.beforeInterval}")
    private int schedulersBeforeInterval;
    @Value("${schedulers.timeout}")
    private int schedulersTimeout;
    @Value("${schedulers.taskQueueCount}")
    private int schedulersTaskQueueCount;
    @Value("${schedulers.taskPreGenerationMaxTimeMinutes}")
    private int schedulersTaskPreGenerationMaxTimeMinutes;
    @Value("${taskLog.maxRetainDays}")
    private int maxRetainDays;
    @Value("${manager.signKey}")
    private String managerSignKey;
    @Value("${executor.signKey}")
    private String executorSignKey;
    @Value("${executor.timeout}")
    private int executorTimeout;
    /**
     * 如果所有的执行器离线太久，则自动停止应用，避免无意义的调度，单位分钟
     */
    @Value("${executor.autoStopAppMinutes}")
    private int autoStopAppMinutes;
    @Value("${domain}")
    private String domain;
    @Value("${lark.appId}")
    private String larkAppId;
    @Value("${lark.appSecret}")
    private String larkAppSecret;
}