package cn.horace.cronjob.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * Created in 2024-12-14 19:02.
 *
 * @author Horace
 */
public class HostUtils {
    private static final Logger logger = LoggerFactory.getLogger(HostUtils.class);

    /**
     * 获取本机主机名
     *
     * @return
     */
    public static String getHostName() {
        String hostName = "Unknown-Host";
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String temp = localHost.getHostName();
            if (StringUtils.isNotBlank(temp)) {
                hostName = temp;
            }
        } catch (Exception ignored) {
        }
        return hostName;
    }
}