package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.entities.MenuEntity;
import cn.horace.cronjob.scheduler.bean.params.GetMenuListParams;
import cn.horace.cronjob.scheduler.bean.params.GrantUserParams;
import cn.horace.cronjob.scheduler.bean.result.MenuItem;
import cn.horace.cronjob.scheduler.bean.result.MenuListResult;

import java.util.List;

/**
 * 菜单
 * <p>
 *
 * @author Horace
 */
public interface MenuService {
    /**
     * 获取菜单列表
     *
     * @param token      用户Token
     * @param tree       是否返回树形结构
     * @param permission 是否包含权限
     * @return
     */
    List<MenuItem> getMenuList(String token, boolean tree, boolean permission);

    /**
     * 获取菜单列表
     *
     * @param menuIds     菜单ID列表
     * @param permissions 是否包含权限
     * @return
     */
    List<MenuEntity> getMenuList(List<Long> menuIds, boolean permissions);

    /**
     * 获取菜单列表
     *
     * @param params 参数
     * @return
     */
    Result<MenuListResult> getAllMenuList(GetMenuListParams params);

    /**
     * 根据路径查询菜单实体
     *
     * @param path 路径
     * @return
     */
    MenuEntity getMenu(String path);

    /**
     * 添加菜单
     *
     * @param menuEntity 菜单实体
     */
    void addMenu(MenuEntity menuEntity);

    /**
     * 获取有权限的菜单ID
     *
     * @param userId 用户ID
     * @return
     */
    Result<List<String>> getMyMenuIds(long userId);

    /**
     * 获取所有的菜单ID列表
     *
     * @return
     */
    List<Long> getAllMenuIds();

    /**
     * 给用户授予菜单权限
     *
     * @param params 参数
     * @return
     */
    Result<String> grantUser(GrantUserParams params);
}
