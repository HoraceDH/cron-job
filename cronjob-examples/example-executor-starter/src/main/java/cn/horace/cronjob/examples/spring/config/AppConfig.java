package cn.horace.cronjob.examples.spring.config;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.executor.starter.config.ExecutorStarterConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created in 2025-01-01 21:00.
 *
 * @author Horace
 */
@Configuration
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    /**
     * 自定义配置
     *
     * @return
     */
    @Bean
    public ExecutorStarterConfig cronJobExecutorStarterConfig() {
        String cronServerAddress = System.getProperty("cronjob.server.address");
        if (StringUtils.isBlank(cronServerAddress)) {
            cronServerAddress = "http://127.0.0.1:9527";
        }
        String tenant = System.getProperty("cronjob.tenant.name");
        if (StringUtils.isBlank(tenant)) {
            tenant = "horace";
        }
        String appName = System.getProperty("cronjob.app.name");
        if (StringUtils.isBlank(appName)) {
            appName = "example-executor-starter";
        }

        ExecutorStarterConfig config = new ExecutorStarterConfig();
        config.setAddress(cronServerAddress);
        config.setTenant(tenant);
        config.setAppName(appName);
        config.setAppDesc("Spring示例执行器");
        config.setTag(Constants.DEFAULT_TAG);
        config.setSignKey("7d890a079948b196756rtf5452d2245t");
        return config;
    }
}