package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2024-07-27 12:24:48.
 * <p>
 * 对应数据库表：t_locks
 * 
 * @author Horace 
 */
public class LocksEntity {
    /**
     * 锁ID lock_id
     */
    private String lockId;

    /**
     * 锁的所有者 lock_owner
     */
    private String lockOwner;

    /**
     * 锁状态，1:已加锁，2:已释放，参考LockState枚举 lock_state
     */
    private Integer lockState;

    /**
     * 锁的过期时间 expire_time
     */
    private Date expireTime;

    /**
     * 锁的描述信息 lock_desc
     */
    private String lockDesc;

    /**
     * 创建时间 create_time
     */
    private Date createTime;

    /**
     * 修改时间 modify_time
     */
    private Date modifyTime;

    /**
     * 锁ID
     * 
     * @return 锁ID
     */
    public String getLockId() {
        return lockId;
    }

    /**
     * 锁ID
     * 
     * @param lockId 锁ID
     */
    public void setLockId(String lockId) {
        this.lockId = lockId == null ? null : lockId.trim();
    }

    /**
     * 锁的所有者
     * 
     * @return 锁的所有者
     */
    public String getLockOwner() {
        return lockOwner;
    }

    /**
     * 锁的所有者
     * 
     * @param lockOwner 锁的所有者
     */
    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner == null ? null : lockOwner.trim();
    }

    /**
     * 锁状态，1:已加锁，2:已释放，参考LockState枚举
     * 
     * @return 锁状态，1:已加锁，2:已释放，参考LockState枚举
     */
    public Integer getLockState() {
        return lockState;
    }

    /**
     * 锁状态，1:已加锁，2:已释放，参考LockState枚举
     * 
     * @param lockState 锁状态，1:已加锁，2:已释放，参考LockState枚举
     */
    public void setLockState(Integer lockState) {
        this.lockState = lockState;
    }

    /**
     * 锁的过期时间
     * 
     * @return 锁的过期时间
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * 锁的过期时间
     * 
     * @param expireTime 锁的过期时间
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 锁的描述信息
     * 
     * @return 锁的描述信息
     */
    public String getLockDesc() {
        return lockDesc;
    }

    /**
     * 锁的描述信息
     * 
     * @param lockDesc 锁的描述信息
     */
    public void setLockDesc(String lockDesc) {
        this.lockDesc = lockDesc == null ? null : lockDesc.trim();
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
        sb.append(", lockId=").append(lockId);
        sb.append(", lockOwner=").append(lockOwner);
        sb.append(", lockState=").append(lockState);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", lockDesc=").append(lockDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_locks
     */
    public static enum Column {
        lockId("lock_id", "lockId", "VARCHAR", false),
        lockOwner("lock_owner", "lockOwner", "VARCHAR", false),
        lockState("lock_state", "lockState", "INTEGER", false),
        expireTime("expire_time", "expireTime", "TIMESTAMP", false),
        lockDesc("lock_desc", "lockDesc", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false);

        /**
         * t_locks
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_locks
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_locks
         */
        private final String column;

        /**
         * t_locks
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_locks
         */
        private final String javaProperty;

        /**
         * t_locks
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