package cn.horace.cronjob.commons.bean;

import cn.horace.cronjob.commons.constants.TaskLogState;
import org.apache.commons.lang3.StringUtils;

/**
 * 任务执行结果
 * <p>
 *
 * @author Horace
 */
public class TaskResult {
    /**
     * 任务日志ID
     */
    private long taskLogId;
    /**
     * 任务ID
     */
    private long taskId;
    /**
     * 任务状态
     *
     * @see TaskLogState
     */
    private int state;
    /**
     * 失败原因
     */
    private String failedReason;
    /**
     * 任务实际执行时间
     */
    private long realExecutionTime;
    /**
     * 执行耗时
     */
    private int elapsedTime;
    /**
     * 执行器地址
     */
    private String address;

    public TaskResult() {
    }

    public long getTaskLogId() {
        return taskLogId;
    }

    public void setTaskLogId(long taskLogId) {
        this.taskLogId = taskLogId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public long getRealExecutionTime() {
        return realExecutionTime;
    }

    public void setRealExecutionTime(long realExecutionTime) {
        this.realExecutionTime = realExecutionTime;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static TaskResult success(long taskLogId, long taskId, int elapsedTime) {
        TaskResult result = new TaskResult();
        result.setTaskLogId(taskLogId);
        result.setTaskId(taskId);
        result.setElapsedTime(elapsedTime);
        result.setState(TaskLogState.EXECUTION_SUCCESS.getValue());
        return result;
    }

    public static TaskResult failed(long taskLogId, long taskId, TaskLogState state, String failedReason) {
        TaskResult result = new TaskResult();
        result.setTaskLogId(taskLogId);
        result.setTaskId(taskId);
        result.setState(state.getValue());
        if (StringUtils.isBlank(failedReason)) {
            failedReason = state.getMsg();
        }
        result.setFailedReason(failedReason);
        return result;
    }

    public static TaskResult failed(long taskLogId, long taskId, TaskLogState state) {
        return failed(taskLogId, taskId, state, null);
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "taskLogId=" + taskLogId +
                ", taskId=" + taskId +
                ", state=" + state +
                ", realExecutionTime=" + realExecutionTime +
                ", elapsedTime=" + elapsedTime +
                ", address='" + address + '\'' +
                ", failedReason='" + failedReason + '\'' +
                '}';
    }
}