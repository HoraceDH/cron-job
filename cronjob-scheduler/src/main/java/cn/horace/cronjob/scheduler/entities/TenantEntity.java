package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2025-03-18 23:00:21.932.
 * <p>
 * 对应数据库表：t_tenant
 * 
 * @author Horace 
 */
public class TenantEntity {
    /**
     * 主键 id
     */
    private Long id;

    /**
     * 租户名称 name
     */
    private String name;

    /**
     * 租户编码 tenant
     */
    private String tenant;

    /**
     * 0：不告警，1：飞书告警，2：企微告警，3：邮件告警 alarm_type
     */
    private Integer alarmType;

    /**
     * 告警配置，json格式 alarm_config
     */
    private String alarmConfig;

    /**
     * 租户描述 remark
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
     * 租户名称
     * 
     * @return 租户名称
     */
    public String getName() {
        return name;
    }

    /**
     * 租户名称
     * 
     * @param name 租户名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 租户编码
     * 
     * @return 租户编码
     */
    public String getTenant() {
        return tenant;
    }

    /**
     * 租户编码
     * 
     * @param tenant 租户编码
     */
    public void setTenant(String tenant) {
        this.tenant = tenant == null ? null : tenant.trim();
    }

    /**
     * 0：不告警，1：飞书告警，2：企微告警，3：邮件告警
     * 
     * @return 0：不告警，1：飞书告警，2：企微告警，3：邮件告警
     */
    public Integer getAlarmType() {
        return alarmType;
    }

    /**
     * 0：不告警，1：飞书告警，2：企微告警，3：邮件告警
     * 
     * @param alarmType 0：不告警，1：飞书告警，2：企微告警，3：邮件告警
     */
    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    /**
     * 告警配置，json格式
     * 
     * @return 告警配置，json格式
     */
    public String getAlarmConfig() {
        return alarmConfig;
    }

    /**
     * 告警配置，json格式
     * 
     * @param alarmConfig 告警配置，json格式
     */
    public void setAlarmConfig(String alarmConfig) {
        this.alarmConfig = alarmConfig == null ? null : alarmConfig.trim();
    }

    /**
     * 租户描述
     * 
     * @return 租户描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 租户描述
     * 
     * @param remark 租户描述
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
        sb.append(", name=").append(name);
        sb.append(", tenant=").append(tenant);
        sb.append(", alarmType=").append(alarmType);
        sb.append(", alarmConfig=").append(alarmConfig);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_tenant
     */
    public static enum Column {
        id("id", "id", "BIGINT", false),
        name("name", "name", "VARCHAR", false),
        tenant("tenant", "tenant", "VARCHAR", false),
        alarmType("alarm_type", "alarmType", "INTEGER", false),
        alarmConfig("alarm_config", "alarmConfig", "VARCHAR", false),
        remark("remark", "remark", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false);

        /**
         * t_tenant
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_tenant
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_tenant
         */
        private final String column;

        /**
         * t_tenant
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_tenant
         */
        private final String javaProperty;

        /**
         * t_tenant
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