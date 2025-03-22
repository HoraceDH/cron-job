package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2025-03-22 13:08:27.880.
 * <p>
 * 对应数据库表：t_alarm
 * 
 * @author Horace 
 */
public class AlarmEntity {
    /**
     * 主键 id
     */
    private Long id;

    /**
     * 任务日志ID task_log_id
     */
    private Long taskLogId;

    /**
     * 应用名 app_name
     */
    private String appName;

    /**
     * 任务名 task_name
     */
    private String taskName;

    /**
     * 执行器地址 executor_address
     */
    private String executorAddress;

    /**
     * 执行器主机名 executor_host_name
     */
    private String executorHostName;

    /**
     * 任务方法，类全限定名 method
     */
    private String method;

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
     * 任务日志ID
     * 
     * @return 任务日志ID
     */
    public Long getTaskLogId() {
        return taskLogId;
    }

    /**
     * 任务日志ID
     * 
     * @param taskLogId 任务日志ID
     */
    public void setTaskLogId(Long taskLogId) {
        this.taskLogId = taskLogId;
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
     * 任务名
     * 
     * @return 任务名
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 任务名
     * 
     * @param taskName 任务名
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
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
     * 执行器主机名
     * 
     * @return 执行器主机名
     */
    public String getExecutorHostName() {
        return executorHostName;
    }

    /**
     * 执行器主机名
     * 
     * @param executorHostName 执行器主机名
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
        sb.append(", taskLogId=").append(taskLogId);
        sb.append(", appName=").append(appName);
        sb.append(", taskName=").append(taskName);
        sb.append(", executorAddress=").append(executorAddress);
        sb.append(", executorHostName=").append(executorHostName);
        sb.append(", method=").append(method);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_alarm
     */
    public static enum Column {
        id("id", "id", "BIGINT", false),
        taskLogId("task_log_id", "taskLogId", "BIGINT", false),
        appName("app_name", "appName", "VARCHAR", false),
        taskName("task_name", "taskName", "VARCHAR", false),
        executorAddress("executor_address", "executorAddress", "VARCHAR", false),
        executorHostName("executor_host_name", "executorHostName", "VARCHAR", false),
        method("method", "method", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false);

        /**
         * t_alarm
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_alarm
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_alarm
         */
        private final String column;

        /**
         * t_alarm
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_alarm
         */
        private final String javaProperty;

        /**
         * t_alarm
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