package cn.horace.cronjob.commons.utils.executor;

import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 可优雅关闭的线程池
 * <p>
 *
 * @author Horace
 */
public class GracefulThreadPoolExecutor extends ThreadPoolExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GracefulThreadPoolExecutor.class);

    public GracefulThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, boolean autoShutdown) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        if (autoShutdown) {
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownGracefully, "graceful-executor-shutdown-hook"));
        }
    }

    public GracefulThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, boolean autoShutdown) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        if (autoShutdown) {
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownGracefully, "graceful-executor-shutdown-hook"));
        }
    }

    public GracefulThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler, boolean autoShutdown) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        if (autoShutdown) {
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownGracefully, "graceful-executor-shutdown-hook"));
        }
    }

    public GracefulThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler, boolean autoShutdown) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        if (autoShutdown) {
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownGracefully, "graceful-executor-shutdown-hook"));
        }
    }

    /**
     * 优雅的关闭线程池，此方法一直阻塞，直到任务执行完
     * <p>
     * 1、拒绝接收新任务
     * 2、等待正在执行的和在队列的任务执行完
     * 3、关闭线程池
     */
    public void shutdownGracefully() {
        String name = "";
        ThreadFactory threadFactory = this.getThreadFactory();
        if (threadFactory instanceof DefaultThreadFactory) {
            name = ((DefaultThreadFactory) threadFactory).getNamePrefix();
        }

        try {
            // 停止接收新任务
            this.shutdown();

            // 等待已有任务执行完，如果线程池已关闭则返回true，否则返回false
            logger.info("start waiting for the task to finish, name:{}, executor:{}", name, this);
            while (!this.awaitTermination(1, TimeUnit.SECONDS)) {
                logger.info("waiting for the task to finish, name:{}, executor:{}", name, this);
            }
            logger.info("shutdown executor success full, name:{}, executor:{}", name, this);
        } catch (InterruptedException e) {
            logger.error("shutdown executor interrupted, name:{}, executor:{}, msg:{}", name, this, e.getMessage(), e);
        }
    }
}