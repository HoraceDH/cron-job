package cn.horace.cronjob.scheduler.bean.params;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author Horace
 */
@Data
@ToString
public class TaskRegisterParams {
    /**
     * 租户编码
     */
    @NotBlank
    private String tenant;
    /**
     * 方法名，类全限定名
     */
    @NotBlank
    private String method;
    /**
     * 任务名
     */
    @NotBlank
    private String name;
    /**
     * 任务负责人
     */
    private String owner;
    /**
     * 应用名
     */
    @NotBlank
    private String appName;
    /**
     * 应用描述
     */
    @NotBlank
    private String appDesc;
    /**
     * 标签
     */
    @NotBlank
    private String tag;
    /**
     * 任务备注
     */
    private String remark;
    /**
     * 默认的运行状态为1，状态值：1：启动，2：停止
     */
    private int runState;
    /**
     * cron表达式
     */
    @NotBlank
    private String cron;
    /**
     * 路由策略，1：随机策略，2：分片策略
     */
    private int routerStrategy;
    /**
     * 过期策略，1：过期丢弃，2：过期执行
     */
    private int expiredStrategy;
    /**
     * 过期时间，毫秒，默认3分钟过期，任务超过过期时间调度时，则走过期策略，最大过期时间5分钟
     */
    private int expiredTime;
    /**
     * 失败策略，1：失败重试，2：失败丢弃
     */
    private int failureStrategy;
    /**
     * 任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒，最大10秒钟
     */
    private int timeout;
    /**
     * 失败最大重试次数，每次间隔10秒钟
     */
    private int maxRetryCount;
    /**
     * 重试间隔时间，单位秒
     */
    private int failureRetryInterval;
}