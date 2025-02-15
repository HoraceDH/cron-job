package cn.horace.cronjob.executor.bean;

import cn.horace.cronjob.commons.bean.TaskParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可比较大小的任务
 * <p>
 *
 * @author Horace
 */
public class ComparableTask implements Comparable<ComparableTask> {
    private static final Logger logger = LoggerFactory.getLogger(ComparableTask.class);
    private TaskParams taskParams;

    public ComparableTask(TaskParams taskParams) {
        this.taskParams = taskParams;
    }

    public TaskParams getTask() {
        return taskParams;
    }

    /**
     * 按照顺序排，小的在前面
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(ComparableTask o) {
        return (int) (this.taskParams.getExecutionTime() - o.getTask().getExecutionTime());
    }

    @Override
    public String toString() {
        return "ComparableTask{" +
                "task=" + taskParams +
                '}';
    }
}