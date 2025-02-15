package cn.horace.cronjob.commons.utils.executor;

import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author Horace
 */
public class GracefulScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GracefulScheduledThreadPoolExecutor.class);

    public GracefulScheduledThreadPoolExecutor(int corePoolSize, boolean autoShutdown) {
        super(corePoolSize);
        if (autoShutdown) {
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownGracefully, "graceful-executor-shutdown-hook"));
        }
    }

    public GracefulScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory, boolean autoShutdown) {
        super(corePoolSize, threadFactory);
        if (autoShutdown) {
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownGracefully, "graceful-executor-shutdown-hook"));
        }
    }

    public GracefulScheduledThreadPoolExecutor(int corePoolSize, RejectedExecutionHandler handler, boolean autoShutdown) {
        super(corePoolSize, handler);
        if (autoShutdown) {
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownGracefully, "graceful-executor-shutdown-hook"));
        }
    }

    public GracefulScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler, boolean autoShutdown) {
        super(corePoolSize, threadFactory, handler);
        if (autoShutdown) {
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdownGracefully, "graceful-executor-shutdown-hook"));
        }
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return super.scheduleAtFixedRate(new TaskRunnableWrapper(command), initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return super.schedule(new TaskRunnableWrapper(command), delay, unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return super.schedule(new TaskCallableWrapper<>(callable), delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return super.scheduleWithFixedDelay(new TaskRunnableWrapper(command), initialDelay, delay, unit);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(new TaskRunnableWrapper(command));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(new TaskRunnableWrapper(task));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(new TaskRunnableWrapper(task), result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(new TaskCallableWrapper<>(task));
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
            logger.info("shutdown scheduler executor success full, name:{}, executor:{}", name, this);
        } catch (InterruptedException e) {
            logger.error("shutdown scheduler executor interrupted, name:{}, executor:{}, msg:{}", name, this, e.getMessage(), e);
        }
    }

    /**
     * 任务包装器，目的是为了能够全局捕获异常
     */
    private class TaskRunnableWrapper implements Runnable {
        private final Runnable runnableTask;

        public TaskRunnableWrapper(Runnable runnableTask) {
            this.runnableTask = runnableTask;
        }

        @Override
        public void run() {
            try {
                this.runnableTask.run();
            } catch (Throwable e) {
                logger.error("run task error, msg:{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 任务包装器，目的是为了能够全局捕获异常
     */
    private class TaskCallableWrapper<T> implements Callable<T> {
        private final Callable<T> callableTask;

        public TaskCallableWrapper(Callable<T> callableTask) {
            this.callableTask = callableTask;
        }

        @Override
        public T call() throws Exception {
            try {
                return this.callableTask.call();
            } catch (Exception e) {
                logger.error("run task error, msg:{}", e.getMessage(), e);
            }
            return null;
        }
    }
}