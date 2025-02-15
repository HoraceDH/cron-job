package cn.horace.cronjob.scheduler.service;

/**
 * 角色
 * <p>
 *
 * @author Horace
 */
public interface RoleService {
    /**
     * 添加角色
     *
     * @param name 角色名
     * @return
     */
    long addRole(String name);

    /**
     * 删除角色
     *
     * @param roleName 角色名
     */
    void deleteRole(String roleName);
}
