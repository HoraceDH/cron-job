package cn.horace.cronjob.scheduler.service;

import java.util.List;

/**
 *
 * @author Horace
 */
public interface RoleMenuService {
    /**
     * 根据角色ID列表获取菜单ID列表
     *
     * @param roleIds 角色ID列表
     * @return
     */
    List<Long> getMenuIds(List<Long> roleIds);

    /**
     * 添加角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     */
    void addRoleMenu(long roleId, String menuIds);
}
