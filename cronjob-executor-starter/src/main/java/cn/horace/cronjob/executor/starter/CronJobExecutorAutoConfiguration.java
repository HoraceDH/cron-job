package cn.horace.cronjob.executor.starter;

import cn.horace.cronjob.executor.CronJobExecutorClient;
import cn.horace.cronjob.executor.config.ExecutorConfig;
import cn.horace.cronjob.executor.handler.TaskHandler;
import cn.horace.cronjob.executor.starter.config.BaseConfig;
import cn.horace.cronjob.executor.starter.config.ExecutorStarterConfig;
import cn.horace.cronjob.executor.starter.config.ExecutorProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created in 2024-11-24 15:47.
 *
 * @author Horace
 */
@Configuration
@ConditionalOnBean(EnabledMarker.class)
@EnableConfigurationProperties(ExecutorProperties.class)
public class CronJobExecutorAutoConfiguration implements ApplicationContextAware, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(CronJobExecutorAutoConfiguration.class);
    private ApplicationContext applicationContext;
    @Resource
    private ExecutorProperties properties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BaseConfig config = this.properties;
        try {
            ExecutorStarterConfig executorStarterConfig = this.applicationContext.getBean(ExecutorStarterConfig.class);
            config = executorStarterConfig;
            logger.info("cron job executor, start auto configuration, use customer bean configuration, config:{}", executorStarterConfig);
        } catch (NoSuchBeanDefinitionException e) {
            logger.info("cron job executor, start auto configuration, use application properties configuration, config:{}", this.properties);
        }

        Map<String, TaskHandler> beansMap = this.applicationContext.getBeansOfType(TaskHandler.class);
        ArrayList<Object> taskObjects = new ArrayList<>(beansMap.values());
        ExecutorConfig executorConfig = ExecutorConfig.Builder.newBuilder(taskObjects)
                .address(config.getAddress())
                .tenant(config.getTenant())
                .appName(config.getAppName())
                .appDesc(config.getAppDesc())
                .tag(config.getTag())
                .signKey(config.getSignKey())
                .build();
        CronJobExecutorClient.init(executorConfig).start();
    }
}