package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2025-01-13 18:31:39.530.
 * <p>
 * 对应数据库表：t_task
 * 
 * @author Horace 
 */
public class TaskEntity {
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
     * 任务名称 name
     */
    private String name;

    /**
     * 任务负责人 owner
     */
    private String owner;

    /**
     * cron表达式 cron
     */
    private String cron;

    /**
     * 任务标签 tag
     */
    private String tag;

    /**
     * 运行状态，1：启动，2：停止，3：未找到执行方法（停止） run_state
     */
    private Integer runState;

    /**
     * 任务方法，类全限定名 method
     */
    private String method;

    /**
     * 路由策略，1：随机策略，2：分片策略 router_strategy
     */
    private Integer routerStrategy;

    /**
     * 过期策略，1：过期丢弃，2：过期执行 expired_strategy
     */
    private Integer expiredStrategy;

    /**
     * 过期时间，毫秒，任务超过过期时间调度时，则走过期策略 expired_time
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
     * 失败重试间隔时间，毫秒 failure_retry_interval
     */
    private Integer failureRetryInterval;

    /**
     * 任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒 timeout
     */
    private Integer timeout;

    /**
     * 任务参数，一个JSON字符串，任务方法需要用String来接收 task_params
     */
    private String taskParams;

    /**
     * 描述 remark
     */
    private String remark;

    /**
     * 创建时间 create_time
     */
    private Date createTime;

    /**
     * 修改时间 modify_time
     */
    private Date modifyTime;

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
     * 任务名称
     * 
     * @return 任务名称
     */
    public String getName() {
        return name;
    }

    /**
     * 任务名称
     * 
     * @param name 任务名称
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
     * 运行状态，1：启动，2：停止，3：未找到执行方法（停止）
     * 
     * @return 运行状态，1：启动，2：停止，3：未找到执行方法（停止）
     */
    public Integer getRunState() {
        return runState;
    }

    /**
     * 运行状态，1：启动，2：停止，3：未找到执行方法（停止）
     * 
     * @param runState 运行状态，1：启动，2：停止，3：未找到执行方法（停止）
     */
    public void setRunState(Integer runState) {
        this.runState = runState;
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
     * 过期时间，毫秒，任务超过过期时间调度时，则走过期策略
     * 
     * @return 过期时间，毫秒，任务超过过期时间调度时，则走过期策略
     */
    public Integer getExpiredTime() {
        return expiredTime;
    }

    /**
     * 过期时间，毫秒，任务超过过期时间调度时，则走过期策略
     * 
     * @param expiredTime 过期时间，毫秒，任务超过过期时间调度时，则走过期策略
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
        sb.append(", cron=").append(cron);
        sb.append(", tag=").append(tag);
        sb.append(", runState=").append(runState);
        sb.append(", method=").append(method);
        sb.append(", routerStrategy=").append(routerStrategy);
        sb.append(", expiredStrategy=").append(expiredStrategy);
        sb.append(", expiredTime=").append(expiredTime);
        sb.append(", failureStrategy=").append(failureStrategy);
        sb.append(", maxRetryCount=").append(maxRetryCount);
        sb.append(", failureRetryInterval=").append(failureRetryInterval);
        sb.append(", timeout=").append(timeout);
        sb.append(", taskParams=").append(taskParams);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_task
     */
    public static enum Column {
        id("id", "id", "BIGINT", false),
        tenantId("tenant_id", "tenantId", "BIGINT", false),
        appId("app_id", "appId", "BIGINT", false),
        appName("app_name", "appName", "VARCHAR", false),
        appDesc("app_desc", "appDesc", "VARCHAR", false),
        name("name", "name", "VARCHAR", false),
        owner("owner", "owner", "VARCHAR", false),
        cron("cron", "cron", "VARCHAR", false),
        tag("tag", "tag", "VARCHAR", false),
        runState("run_state", "runState", "INTEGER", false),
        method("method", "method", "VARCHAR", false),
        routerStrategy("router_strategy", "routerStrategy", "INTEGER", false),
        expiredStrategy("expired_strategy", "expiredStrategy", "INTEGER", false),
        expiredTime("expired_time", "expiredTime", "INTEGER", false),
        failureStrategy("failure_strategy", "failureStrategy", "INTEGER", false),
        maxRetryCount("max_retry_count", "maxRetryCount", "INTEGER", false),
        failureRetryInterval("failure_retry_interval", "failureRetryInterval", "INTEGER", false),
        timeout("timeout", "timeout", "INTEGER", false),
        taskParams("task_params", "taskParams", "VARCHAR", false),
        remark("remark", "remark", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false);

        /**
         * t_task
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_task
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_task
         */
        private final String column;

        /**
         * t_task
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_task
         */
        private final String javaProperty;

        /**
         * t_task
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