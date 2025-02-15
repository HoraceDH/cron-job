package cn.horace.cronjob.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 *
 * @author Horace
 */
public class IPUtils {

    /**
     * 获取主机地址
     *
     * @param prefix
     * @return
     */
    public static String getLocalIpAddress(String prefix) {
        try {
            String localIp = System.getProperty("local.ip");
            if (localIp != null && !localIp.isEmpty()) {
                return localIp;
            }
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                Enumeration<InetAddress> addresses = interfaces.nextElement().getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();
                    String address = inetAddress.getHostAddress();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        if (StringUtils.isBlank(prefix)) {
                            return address;
                        } else if (address.startsWith(prefix)) {
                            return address;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取本机IP地址
     *
     * @return
     */
    public static String getDefaultAddress() {
        return getLocalIpAddress("");
    }

    /**
     * 获取IP地址
     *
     * @return
     */
    public static String getLocalIpAddress() {
        // 如果明确指定了本机IP，则使用本机IP
        String localIp = System.getProperty("local.ip");
        if (StringUtils.isNotBlank(localIp)) {
            return localIp;
        }

        String ip = getLocalIpAddress("10.16");
        if (ip == null) {
            ip = getLocalIpAddress("10.1");
        }
        if (ip == null) {
            ip = getLocalIpAddress("10.");
        }
        if (ip == null) {
            ip = getLocalIpAddress("192");
        }
        if (ip == null) {
            ip = getLocalIpAddress("172.17");
        }
        if (ip == null) {
            ip = getLocalIpAddress("172");
        }
        if (ip == null) {
            ip = getDefaultAddress();
        }
        return ip;
    }
}
