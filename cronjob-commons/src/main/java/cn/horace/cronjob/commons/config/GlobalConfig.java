package cn.horace.cronjob.commons.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一些全局配置
 *
 * @author Horace
 */
public class GlobalConfig {
    private static final Logger logger = LoggerFactory.getLogger(GlobalConfig.class);
    private static volatile Map<String, Object> CONFIG = new ConcurrentHashMap<>();
    private static String LOG_ENABLE = "logger-enable";

    /**
     * 获取全局日志开关
     *
     * @return
     */
    public static boolean isEnabledLog() {
        Object value = CONFIG.get(LOG_ENABLE);
        if (value == null) {
            value = getFromSystemProperty(LOG_ENABLE);
            CONFIG.put(LOG_ENABLE, value);
        }
        try {
            return Boolean.parseBoolean(value.toString());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 从系统参数获取配置值
     *
     * @param key 配置值
     * @return
     */
    private static Object getFromSystemProperty(String key) {
        // 从系统参数获取
        String property = System.getProperty(LOG_ENABLE);
        if (StringUtils.isBlank(property)) {
            return true;
        }
        try {
            return Boolean.parseBoolean(property);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 设置日志开关
     *
     * @param value 开关值
     */
    public static void setLogEnable(boolean value) {
        CONFIG.put(LOG_ENABLE, value);
    }
}