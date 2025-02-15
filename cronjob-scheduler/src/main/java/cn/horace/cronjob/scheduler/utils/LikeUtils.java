package cn.horace.cronjob.scheduler.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Horace
 */
public class LikeUtils {
    private static final Logger logger = LoggerFactory.getLogger(LikeUtils.class);

    /**
     * 拼接Like字符串
     *
     * @param data 数据
     * @return
     */
    public static String toLikeString(String data) {
        return "%" + data + "%";
    }
}