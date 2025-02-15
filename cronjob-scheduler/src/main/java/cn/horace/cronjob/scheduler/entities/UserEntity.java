package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2024-07-27 12:24:48.
 * <p>
 * 对应数据库表：t_user
 * 
 * @author Horace 
 */
public class UserEntity {
    /**
     * 用户ID id
     */
    private Long id;

    /**
     * 用户名 username
     */
    private String username;

    /**
     * 密码 password
     */
    private String password;

    /**
     * 昵称 nickname
     */
    private String nickname;

    /**
     * 邮箱 email
     */
    private String email;

    /**
     * 0：正常，1：禁用 state
     */
    private Integer state;

    /**
     * 手机号 phone
     */
    private String phone;

    /**
     * 头像地址 avatar
     */
    private String avatar;

    /**
     * 地址 address
     */
    private String address;

    /**
     * 签名 signature
     */
    private String signature;

    /**
     * 创建时间 create_time
     */
    private Date createTime;

    /**
     * 修改时间 modify_time
     */
    private Date modifyTime;

    /**
     * 用户ID
     * 
     * @return 用户ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 用户ID
     * 
     * @param id 用户ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户名
     * 
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     * 
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * 
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 昵称
     * 
     * @return 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 昵称
     * 
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 邮箱
     * 
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     * 
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 0：正常，1：禁用
     * 
     * @return 0：正常，1：禁用
     */
    public Integer getState() {
        return state;
    }

    /**
     * 0：正常，1：禁用
     * 
     * @param state 0：正常，1：禁用
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 手机号
     * 
     * @return 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 手机号
     * 
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 头像地址
     * 
     * @return 头像地址
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 头像地址
     * 
     * @param avatar 头像地址
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 地址
     * 
     * @return 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地址
     * 
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 签名
     * 
     * @return 签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 签名
     * 
     * @param signature 签名
     */
    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
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
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", nickname=").append(nickname);
        sb.append(", email=").append(email);
        sb.append(", state=").append(state);
        sb.append(", phone=").append(phone);
        sb.append(", avatar=").append(avatar);
        sb.append(", address=").append(address);
        sb.append(", signature=").append(signature);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_user
     */
    public static enum Column {
        id("id", "id", "BIGINT", false),
        username("username", "username", "VARCHAR", false),
        password("password", "password", "VARCHAR", false),
        nickname("nickname", "nickname", "VARCHAR", false),
        email("email", "email", "VARCHAR", false),
        state("state", "state", "INTEGER", false),
        phone("phone", "phone", "VARCHAR", false),
        avatar("avatar", "avatar", "VARCHAR", false),
        address("address", "address", "VARCHAR", false),
        signature("signature", "signature", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false);

        /**
         * t_user
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_user
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_user
         */
        private final String column;

        /**
         * t_user
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_user
         */
        private final String javaProperty;

        /**
         * t_user
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