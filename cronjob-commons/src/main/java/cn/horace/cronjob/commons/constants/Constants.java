package cn.horace.cronjob.commons.constants;

import cn.horace.cronjob.commons.utils.MavenUtils;


/**
 *
 * @author Horace
 */
public class Constants {
    /**
     * Root包路径
     */
    public static final String ROOT_PACKAGE = "cn.horace.cronjob.scheduler";

    /**
     * 执行器SDK包版本
     */
    public static String VERSION = MavenUtils.getMavenInfo("cn.horace.cronjob", "cronjob-executor", "cn.horace.cronjob.executor.CronJobExecutorClient").getVersion();
    /**
     * 日期格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
}