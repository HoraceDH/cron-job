package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.params.GetTenantListParams;
import cn.horace.cronjob.scheduler.bean.params.GrantTenantParams;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.bean.result.TenantListResult;
import cn.horace.cronjob.scheduler.entities.TenantEntity;

import java.util.List;

/**
 * 租户服务类
 * <p>
 *
 * @author Horace
 */
public interface TenantService {

    /**
     * 获取租户
     *
     * @param tenant 租户编码
     * @return
     */
    TenantEntity getOrInitTenant(String tenant);

    /**
     * 获取当前用户所属的租户ID列表
     *
     * @param userId 用户ID
     * @return
     */
    Result<List<String>> getMyTenantIds(long userId);

    /**
     * 获取所有的租户ID
     *
     * @return
     */
    public List<Long> getAllTenantIds();

    /**
     * 获取租户列表
     *
     * @param params 参数
     * @return
     */
    Result<TenantListResult> getAllTenant(GetTenantListParams params);

    /**
     * 授权租户给用户
     *
     * @param params 参数
     * @return
     */
    Result<Void> grantTenant(GrantTenantParams params);

    /**
     * 获取租户
     *
     * @param tenantId
     * @return
     */
    TenantEntity getTenant(long tenantId);

    /**
     * 获取租户列表，提供给搜索框用，只能查询到自己有权限的租户
     *
     * @param userId 当前用户ID
     * @return
     */
    Result<List<SearchItem>> getSearchList(long userId);

    /**
     * 更新租户信息
     *
     * @param tenant 租户信息
     * @return
     */
    boolean updateTenant(TenantEntity tenant);
}