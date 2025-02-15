package cn.horace.cronjob.executor.bean;

import cn.horace.cronjob.commons.bean.TaskParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created in 2024-12-07 08:37.
 *
 * @author Horace
 */
public class DelayTask implements Delayed {
    private static final Logger logger = LoggerFactory.getLogger(DelayTask.class);
    private TaskParams task;

    public DelayTask(TaskParams task) {
        this.task = task;
    }

    public TaskParams getTask() {
        return task;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.calcDelayTime(), TimeUnit.MICROSECONDS);
    }

    /**
     * 计算延迟时间
     *
     * @return
     */
    private long calcDelayTime() {
        long currentTimeMillis = System.currentTimeMillis();
        long executionTime = task.getExecutionTime();
        return executionTime - currentTimeMillis;
    }

    @Override
    public int compareTo(Delayed o) {
        DelayTask delayTask = (DelayTask) o;
        long o1DelayTime = this.getDelay(TimeUnit.MICROSECONDS);
        long o2DelayTime = delayTask.getDelay(TimeUnit.MICROSECONDS);
        return (int) (o1DelayTime - o2DelayTime);
    }
}