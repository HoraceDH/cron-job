package cn.horace.cronjob.scheduler.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Horace
 */
public class IPUtils {
    private static List<String> REAL_IP_HEADERS = new ArrayList<>(20);

    static {
        // 在获取IP地址时，除开已配置好的精准匹配，还需要转换成大小后再做两次匹配，来解决上层多变的传递方式
        REAL_IP_HEADERS.add("X-Real-IP");
        REAL_IP_HEADERS.add("X_Real_IP");
        REAL_IP_HEADERS.add("X-Forwarded-For");
        REAL_IP_HEADERS.add("X_Forwarded_For");
        REAL_IP_HEADERS.add("Proxy-Client-IP");
        REAL_IP_HEADERS.add("Proxy_Client_IP");
        REAL_IP_HEADERS.add("WL-Proxy-Client-IP");
        REAL_IP_HEADERS.add("WL_Proxy_Client_IP");
        REAL_IP_HEADERS.add("Http-Client-IP");
        REAL_IP_HEADERS.add("Http_Client_IP");
        REAL_IP_HEADERS.add("Http-X-Forwarded-For");
        REAL_IP_HEADERS.add("Http_X_Forwarded_For");
    }

    /**
     * 获取用户的真实IP地址，通过预定义的各种Header头获取
     *
     * @param request
     * @return 可能会返回 null
     */
    public static String getRealIp(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException();
        }
        String realIp = null;
        for (String header : REAL_IP_HEADERS) {
            realIp = request.getHeader(header);
            if (StringUtils.isBlank(realIp)) {
                // 转成小写再获取一次
                realIp = request.getHeader(header.toLowerCase());
                if (StringUtils.isBlank(realIp)) {
                    // 转成大写再获取一次
                    realIp = request.getHeader(header.toUpperCase());
                }
            }
            // 如果已经获取到并且有效，则退出，这里会按照配置好的先后顺序逐个匹配
            if (StringUtils.isNotBlank(realIp)) {
                // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                if (realIp.contains(",")) {
                    realIp = realIp.split(",")[0];
                }
            }
            if (StringUtils.isNotBlank(realIp)) {
                break;
            }
        }

        // 如果没有走Nginx代理之类的，则从Http请求本身去拿
        if (StringUtils.isBlank(realIp)) {
            realIp = request.getRemoteAddr();
        }
//        if (isIpv4Valid(realIp)) {
//            return realIp;
//        }
        return realIp;
    }

    /**
     * 判断是否是合法的IPv4地址
     *
     * @param ip
     * @return
     */
    public static boolean isIpv4Valid(String ip) {
        if (StringUtils.isBlank(ip)) {
            return false;
        }

        String[] split = ip.split("\\.");
        if (split.length != 4) {
            return false;
        }
        try {
            long first = Long.parseLong(split[0]);
            long second = Long.parseLong(split[1]);
            long third = Long.parseLong(split[2]);
            long fourth = Long.parseLong(split[3]);
            return first < 256 && first > 0
                    && second < 256 && second >= 0
                    && third < 256 && third >= 0
                    && fourth < 256 && fourth >= 0;
        } catch (Exception e) {
            return false;
        }
    }
}