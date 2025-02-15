package cn.horace.cronjob.scheduler.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class TaskLogItem {
    private String id;
    private String taskId;
    private String tenant;
    private String appName;
    private String method;
    private String taskName;
    private String owner;
    private String cron;
    private String executorAddress;
    private Integer elapsedTime;
    private String tag;
    private int exeType;
    private int routerStrategy;
    private int expiredStrategy;
    private int expiredTime;
    private int failureStrategy;
    private int maxRetryCount;
    private int retryCount;
    private int failureRetryInterval;
    private String schedulersAddress;
    private int state;
    private String params;
    private String remark;
    private String failedReason;
    private String firstExecutionTime;
    private String executionTime;
    private String dispatcherTime;
    private String realExecutionTime;
    private String createTime;
    private String modifyTime;
    private Integer delayTime;
    private int page;
    private int total;
}