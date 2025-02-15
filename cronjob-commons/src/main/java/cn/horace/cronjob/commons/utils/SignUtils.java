package cn.horace.cronjob.commons.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created in 2025-01-26 19:23.
 *
 * @author Horace
 */
public class SignUtils {
    private static final Logger logger = LoggerFactory.getLogger(SignUtils.class);

    /**
     * 将参数签名
     *
     * @param signKey 签名Key
     * @param token   token
     * @param times   时间戳
     * @param body    请求体
     * @param params  请求参数
     * @return
     */
    public static String sign(String signKey, String token, String times, String body, Map<String, String> params) {
        TreeMap<String, Object> paramsMap = new TreeMap<>();
        paramsMap.put("_signKey_", signKey);
        paramsMap.put("times", times);
        paramsMap.put("token", token);
        paramsMap.put("rb", body);
        paramsMap.putAll(params);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return DigestUtils.md5Hex(sb.toString());
    }
}