package cn.horace.cronjob.executor.service;

import cn.horace.cronjob.commons.bean.TaskParams;
import cn.horace.cronjob.executor.bean.DelayTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 任务调度器
 * <p>
 *
 * @author Horace
 */
public class DispatcherService {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherService.class);
    private final DelayQueue<DelayTask> taskQueue = new DelayQueue<>();
    private static final DispatcherService INSTANCE = new DispatcherService();
    private final TaskService taskService = TaskService.getInstance();
    private volatile boolean running = true;
    private volatile boolean completed = false;

    private DispatcherService() {
        Thread thread = new Thread(this::dispatcher);
        thread.setDaemon(true);
        thread.setName("dispatcher-task");
        thread.start();
    }

    /**
     * 是否正在运行
     *
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * 停止调度
     */
    public void stop() {
        this.running = false;
        while (!this.completed) {
            logger.info("waiting dispatcher complete...");
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static DispatcherService getInstance() {
        return INSTANCE;
    }

    /**
     * 任务调度
     */
    private void dispatcher() {
        // 如果是运行状态，或者队列里还有元素，则需要继续消费
        while (this.running || !this.taskQueue.isEmpty()) {
            try {
                DelayTask delayTask = taskQueue.poll(5, TimeUnit.SECONDS);
                if (delayTask != null) {
                    TaskParams taskParams = delayTask.getTask();
                    this.taskService.invoke(taskParams);
                }
            } catch (Exception e) {
                logger.error("dispatcher task error, msg:{}", e.getMessage(), e);
            }
        }
        this.completed = true;
        logger.info("dispatcher complete");
    }

    /**
     * 调度任务
     *
     * @param taskParams 任务对象
     * @return
     */
    public int add(TaskParams taskParams) {
        this.taskQueue.add(new DelayTask(taskParams));
        return this.taskQueue.size();
    }
}