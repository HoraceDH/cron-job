package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2025-03-01 20:03:00.771.
 * <p>
 * 对应数据库表：t_task_statistics
 * 
 * @author Horace 
 */
public class TaskStatisticsEntityKey {
    /**
     * 统计时间，分钟级 date_scale
     */
    private Date dateScale;

    /**
     * 任务ID task_id
     */
    private Long taskId;

    /**
     * 统计时间，分钟级
     * 
     * @return 统计时间，分钟级
     */
    public Date getDateScale() {
        return dateScale;
    }

    /**
     * 统计时间，分钟级
     * 
     * @param dateScale 统计时间，分钟级
     */
    public void setDateScale(Date dateScale) {
        this.dateScale = dateScale;
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
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dateScale=").append(dateScale);
        sb.append(", taskId=").append(taskId);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_task_statistics
     */
    public static enum Column {
        dateScale("date_scale", "dateScale", "TIMESTAMP", false),
        taskId("task_id", "taskId", "BIGINT", false),
        taskName("task_name", "taskName", "VARCHAR", false),
        schedulerSuccess("scheduler_success", "schedulerSuccess", "INTEGER", false),
        schedulerFailed("scheduler_failed", "schedulerFailed", "INTEGER", false),
        delayAvg("delay_avg", "delayAvg", "DOUBLE", false),
        delayMax("delay_max", "delayMax", "DOUBLE", false),
        delayMin("delay_min", "delayMin", "DOUBLE", false),
        elapsedAvg("elapsed_avg", "elapsedAvg", "DOUBLE", false),
        elapsedMax("elapsed_max", "elapsedMax", "DOUBLE", false),
        elapsedMin("elapsed_min", "elapsedMin", "DOUBLE", false),
        beforeAvg("before_avg", "beforeAvg", "DOUBLE", false),
        beforeMax("before_max", "beforeMax", "DOUBLE", false),
        beforeMin("before_min", "beforeMin", "DOUBLE", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false);

        /**
         * t_task_statistics
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_task_statistics
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_task_statistics
         */
        private final String column;

        /**
         * t_task_statistics
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_task_statistics
         */
        private final String javaProperty;

        /**
         * t_task_statistics
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