package cn.horace.cronjob.scheduler.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单项
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class MenuItemResult {
    private String key;
    private String parentId;
    private boolean isMenu;
    private String name;
    private String icon;
    private String path;
    private boolean locale;
    private String component;
    private List<MenuItemResult> subMenus = new ArrayList<>();
}