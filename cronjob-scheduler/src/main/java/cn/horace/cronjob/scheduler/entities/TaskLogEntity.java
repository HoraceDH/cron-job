package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2025-03-02 16:53:50.758.
 * <p>
 * 对应数据库表：t_task_log
 * 
 * @author Horace 
 */
public class TaskLogEntity {
    /**
     * 主键 id
     */
    private Long id;

    /**
     * 租户ID tenant_id
     */
    private Long tenantId;

    /**
     * 应用ID app_id
     */
    private Long appId;

    /**
     * 应用名 app_name
     */
    private String appName;

    /**
     * 应用描述 app_desc
     */
    private String appDesc;

    /**
     * 任务名 name
     */
    private String name;

    /**
     * 任务负责人 owner
     */
    private String owner;

    /**
     * 任务ID task_id
     */
    private Long taskId;

    /**
     * 执行器地址 executor_address
     */
    private String executorAddress;

    /**
     * 主机名 executor_host_name
     */
    private String executorHostName;

    /**
     * 任务方法，类全限定名 method
     */
    private String method;

    /**
     * 执行类型，0：常规任务调度，1：管理后台立即执行，2：过期执行 exe_type
     */
    private Integer exeType;

    /**
     * 执行耗时 elapsed_time
     */
    private Integer elapsedTime;

    /**
     * 版本号 version
     */
    private Integer version;

    /**
     * cron表达式 cron
     */
    private String cron;

    /**
     * 任务标签 tag
     */
    private String tag;

    /**
     * 路由策略，1：随机策略，2：分片策略 router_strategy
     */
    private Integer routerStrategy;

    /**
     * 过期策略，1：过期丢弃，2：过期执行 expired_strategy
     */
    private Integer expiredStrategy;

    /**
     * 过期时间，任务超过过期时间调度时，则走过期策略 expired_time
     */
    private Integer expiredTime;

    /**
     * 失败策略，1：失败重试，2：失败丢弃 failure_strategy
     */
    private Integer failureStrategy;

    /**
     * 失败重试次数 max_retry_count
     */
    private Integer maxRetryCount;

    /**
     * 已经重试的次数 retry_count
     */
    private Integer retryCount;

    /**
     * 失败重试间隔时间，毫秒 failure_retry_interval
     */
    private Integer failureRetryInterval;

    /**
     * 任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒 timeout
     */
    private Integer timeout;

    /**
     * 调度器地址，表示这个任务由哪个调度器调度 scheduler_address
     */
    private String schedulerAddress;

    /**
     * 执行状态，1：初始化，2：队列中，3：调度中，4：执行成功，5：执行失败，6：取消执行（预生成日志后，任务取消等情况），7：任务过期（超过执行时间而未被调度），8：执行失败，已丢弃，9：执行失败，重试中，10：执行失败，未找到执行方法 state
     */
    private Integer state;

    /**
     * 任务参数，一个JSON字符串，任务方法需要用String来接收 task_params
     */
    private String taskParams;

    /**
     * 分片索引号，一般在分片任务场景用 page
     */
    private Integer page;

    /**
     * 分片总数，一般在分片任务场景用 total
     */
    private Integer total;

    /**
     * 父级ID，在分片或者广播的路由策略下使用 parent_id
     */
    private Long parentId;

    /**
     * 描述 remark
     */
    private String remark;

    /**
     * 任务执行时间 execution_time
     */
    private Date executionTime;

    /**
     * 调度的时间 dispatch_time
     */
    private Date dispatchTime;

    /**
     * 实际的任务执行时间 real_execution_time
     */
    private Date realExecutionTime;

    /**
     * 创建时间 create_time
     */
    private Date createTime;

    /**
     * 修改时间 modify_time
     */
    private Date modifyTime;

    /**
     * 失败原因 failed_reason
     */
    private String failedReason;

    /**
     * 主键
     * 
     * @return 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * 
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 租户ID
     * 
     * @return 租户ID
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * 租户ID
     * 
     * @param tenantId 租户ID
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 应用ID
     * 
     * @return 应用ID
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * 应用ID
     * 
     * @param appId 应用ID
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }

    /**
     * 应用名
     * 
     * @return 应用名
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 应用名
     * 
     * @param appName 应用名
     */
    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    /**
     * 应用描述
     * 
     * @return 应用描述
     */
    public String getAppDesc() {
        return appDesc;
    }

