package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2024-11-04 15:04:46.
 * <p>
 * 对应数据库表：t_app
 * 
 * @author Horace 
 */
public class AppEntity {
    /**
     * 主键 id
     */
    private Long id;

    /**
     * 租户ID tenant_id
     */
    private Long tenantId;

    /**
     * 应用名 app_name
     */
    private String appName;

    /**
     * 应用描述 app_desc
     */
    private String appDesc;

    /**
     * 0：正常，1：停用 state
     */
    private Integer state;

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
     * 0：正常，1：停用
     * 
     * @return 0：正常，1：停用
     */
    public Integer getState() {
        return state;
    }

    /**
     * 0：正常，1：停用
     * 
     * @param state 0：正常，1：停用
     */
    public void setState(Integer state) {
        this.state = state;
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
        sb.append(", appName=").append(appName);
        sb.append(", appDesc=").append(appDesc);
        sb.append(", state=").append(state);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_app
     */
    public static enum Column {
        id("id", "id", "BIGINT", false),
        tenantId("tenant_id", "tenantId", "BIGINT", false),
        appName("app_name", "appName", "VARCHAR", false),
        appDesc("app_desc", "appDesc", "VARCHAR", false),
        state("state", "state", "INTEGER", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false);

        /**
         * t_app
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_app
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_app
         */
        private final String column;

        /**
         * t_app
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_app
         */
        private final String javaProperty;

        /**
         * t_app
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