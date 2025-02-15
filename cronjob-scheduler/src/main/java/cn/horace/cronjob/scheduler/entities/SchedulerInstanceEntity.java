package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2024-12-14 19:34:41.
 * <p>
 * 对应数据库表：t_scheduler_instance
 * 
 * @author Horace 
 */
public class SchedulerInstanceEntity {
    /**
     * 调度器服务ID id
     */
    private Integer id;

    /**
     * 调度器状态，1：在线，2：离线 state
     */
    private Integer state;

    /**
     * 主机地址，例如：localhost:9527 address
     */
    private String address;

    /**
     * 创建时间 create_time
     */
    private Date createTime;

    /**
     * 修改时间 modify_time
     */
    private Date modifyTime;

    /**
     * 主机名 host_name
     */
    private String hostName;

    /**
     * 调度器服务ID
     * 
     * @return 调度器服务ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 调度器服务ID
     * 
     * @param id 调度器服务ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 调度器状态，1：在线，2：离线
     * 
     * @return 调度器状态，1：在线，2：离线
     */
    public Integer getState() {
        return state;
    }

    /**
     * 调度器状态，1：在线，2：离线
     * 
     * @param state 调度器状态，1：在线，2：离线
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 主机地址，例如：localhost:9527
     * 
     * @return 主机地址，例如：localhost:9527
     */
    public String getAddress() {
        return address;
    }

    /**
     * 主机地址，例如：localhost:9527
     * 
     * @param address 主机地址，例如：localhost:9527
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", state=").append(state);
        sb.append(", address=").append(address);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", hostName=").append(hostName);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_scheduler_instance
     */
    public static enum Column {
        id("id", "id", "INTEGER", false),
        state("state", "state", "INTEGER", false),
        address("address", "address", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false),
        hostName("host_name", "hostName", "VARCHAR", false);

        /**
         * t_scheduler_instance
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_scheduler_instance
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_scheduler_instance
         */
        private final String column;

        /**
         * t_scheduler_instance
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_scheduler_instance
         */
        private final String javaProperty;

        /**
         * t_scheduler_instance
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