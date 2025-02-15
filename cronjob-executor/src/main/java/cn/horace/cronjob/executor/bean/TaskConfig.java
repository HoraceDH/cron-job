package cn.horace.cronjob.executor.bean;

/**
 * 任务对象
 * <p>
 *
 * @author Horace
 */
public class TaskConfig {
    /**
     * 租户编码
     */
    private String tenant;
    /**
     * 方法名，类全限定名
     */
    private String method;
    /**
     * 任务名
     */
    private String name;
    /**
     * 应用名
     */
    private String appName;
    /**
     * 应用描述
     */
    private String appDesc;
    /**
     * 标签
     */
    private String tag;
    /**
     * 任务备注
     */
    private String remark;
    /**
     * cron表达式
     */
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
     * 过期时间，任务超过过期时间调度时，则走过期策略
     */
    private int expiredTime;
    /**
     * 失败策略，1：失败重试，2：失败丢弃
     */
    private int failureStrategy;
    /**
     * 任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒
     */
    private int timeout;
    /**
     * 失败最大重试次数，每次间隔10秒钟
     */
    private int maxRetryCount;
    /**
     * 失败重试间隔时间，毫秒
     */
    private int failureRetryInterval;

    public TaskConfig() {
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public int getRouterStrategy() {
        return routerStrategy;
    }

    public void setRouterStrategy(int routerStrategy) {
        this.routerStrategy = routerStrategy;
    }

    public int getExpiredStrategy() {
        return expiredStrategy;
    }

    public void setExpiredStrategy(int expiredStrategy) {
        this.expiredStrategy = expiredStrategy;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }

    public int getFailureStrategy() {
        return failureStrategy;
    }

    public void setFailureStrategy(int failureStrategy) {
        this.failureStrategy = failureStrategy;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public int getFailureRetryInterval() {
        return failureRetryInterval;
    }

    public void setFailureRetryInterval(int failureRetryInterval) {
        this.failureRetryInterval = failureRetryInterval;
    }

    @Override
    public String toString() {
        return "TaskConfig{" +
                "tenant='" + tenant + '\'' +
                ", method='" + method + '\'' +
                ", name='" + name + '\'' +
                ", appName='" + appName + '\'' +
                ", appDesc='" + appDesc + '\'' +
                ", tag='" + tag + '\'' +
                ", remark='" + remark + '\'' +
                ", cron='" + cron + '\'' +
                ", routerStrategy=" + routerStrategy +
                ", expiredStrategy=" + expiredStrategy +
                ", expiredTime=" + expiredTime +
                ", failureStrategy=" + failureStrategy +
                ", timeout=" + timeout +
                ", maxRetryCount=" + maxRetryCount +
                ", failureRetryInterval=" + failureRetryInterval +
                '}';
    }
}