package cn.horace.cronjob.commons.httpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询字符串工具类
 *
 * @author Horace
 */
public class QueryStringUtils {
    private static Logger logger = LoggerFactory.getLogger(QueryStringUtils.class);

    /**
     * 将参数转换为查询字符串
     *
     * @param params 参数
     * @return 返回查询字符串
     */
    public static String toQueryString(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 将查询字符串转换为参数
     *
     * @param queryString 查询字符串
     * @return 返回参数
     */
    public static Map<String, String> toParams(String queryString) {
        Map<String, String> params = new HashMap<>();
        if (queryString != null) {
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }
}