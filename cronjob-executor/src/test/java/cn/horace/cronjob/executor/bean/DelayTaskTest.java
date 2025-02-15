package cn.horace.cronjob.executor.bean;

import cn.horace.cronjob.commons.bean.TaskParams;
import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import cn.horace.cronjob.commons.utils.executor.GracefulThreadPoolExecutor;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created in 2024-12-07 10:40.
 *
 * @author Horace
 */
public class DelayTaskTest {
    private static final Logger logger = LoggerFactory.getLogger(DelayTaskTest.class);
    private int DISPATCHER_THREAD_INIT_SIZE = 2;
    private GracefulThreadPoolExecutor dispatcherExecutor = new GracefulThreadPoolExecutor(DISPATCHER_THREAD_INIT_SIZE, DISPATCHER_THREAD_INIT_SIZE, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1000), new DefaultThreadFactory(true, "dispatcher-task"), true);
    private final DelayQueue<DelayTask> taskQueue = new DelayQueue<>();
    private volatile boolean running = true;

    @Before
    public void init() {
        for (int i = 0; i < DISPATCHER_THREAD_INIT_SIZE; i++) {
            this.dispatcherExecutor.submit(this::dispatcher);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            running = false;
            logger.info("shutdown hook, running:{}", this.running);
        }));
    }

    private void dispatcher() {
        while (this.running) {
            try {
                DelayTask delayTask = taskQueue.poll(5, TimeUnit.SECONDS);
                if (delayTask != null) {
                    logger.info("get delay task:{}", delayTask.getTask());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testDelayTask() {
        // 原因是内部判断还没到时间时，会awaitNanos(getDelay())，如果getDelay返回的值是纳秒单位，那么就会睡眠时间很短，一直死循环一样导致CPU很高
        long delay = 50;
        System.out.println("millis = " + TimeUnit.NANOSECONDS.convert(delay, TimeUnit.MILLISECONDS));
        System.out.println("micros = " + TimeUnit.NANOSECONDS.convert(delay, TimeUnit.MICROSECONDS));
        System.out.println("nanos = " + TimeUnit.NANOSECONDS.convert(delay, TimeUnit.NANOSECONDS));

        long startTime = System.currentTimeMillis();
        TaskParams taskParams = new TaskParams();
        taskParams.setTaskLogId(1);
        taskParams.setExecutionTime(startTime + 2 * 1000);
        this.taskQueue.add(new DelayTask(taskParams));
        logger.info("add delay task:{}", taskParams);

        TaskParams taskParams1 = new TaskParams();
        taskParams1.setTaskLogId(1);
        taskParams1.setExecutionTime(startTime + 4 * 1000);
        this.taskQueue.add(new DelayTask(taskParams1));
        logger.info("add delay task:{}", taskParams1);

        LockSupport.parkNanos(TimeUnit.MINUTES.toNanos(10));
    }
}