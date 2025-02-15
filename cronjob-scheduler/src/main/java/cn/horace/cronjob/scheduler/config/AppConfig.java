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
}