package cn.horace.cronjob.scheduler.bean;

import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可比较大小的任务
 * <p>
 *
 * @author Horace
 */
public class ComparableTaskLog implements Comparable<ComparableTaskLog> {
    private static final Logger logger = LoggerFactory.getLogger(ComparableTaskLog.class);
    private TaskLogEntity entity;

    public ComparableTaskLog(TaskLogEntity entity) {
        this.entity = entity;
    }

    public TaskLogEntity getEntity() {
        return entity;
    }

    /**
     * 按照顺序排，小的在前面
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(ComparableTaskLog o) {
        return (int) (this.entity.getExecutionTime().getTime() - o.getEntity().getExecutionTime().getTime());
    }

    @Override
    public String toString() {
        return "ComparableTaskLog{" +
                "entity=" + entity +
                '}';
    }
}