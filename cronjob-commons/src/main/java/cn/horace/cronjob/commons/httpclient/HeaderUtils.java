package cn.horace.cronjob.commons.httpclient;

import cn.horace.cronjob.commons.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Horace
 */
public class HeaderUtils {
    private static final Logger logger = LoggerFactory.getLogger(HeaderUtils.class);

    /**
     * 获取公共Header头
     *
     * @return
     */
    public static Map<String, String> getCommonHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "CronJob-Java-SDK");
        headers.put("Content-Type", "application/json");
        headers.put("SDK-Version", Constants.VERSION);
        return headers;
    }
}