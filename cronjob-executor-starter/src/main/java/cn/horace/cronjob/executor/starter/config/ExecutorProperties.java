package cn.horace.cronjob.executor.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 执行器配置属性
 * <p>
 * Created in 2024-11-24 16:18.
 *
 * @author Horace
 */
@ConfigurationProperties(prefix = "cronjob")
public class ExecutorProperties extends BaseConfig {
    @Override
    public String toString() {
        return "ExecutorProperties{" +
                "address='" + address + '\'' +
                ", tenant='" + tenant + '\'' +
                ", appName='" + appName + '\'' +
                ", appDesc='" + appDesc + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}