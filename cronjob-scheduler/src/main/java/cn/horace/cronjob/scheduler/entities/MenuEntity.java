package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Create in 2024-07-27 12:24:48.
 * <p>
 * 对应数据库表：t_menu
 * 
 * @author Horace 
 */
public class MenuEntity {
    /**
     * 菜单ID id
     */
    private Long id;

    /**
     * 父级菜单，0：表示顶级 parent_id
     */
    private Long parentId;

    /**
     * 是否是菜单 menu
     */
    private Boolean menu;

    /**
     * 菜单名称 name
     */
    private String name;

    /**
     * 菜单ICON，例如 DashboardFilled icon
     */
    private String icon;

    /**
     * 菜单路径 path
     */
    private String path;

    /**
     * 是否国际化，默认false locale
     */
    private Boolean locale;

    /**
     * 对应视图组件 component
     */
    private String component;

    /**
     * 创建时间 create_time
     */
    private Date createTime;

    /**
     * 修改时间 modify_time
     */
    private Date modifyTime;

    /**
     * 菜单ID
     * 
     * @return 菜单ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 菜单ID
     * 
     * @param id 菜单ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 父级菜单，0：表示顶级
     * 
     * @return 父级菜单，0：表示顶级
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 父级菜单，0：表示顶级
     * 
     * @param parentId 父级菜单，0：表示顶级
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 是否是菜单
     * 
     * @return 是否是菜单
     */
    public Boolean getMenu() {
        return menu;
    }

    /**
     * 是否是菜单
     * 
     * @param menu 是否是菜单
     */
    public void setMenu(Boolean menu) {
        this.menu = menu;
    }

    /**
     * 菜单名称
     * 
     * @return 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 菜单名称
     * 
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 菜单ICON，例如 DashboardFilled
     * 
     * @return 菜单ICON，例如 DashboardFilled
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 菜单ICON，例如 DashboardFilled
     * 
     * @param icon 菜单ICON，例如 DashboardFilled
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 菜单路径
     * 
     * @return 菜单路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 菜单路径
     * 
     * @param path 菜单路径
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * 是否国际化，默认false
     * 
     * @return 是否国际化，默认false
     */
    public Boolean getLocale() {
        return locale;
    }

    /**
     * 是否国际化，默认false
     * 
     * @param locale 是否国际化，默认false
     */
    public void setLocale(Boolean locale) {
        this.locale = locale;
    }

    /**
     * 对应视图组件
     * 
     * @return 对应视图组件
     */
    public String getComponent() {
        return component;
    }

    /**
     * 对应视图组件
     * 
     * @param component 对应视图组件
     */
    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
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
        sb.append(", parentId=").append(parentId);
        sb.append(", menu=").append(menu);
        sb.append(", name=").append(name);
        sb.append(", icon=").append(icon);
        sb.append(", path=").append(path);
        sb.append(", locale=").append(locale);
        sb.append(", component=").append(component);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * t_menu
     */
    public static enum Column {
        id("id", "id", "BIGINT", false),
        parentId("parent_id", "parentId", "BIGINT", false),
        menu("menu", "menu", "BIT", false),
        name("name", "name", "VARCHAR", false),
        icon("icon", "icon", "VARCHAR", false),
        path("path", "path", "VARCHAR", false),
        locale("locale", "locale", "BIT", false),
        component("component", "component", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        modifyTime("modify_time", "modifyTime", "TIMESTAMP", false);

        /**
         * t_menu
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * t_menu
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * t_menu
         */
        private final String column;

        /**
         * t_menu
         */
        private final boolean isColumnNameDelimited;

        /**
         * t_menu
         */
        private final String javaProperty;

        /**
         * t_menu
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