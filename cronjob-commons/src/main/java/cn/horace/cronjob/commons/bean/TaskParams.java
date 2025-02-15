package cn.horace.cronjob.commons.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务参数
 *
 * @author Horace
 */
public class TaskParams {
    private static final Logger logger = LoggerFactory.getLogger(TaskParams.class);
    /**
     * 页码
     */
    private int page;

    /**
     * 总页数
     */
    private int total;

    /**
     * 任务日志ID
     */
    private long taskLogId;

    /**
     * 任务ID
     */
    private long taskId;

    /**
     * 任务方法，类全限定名
     */
    private String method;

    /**
     * 执行类型，0：表示常规任务调度，1：管理后台立即执行，2：过期执行
     */
    private int exeType;

    /**
     * cron表达式
     */
    private String cron;

    /**
     * 任务标签
     */
    private String tag;

    /**
     * 执行时间
     */
    private long executionTime;

    /**
     * 调度时间，这里表示接到调度任务的时间，并不是服务端记录的调度时间，查问题时也可以拿来与服务端做对比，服务端是先更新调度时间，再发起请求给执行器
     */
    private long receivedDispatcherTime = System.currentTimeMillis();

    /**
     * 自定义参数
     */
    private String params;

    public TaskParams() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getExeType() {
        return exeType;
    }

    public void setExeType(int exeType) {
        this.exeType = exeType;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public long getReceivedDispatcherTime() {
        return receivedDispatcherTime;
    }

    public void setReceivedDispatcherTime(long receivedDispatcherTime) {
        this.receivedDispatcherTime = receivedDispatcherTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "TaskParams{" +
                "page=" + page +
                ", total=" + total +
                ", taskLogId=" + taskLogId +
                ", taskId=" + taskId +
                ", method='" + method + '\'' +
                ", exeType=" + exeType +
                ", cron='" + cron + '\'' +
                ", tag='" + tag + '\'' +
                ", executionTime=" + executionTime +
                ", receivedDispatcherTime=" + receivedDispatcherTime +
                ", params='" + params + '\'' +
                '}';
    }
}