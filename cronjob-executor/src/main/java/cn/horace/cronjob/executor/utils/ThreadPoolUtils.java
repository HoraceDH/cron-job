package cn.horace.cronjob.executor.utils;

import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import cn.horace.cronjob.commons.utils.executor.GracefulScheduledThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created in 2025-01-02 16:47.
 *
 * @author Horace
 */
public class ThreadPoolUtils {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolUtils.class);
    public static final GracefulScheduledThreadPoolExecutor threadScheduledExecutor = new GracefulScheduledThreadPoolExecutor(3, new DefaultThreadFactory(true, "scheduler-thread"), true);
}