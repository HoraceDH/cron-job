package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2024-12-14 18:58:26.
 * <p>
 * 对应数据库表：t_executor
 * 
 * @author Horace 
 */
public class ExecutorEntity {
    /**
     * 主键，执行器地址 address
     */
    private String address;

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
     * 主机名 host_name
     */
    private String hostName;

    /**
     * 应用描述 app_desc
     */
    private String appDesc;

    /**
     * 执行器标签 tag
     */
    private String tag;

    /**
     * 执行器状态，1：在线，2：离线 state
     */
    private Integer state;

    /**
     * 执行器SDK版本 version
     */
    private String version;

    /**
     * 创建时间 create_time
     */
    private Date createTime;

    /**
     * 修改时间 modify_time
     */
    private Date modifyTime;

    /**
     * 主键，执行器地址
     * 
     * @return 主键，执行器地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 主键，执行器地址
     * 
     * @param address 主键，执行器地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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
     * 主机名
     * 
     * @return 主机名
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 主机名
     * 
     * @param hostName 主机名
     */
    public void setHostName(String hostName) {
        this.hostName = hostName == null ? null : hostName.trim();
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
     * 执行器标签
     * 
     * @return 执行器标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * 执行器标签
     * 
     * @param tag 执行器标签
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * 执行器状态，1：在线，2：离线
     * 
     * @return 执行器状态，1：在线，2：离线
     */
    public Integer getState() {
        return state;
    }

    /**
     * 执行器状态，1：在线，2：离线
     * 
     * @param state 执行器状态，1：在线，2：离线
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 执行器SDK版本
     * 
     * @return 执行器SDK版本
     */
    public String getVersion() {
        return version;
    }

    /**
     * 执行器SDK版本
     * 
     * @param version 执行器SDK版本
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
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
        sb.append(", address=").append(address);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", appId=").append(appId);
        sb.append(", appName=").append(appName);
        sb.append(", hostName=").append(hostName);
        sb.append(", appDesc=").append(appDesc);
        sb.append(", tag=").append(tag);
        sb.append(", state=").append(state);
        sb.append(", version=").append(version);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_executor
     */
    public static enum Column {
        address("address", "address", "VARCHAR", false),
        tenantId("tenant_id", "tenantId", "BIGINT", false),
        appId("app_id", "appId", "BIGINT", false),
        appName("app_name", "appName", "VARCHAR", false),
        hostName("host_name", "hostName", "VARCHAR", false),
        appDesc("app_desc", "appDesc", "VARCHAR", false),
        tag("tag", "tag", "VARCHAR", false),
        state("state", "state", "INTEGER", false),
        version("version", "version", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false);

        /**
         * t_executor
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_executor
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_executor
         */
        private final String column;

        /**
         * t_executor
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_executor
         */
        private final String javaProperty;

        /**
         * t_executor
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