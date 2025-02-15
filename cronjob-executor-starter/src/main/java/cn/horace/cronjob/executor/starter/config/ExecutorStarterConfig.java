package cn.horace.cronjob.executor.starter.config;

/**
 * 配置对象
 * <p>
 * Created in 2024-11-24 17:40.
 *
 * @author Horace
 */
public class ExecutorStarterConfig extends BaseConfig {
    @Override
    public String toString() {
        return "ExecutorStarterConfig{" +
                "address='" + address + '\'' +
                ", tenant='" + tenant + '\'' +
                ", appName='" + appName + '\'' +
                ", appDesc='" + appDesc + '\'' +
                ", tag='" + tag + '\'' +
                ", signKey='" + signKey + '\'' +
                '}';
    }
}