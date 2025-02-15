package cn.horace.cronjob.commons.utils;

import cn.horace.cronjob.commons.bean.MavenInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maven工具类，可以获取maven包的元数据信息
 * <p>
 *
 * @author Horace
 */
public class MavenUtils {
    private static final Logger logger = LoggerFactory.getLogger(MavenUtils.class);
    private static ConcurrentHashMap<String, MavenInfo> mavenInfos = new ConcurrentHashMap<>();

    /**
     * 获取maven信息
     *
     * @param groupId    包路径
     * @param artifactId 模块名
     * @param className  Jar内任意一个Class全类名
     * @return
     */
    public static MavenInfo getMavenInfo(String groupId, String artifactId, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return getMavenInfo(groupId, artifactId, clazz);
        } catch (ClassNotFoundException e) {
            logger.debug("get maven info failed, class not found, className:{}", className);
        }
        return new MavenInfo(groupId, artifactId, "", "", "", "");
    }

    /**
     * 获取Maven信息
     *
     * @param groupId    包路径
     * @param artifactId 模块名
     * @param clazz      Jar内任意一个Class对象
     * @return
     */
    public static MavenInfo getMavenInfo(String groupId, String artifactId, Class<?> clazz) {
        String key = groupId + ":" + artifactId;
        MavenInfo mavenInfo = mavenInfos.get(key);
        if (mavenInfo != null) {
            return mavenInfo;
        }
        synchronized (MavenUtils.class) {
            mavenInfo = mavenInfos.get(key);
            if (mavenInfo != null) {
                return mavenInfo;
            }
        }

        // 解析文件并获取信息
        String version = "latest-version";
        String buildJdk = "";
        String createBy = "";
        String mavenVersion = "";
        try {
            String jarPath = getJarPath(clazz);
            // 在IDEA中运行，则不做解析
            if (!jarPath.endsWith("/target/classes/")) {
                version = getVersion(clazz, groupId, artifactId);
                URL url = new URL(getManifestPropertiesFile(getJarPath(clazz)));
                InputStream inputStream = url.openStream();
                Properties properties = new Properties();
                properties.load(inputStream);
                buildJdk = properties.getProperty("Build-Jdk");
                createBy = properties.getProperty("Built-By");
                mavenVersion = properties.getProperty("Created-By");
                inputStream.close();
            }
        } catch (Exception e) {
            logger.warn("get maven info error, clazz:{}, groupId:{}, artifactId:{}, msg:{}", clazz, groupId, artifactId, e.getMessage(), e);
        }
        mavenInfo = new MavenInfo(groupId, artifactId, version, buildJdk, createBy, mavenVersion);
        mavenInfos.put(key, mavenInfo);
        return mavenInfo;
    }

    /**
     * 获取版本号
     *
     * @param clazz      Jar内任意一个Class类对象
     * @param groupId    组ID
     * @param artifactId 工件ID
     * @return
     * @throws IOException
     */
    private static String getVersion(Class<?> clazz, String groupId, String artifactId) throws IOException {
        String version;
        URL url = new URL(getPomPropertiesFile(getJarPath(clazz), groupId, artifactId));
        InputStream inputStream = url.openStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        version = properties.getProperty("version");
        inputStream.close();
        return version;
    }

    /**
     * 获取Jar文件路径
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getJarPath(Class<?> clazz) throws UnsupportedEncodingException {
        String jarPath = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        jarPath = java.net.URLDecoder.decode(jarPath, "UTF-8");
        return jarPath;
    }

    /**
     * 获取pom.properties文件路径
     *
     * @param jarPath    Jar路径
     * @param groupId    组ID
     * @param artifactId 工件ID
     * @return
     */
    private static String getPomPropertiesFile(String jarPath, String groupId, String artifactId) {
        StringBuilder sb = new StringBuilder();
        sb.append("jar:file:");
        sb.append(jarPath);
        sb.append("!/META-INF/maven/");
        sb.append(groupId);
        sb.append("/");
        sb.append(artifactId);
        sb.append("/pom.properties");
        return sb.toString();
    }

    /**
     * 获取pom.properties文件路径
     *
     * @param jarPath Jar路径
     * @return
     */
    private static String getManifestPropertiesFile(String jarPath) {
        StringBuilder sb = new StringBuilder();
        sb.append("jar:file:");
        sb.append(jarPath);
        sb.append("!/META-INF/MANIFEST.MF");
        return sb.toString();
    }

    /**
     * 是否是windows系统
     *
     * @return
     */
    private static boolean isWindows() {
        String osName = System.getProperty("os.name");
        if (osName != null && osName.toLowerCase().indexOf("win") >= 0) {
            return true;
        }
        return false;
    }
}