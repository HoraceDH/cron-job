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
public class TaskItem {
    private String id;
    private String tenant;
    private String appName;
    private String appDesc;
    private String name;
    private String owner;
    private String cron;
    private String tag;
    private int runState;
    private String method;
    private int routerStrategy;
    private int expiredStrategy;
    private int expiredTime;
    private int failureStrategy;
    private int maxRetryCount;
    private int failureRetryInterval;
    private String params;
    private String remark;
    private String createTime;
    private String modifyTime;
}