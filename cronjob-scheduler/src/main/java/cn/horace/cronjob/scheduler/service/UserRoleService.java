package cn.horace.cronjob.scheduler.service;

import java.util.List;

/**
 * 用户-角色
 *
 * @author Horace
 */
public interface UserRoleService {
    /**
     * 根据用户ID获取角色ID列表
     *
     * @param userId 用户ID
     * @return
     */
    List<Long> getRoleIds(long userId);

    /**
     * 添加用户角色关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void addUserRole(long userId, long roleId);

    /**
     * 根据用户ID删除用户与角色的关系
     *
     * @param userId 用户ID
     */
    void deleteUserRole(long userId);
}
