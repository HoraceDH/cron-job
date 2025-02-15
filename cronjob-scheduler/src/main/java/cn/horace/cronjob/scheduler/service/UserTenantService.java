package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.scheduler.entities.UserTenantEntity;

import java.util.List;

/**
 *
 * @author Horace
 */
public interface UserTenantService {
    /**
     * 根据用户ID获取租户ID列表
     *
     * @param userId 用户ID
     * @return
     */
    public List<Long> getTenantIds(long userId);

    /**
     * 删除用户与租户的关系
     *
     * @param userId 用户ID
     */
    void deleteUserTenant(long userId);

    /**
     * 添加用户与租户的关联关系
     *
     * @param userId    用户ID
     * @param tenantIds 租户ID
     */
    void addUserTenant(long userId, List<Long> tenantIds);

    /**
     * 获取用户与租户的关系
     *
     * @param userId   用户ID
     * @param tenantId 租户ID
     * @return
     */
    UserTenantEntity getTenant(long userId, long tenantId);
}