    /**
     * 应用描述
     * 
     * @param appDesc 应用描述
     */
    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc == null ? null : appDesc.trim();
    }

    /**
     * 任务名
     * 
     * @return 任务名
     */
    public String getName() {
        return name;
    }

    /**
     * 任务名
     * 
     * @param name 任务名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 任务负责人
     * 
     * @return 任务负责人
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 任务负责人
     * 
     * @param owner 任务负责人
     */
    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    /**
     * 任务ID
     * 
     * @return 任务ID
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * 任务ID
     * 
     * @param taskId 任务ID
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * 执行器地址
     * 
     * @return 执行器地址
     */
    public String getExecutorAddress() {
        return executorAddress;
    }

    /**
     * 执行器地址
     * 
     * @param executorAddress 执行器地址
     */
    public void setExecutorAddress(String executorAddress) {
        this.executorAddress = executorAddress == null ? null : executorAddress.trim();
    }

    /**
     * 主机名
     * 
     * @return 主机名
     */
    public String getExecutorHostName() {
        return executorHostName;
    }

    /**
     * 主机名
     * 
     * @param executorHostName 主机名
     */
    public void setExecutorHostName(String executorHostName) {
        this.executorHostName = executorHostName == null ? null : executorHostName.trim();
    }

    /**
     * 任务方法，类全限定名
     * 
     * @return 任务方法，类全限定名
     */
    public String getMethod() {
        return method;
    }

    /**
     * 任务方法，类全限定名
     * 
     * @param method 任务方法，类全限定名
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * 执行类型，0：常规任务调度，1：管理后台立即执行，2：过期执行
     * 
     * @return 执行类型，0：常规任务调度，1：管理后台立即执行，2：过期执行
     */
    public Integer getExeType() {
        return exeType;
    }

    /**
     * 执行类型，0：常规任务调度，1：管理后台立即执行，2：过期执行
     * 
     * @param exeType 执行类型，0：常规任务调度，1：管理后台立即执行，2：过期执行
     */
    public void setExeType(Integer exeType) {
        this.exeType = exeType;
    }

    /**
     * 执行耗时
     * 
     * @return 执行耗时
     */
    public Integer getElapsedTime() {
        return elapsedTime;
    }

    /**
     * 执行耗时
     * 
     * @param elapsedTime 执行耗时
     */
    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * 版本号
     * 
     * @return 版本号
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 版本号
     * 
     * @param version 版本号
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * cron表达式
     * 
     * @return cron表达式
     */
    public String getCron() {
        return cron;
    }

    /**
     * cron表达式
     * 
     * @param cron cron表达式
     */
    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    /**
     * 任务标签
     * 
     * @return 任务标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * 任务标签
     * 
     * @param tag 任务标签
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * 路由策略，1：随机策略，2：分片策略
     * 
     * @return 路由策略，1：随机策略，2：分片策略
     */
    public Integer getRouterStrategy() {
        return routerStrategy;
    }

    /**
     * 路由策略，1：随机策略，2：分片策略
     * 
     * @param routerStrategy 路由策略，1：随机策略，2：分片策略
     */
    public void setRouterStrategy(Integer routerStrategy) {
        this.routerStrategy = routerStrategy;
    }

    /**
     * 过期策略，1：过期丢弃，2：过期执行
     * 
     * @return 过期策略，1：过期丢弃，2：过期执行
     */
    public Integer getExpiredStrategy() {
        return expiredStrategy;
    }

    /**
     * 过期策略，1：过期丢弃，2：过期执行
     * 
     * @param expiredStrategy 过期策略，1：过期丢弃，2：过期执行
     */
    public void setExpiredStrategy(Integer expiredStrategy) {
        this.expiredStrategy = expiredStrategy;
    }

    /**
     * 过期时间，任务超过过期时间调度时，则走过期策略
     * 
     * @return 过期时间，任务超过过期时间调度时，则走过期策略
     */
    public Integer getExpiredTime() {
        return expiredTime;
    }

    /**
     * 过期时间，任务超过过期时间调度时，则走过期策略
     * 
     * @param expiredTime 过期时间，任务超过过期时间调度时，则走过期策略
     */
    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    /**
     * 失败策略，1：失败重试，2：失败丢弃
     * 
     * @return 失败策略，1：失败重试，2：失败丢弃
     */
    public Integer getFailureStrategy() {
        return failureStrategy;
    }

    /**
     * 失败策略，1：失败重试，2：失败丢弃
     * 
     * @param failureStrategy 失败策略，1：失败重试，2：失败丢弃
     */
    public void setFailureStrategy(Integer failureStrategy) {
        this.failureStrategy = failureStrategy;
    }

    /**
     * 失败重试次数
     * 
     * @return 失败重试次数
     */
    public Integer getMaxRetryCount() {
        return maxRetryCount;
    }

    /**
     * 失败重试次数
     * 
     * @param maxRetryCount 失败重试次数
     */
    public void setMaxRetryCount(Integer maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    /**
     * 已经重试的次数
     * 
     * @return 已经重试的次数
     */
    public Integer getRetryCount() {
        return retryCount;
    }

    /**
     * 已经重试的次数
     * 
     * @param retryCount 已经重试的次数
     */
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * 失败重试间隔时间，毫秒
     * 
     * @return 失败重试间隔时间，毫秒
     */
    public Integer getFailureRetryInterval() {
        return failureRetryInterval;
    }

    /**
     * 失败重试间隔时间，毫秒
     * 
     * @param failureRetryInterval 失败重试间隔时间，毫秒
     */
    public void setFailureRetryInterval(Integer failureRetryInterval) {
        this.failureRetryInterval = failureRetryInterval;
    }

    /**
     * 任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒
     * 
     * @return 任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * 任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒
     * 
     * @param timeout 任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒
     */
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    /**
     * 调度器地址，表示这个任务由哪个调度器调度
     * 
     * @return 调度器地址，表示这个任务由哪个调度器调度
     */
    public String getSchedulerAddress() {
        return schedulerAddress;
    }

    /**
     * 调度器地址，表示这个任务由哪个调度器调度
     * 
     * @param schedulerAddress 调度器地址，表示这个任务由哪个调度器调度
     */
    public void setSchedulerAddress(String schedulerAddress) {
        this.schedulerAddress = schedulerAddress == null ? null : schedulerAddress.trim();
    }

    /**
     * 执行状态，1：初始化，2：队列中，3：调度中，4：执行成功，5：执行失败，6：取消执行（预生成日志后，任务取消等情况），7：任务过期（超过执行时间而未被调度），8：执行失败，已丢弃，9：执行失败，重试中，10：执行失败，未找到执行方法
     * 
     * @return 执行状态，1：初始化，2：队列中，3：调度中，4：执行成功，5：执行失败，6：取消执行（预生成日志后，任务取消等情况），7：任务过期（超过执行时间而未被调度），8：执行失败，已丢弃，9：执行失败，重试中，10：执行失败，未找到执行方法
     */
    public Integer getState() {
        return state;
    }

    /**
     * 执行状态，1：初始化，2：队列中，3：调度中，4：执行成功，5：执行失败，6：取消执行（预生成日志后，任务取消等情况），7：任务过期（超过执行时间而未被调度），8：执行失败，已丢弃，9：执行失败，重试中，10：执行失败，未找到执行方法
     * 
     * @param state 执行状态，1：初始化，2：队列中，3：调度中，4：执行成功，5：执行失败，6：取消执行（预生成日志后，任务取消等情况），7：任务过期（超过执行时间而未被调度），8：执行失败，已丢弃，9：执行失败，重试中，10：执行失败，未找到执行方法
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 任务参数，一个JSON字符串，任务方法需要用String来接收
     * 
     * @return 任务参数，一个JSON字符串，任务方法需要用String来接收
     */
    public String getTaskParams() {
        return taskParams;
    }

    /**
     * 任务参数，一个JSON字符串，任务方法需要用String来接收
     * 
     * @param taskParams 任务参数，一个JSON字符串，任务方法需要用String来接收
     */
    public void setTaskParams(String taskParams) {
        this.taskParams = taskParams == null ? null : taskParams.trim();
    }

    /**
     * 分片索引号，一般在分片任务场景用
     * 
     * @return 分片索引号，一般在分片任务场景用
     */
    public Integer getPage() {
        return page;
    }

    /**
     * 分片索引号，一般在分片任务场景用
     * 
     * @param page 分片索引号，一般在分片任务场景用
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 分片总数，一般在分片任务场景用
     * 
     * @return 分片总数，一般在分片任务场景用
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 分片总数，一般在分片任务场景用
     * 
     * @param total 分片总数，一般在分片任务场景用
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 父级ID，在分片或者广播的路由策略下使用
     * 
     * @return 父级ID，在分片或者广播的路由策略下使用
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 父级ID，在分片或者广播的路由策略下使用
     * 
     * @param parentId 父级ID，在分片或者广播的路由策略下使用
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 描述
     * 
     * @return 描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 描述
     * 
     * @param remark 描述
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 任务执行时间
     * 
     * @return 任务执行时间
     */
    public Date getExecutionTime() {
        return executionTime;
    }

    /**
     * 任务执行时间
     * 
     * @param executionTime 任务执行时间
     */
    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * 调度的时间
     * 
     * @return 调度的时间
     */
    public Date getDispatchTime() {
        return dispatchTime;
    }

    /**
     * 调度的时间
     * 
     * @param dispatchTime 调度的时间
     */
    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    /**
     * 实际的任务执行时间
     * 
     * @return 实际的任务执行时间
     */
    public Date getRealExecutionTime() {
        return realExecutionTime;
    }

    /**
     * 实际的任务执行时间
     * 
     * @param realExecutionTime 实际的任务执行时间
     */
    public void setRealExecutionTime(Date realExecutionTime) {
        this.realExecutionTime = realExecutionTime;
    }

    /**
     * 创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * 
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     * 
     * @return 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 修改时间
     * 
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 失败原因
     * 
     * @return 失败原因
     */
    public String getFailedReason() {
        return failedReason;
    }

    /**
     * 失败原因
     * 
     * @param failedReason 失败原因
     */
    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason == null ? null : failedReason.trim();
    }

    /**
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", appId=").append(appId);
        sb.append(", appName=").append(appName);
        sb.append(", appDesc=").append(appDesc);
        sb.append(", name=").append(name);
        sb.append(", owner=").append(owner);
        sb.append(", taskId=").append(taskId);
        sb.append(", executorAddress=").append(executorAddress);
        sb.append(", executorHostName=").append(executorHostName);
        sb.append(", method=").append(method);
        sb.append(", exeType=").append(exeType);
        sb.append(", elapsedTime=").append(elapsedTime);
        sb.append(", version=").append(version);
        sb.append(", cron=").append(cron);
        sb.append(", tag=").append(tag);
        sb.append(", routerStrategy=").append(routerStrategy);
        sb.append(", expiredStrategy=").append(expiredStrategy);
        sb.append(", expiredTime=").append(expiredTime);
        sb.append(", failureStrategy=").append(failureStrategy);
        sb.append(", maxRetryCount=").append(maxRetryCount);
        sb.append(", retryCount=").append(retryCount);
        sb.append(", failureRetryInterval=").append(failureRetryInterval);
        sb.append(", timeout=").append(timeout);
        sb.append(", schedulerAddress=").append(schedulerAddress);
        sb.append(", state=").append(state);
        sb.append(", taskParams=").append(taskParams);
        sb.append(", page=").append(page);
        sb.append(", total=").append(total);
        sb.append(", parentId=").append(parentId);
        sb.append(", remark=").append(remark);
        sb.append(", executionTime=").append(executionTime);
        sb.append(", dispatchTime=").append(dispatchTime);
        sb.append(", realExecutionTime=").append(realExecutionTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", failedReason=").append(failedReason);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_task_log
     */
    public static enum Column {
        id("id", "id", "BIGINT", false),
        tenantId("tenant_id", "tenantId", "BIGINT", false),
        appId("app_id", "appId", "BIGINT", false),
        appName("app_name", "appName", "VARCHAR", false),
        appDesc("app_desc", "appDesc", "VARCHAR", false),
        name("name", "name", "VARCHAR", false),
        owner("owner", "owner", "VARCHAR", false),
        taskId("task_id", "taskId", "BIGINT", false),
        executorAddress("executor_address", "executorAddress", "VARCHAR", false),
        executorHostName("executor_host_name", "executorHostName", "VARCHAR", false),
        method("method", "method", "VARCHAR", false),
        exeType("exe_type", "exeType", "INTEGER", false),
        elapsedTime("elapsed_time", "elapsedTime", "INTEGER", false),
        version("version", "version", "INTEGER", false),
        cron("cron", "cron", "VARCHAR", false),
        tag("tag", "tag", "VARCHAR", false),
        routerStrategy("router_strategy", "routerStrategy", "INTEGER", false),
        expiredStrategy("expired_strategy", "expiredStrategy", "INTEGER", false),
        expiredTime("expired_time", "expiredTime", "INTEGER", false),
        failureStrategy("failure_strategy", "failureStrategy", "INTEGER", false),
        maxRetryCount("max_retry_count", "maxRetryCount", "INTEGER", false),
        retryCount("retry_count", "retryCount", "INTEGER", false),
        failureRetryInterval("failure_retry_interval", "failureRetryInterval", "INTEGER", false),
        timeout("timeout", "timeout", "INTEGER", false),
        schedulerAddress("scheduler_address", "schedulerAddress", "VARCHAR", false),
        state("state", "state", "INTEGER", false),
        taskParams("task_params", "taskParams", "VARCHAR", false),
        page("page", "page", "INTEGER", false),
        total("total", "total", "INTEGER", false),
        parentId("parent_id", "parentId", "BIGINT", false),
        remark("remark", "remark", "VARCHAR", false),
        executionTime("execution_time", "executionTime", "TIMESTAMP", false),
        dispatchTime("dispatch_time", "dispatchTime", "TIMESTAMP", false),
        realExecutionTime("real_execution_time", "realExecutionTime", "TIMESTAMP", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false),
        failedReason("failed_reason", "failedReason", "LONGVARCHAR", false);

        /**
         * t_task_log
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_task_log
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_task_log
         */
        private final String column;

        /**
         * t_task_log
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_task_log
         */
        private final String javaProperty;

        /**
         * t_task_log
         */
        private final String jdbcType;

        /**
         */
        public String value() {
            return this.column;
        }

        /**
         */
        public String getValue() {
            return this.column;
        }

        /**
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         */
        public static Column[] all() {
            return Column.values();
        }

        /**
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        /**
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}