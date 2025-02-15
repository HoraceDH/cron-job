package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2024-11-09 23:26:30.
 * <p>
 * 对应数据库表：t_statistics
 * 
 * @author Horace 
 */
public class StatisticsEntity {
    /**
     * 统计时间，分钟级 date_scale
     */
    private Date dateScale;

    /**
     * 调度成功 scheduler_success
     */
    private Integer schedulerSuccess;

    /**
     * 调度失败 scheduler_failed
     */
    private Integer schedulerFailed;

    /**
     * 平均延迟 delay_avg
     */
    private Double delayAvg;

    /**
     * 最大延迟 delay_max
     */
    private Double delayMax;

    /**
     * 最小延迟 delay_min
     */
    private Double delayMin;

    /**
     * 平均耗时 elapsed_avg
     */
    private Double elapsedAvg;

    /**
     * 最大耗时 elapsed_max
     */
    private Double elapsedMax;

    /**
     * 最小耗时 elapsed_min
     */
    private Double elapsedMin;

    /**
     * 创建时间 create_time
     */
    private Date createTime;

    /**
     * 修改时间 modify_time
     */
    private Date modifyTime;

    /**
     * 平均提前调度时间，毫秒 before_avg
     */
    private Double beforeAvg;

    /**
     * 最大提前调度时间，毫秒 before_max
     */
    private Double beforeMax;

    /**
     * 最小提前调度时间，毫秒 before_min
     */
    private Double beforeMin;

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
     * 调度成功
     * 
     * @return 调度成功
     */
    public Integer getSchedulerSuccess() {
        return schedulerSuccess;
    }

    /**
     * 调度成功
     * 
     * @param schedulerSuccess 调度成功
     */
    public void setSchedulerSuccess(Integer schedulerSuccess) {
        this.schedulerSuccess = schedulerSuccess;
    }

    /**
     * 调度失败
     * 
     * @return 调度失败
     */
    public Integer getSchedulerFailed() {
        return schedulerFailed;
    }

    /**
     * 调度失败
     * 
     * @param schedulerFailed 调度失败
     */
    public void setSchedulerFailed(Integer schedulerFailed) {
        this.schedulerFailed = schedulerFailed;
    }

    /**
     * 平均延迟
     * 
     * @return 平均延迟
     */
    public Double getDelayAvg() {
        return delayAvg;
    }

    /**
     * 平均延迟
     * 
     * @param delayAvg 平均延迟
     */
    public void setDelayAvg(Double delayAvg) {
        this.delayAvg = delayAvg;
    }

    /**
     * 最大延迟
     * 
     * @return 最大延迟
     */
    public Double getDelayMax() {
        return delayMax;
    }

    /**
     * 最大延迟
     * 
     * @param delayMax 最大延迟
     */
    public void setDelayMax(Double delayMax) {
        this.delayMax = delayMax;
    }

    /**
     * 最小延迟
     * 
     * @return 最小延迟
     */
    public Double getDelayMin() {
        return delayMin;
    }

    /**
     * 最小延迟
     * 
     * @param delayMin 最小延迟
     */
    public void setDelayMin(Double delayMin) {
        this.delayMin = delayMin;
    }

    /**
     * 平均耗时
     * 
     * @return 平均耗时
     */
    public Double getElapsedAvg() {
        return elapsedAvg;
    }

    /**
     * 平均耗时
     * 
     * @param elapsedAvg 平均耗时
     */
    public void setElapsedAvg(Double elapsedAvg) {
        this.elapsedAvg = elapsedAvg;
    }

    /**
     * 最大耗时
     * 
     * @return 最大耗时
     */
    public Double getElapsedMax() {
        return elapsedMax;
    }

    /**
     * 最大耗时
     * 
     * @param elapsedMax 最大耗时
     */
    public void setElapsedMax(Double elapsedMax) {
        this.elapsedMax = elapsedMax;
    }

    /**
     * 最小耗时
     * 
     * @return 最小耗时
     */
    public Double getElapsedMin() {
        return elapsedMin;
    }

    /**
     * 最小耗时
     * 
     * @param elapsedMin 最小耗时
     */
    public void setElapsedMin(Double elapsedMin) {
        this.elapsedMin = elapsedMin;
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
     * 平均提前调度时间，毫秒
     * 
     * @return 平均提前调度时间，毫秒
     */
    public Double getBeforeAvg() {
        return beforeAvg;
    }

    /**
     * 平均提前调度时间，毫秒
     * 
     * @param beforeAvg 平均提前调度时间，毫秒
     */
    public void setBeforeAvg(Double beforeAvg) {
        this.beforeAvg = beforeAvg;
    }

    /**
     * 最大提前调度时间，毫秒
     * 
     * @return 最大提前调度时间，毫秒
     */
    public Double getBeforeMax() {
        return beforeMax;
    }

    /**
     * 最大提前调度时间，毫秒
     * 
     * @param beforeMax 最大提前调度时间，毫秒
     */
    public void setBeforeMax(Double beforeMax) {
        this.beforeMax = beforeMax;
    }

    /**
     * 最小提前调度时间，毫秒
     * 
     * @return 最小提前调度时间，毫秒
     */
    public Double getBeforeMin() {
        return beforeMin;
    }

    /**
     * 最小提前调度时间，毫秒
     * 
     * @param beforeMin 最小提前调度时间，毫秒
     */
    public void setBeforeMin(Double beforeMin) {
        this.beforeMin = beforeMin;
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
        sb.append(", schedulerSuccess=").append(schedulerSuccess);
        sb.append(", schedulerFailed=").append(schedulerFailed);
        sb.append(", delayAvg=").append(delayAvg);
        sb.append(", delayMax=").append(delayMax);
        sb.append(", delayMin=").append(delayMin);
        sb.append(", elapsedAvg=").append(elapsedAvg);
        sb.append(", elapsedMax=").append(elapsedMax);
        sb.append(", elapsedMin=").append(elapsedMin);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", beforeAvg=").append(beforeAvg);
        sb.append(", beforeMax=").append(beforeMax);
        sb.append(", beforeMin=").append(beforeMin);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_statistics
     */
    public static enum Column {
        dateScale("date_scale", "dateScale", "TIMESTAMP", false),
        schedulerSuccess("scheduler_success", "schedulerSuccess", "INTEGER", false),
        schedulerFailed("scheduler_failed", "schedulerFailed", "INTEGER", false),
        delayAvg("delay_avg", "delayAvg", "DOUBLE", false),
        delayMax("delay_max", "delayMax", "DOUBLE", false),
        delayMin("delay_min", "delayMin", "DOUBLE", false),
        elapsedAvg("elapsed_avg", "elapsedAvg", "DOUBLE", false),
        elapsedMax("elapsed_max", "elapsedMax", "DOUBLE", false),
        elapsedMin("elapsed_min", "elapsedMin", "DOUBLE", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false),
        beforeAvg("before_avg", "beforeAvg", "DOUBLE", false),
        beforeMax("before_max", "beforeMax", "DOUBLE", false),
        beforeMin("before_min", "beforeMin", "DOUBLE", false);

        /**
         * t_statistics
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_statistics
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_statistics
         */
        private final String column;

        /**
         * t_statistics
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_statistics
         */
        private final String javaProperty;

        /**
         * t_statistics
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