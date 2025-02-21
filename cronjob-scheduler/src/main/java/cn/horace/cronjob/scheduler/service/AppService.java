package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.AppState;
import cn.horace.cronjob.scheduler.bean.params.GetAppListParams;
import cn.horace.cronjob.scheduler.bean.result.AppListResult;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.entities.AppEntity;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Horace
 */
public interface AppService {
    /**
     * 获取或者初始化应用实体
     *
     * @param tenantId 租户ID
     * @param appName  应用名称
     * @param appDesc  应用描述
     * @return
     */
    AppEntity getOrInitApp(long tenantId, String appName, @NotBlank String appDesc);

    /**
     * 获取应用数
     *
     * @param tenantId 租户ID
     * @return
     */
    int getAppCount(long tenantId);

    /**
     * 获取应用列表
     *
     * @param userId 当前用户ID
     * @param params 查询参数
     * @return
     */
    Result<AppListResult> getAppList(long userId, GetAppListParams params);

    /**
     * 获取搜索列表，提供给搜索框用
     *
     * @param userId   当前用户ID
     * @param tenantId 租户ID
     * @return
     */
    Result<List<SearchItem>> getSearchList(long userId, String tenantId);

    /**
     * 获取应用实体
     *
     * @param tenantId 租户ID
     * @param appName  应用名
     * @return
     */
    AppEntity getApp(long tenantId, String appName);

    /**
     * 获取应用
     *
     * @param id 应用ID
     * @return
     */
    AppEntity getApp(long id);

    /**
     * 更新应用状态
     *
     * @param id    应用ID
     * @param state 状态
     * @return true: 成功，false：失败
     */
    boolean updateState(long id, AppState state);

    /**
     * 更新应用状态
     *
     * @param userId 用户ID
     * @param id     应用ID
     * @param state  状态值
     * @return
     */
    Result<Void> updateState(long userId, long id, AppState state);

    /**
     * 根据状态获取App数量
     *
     * @param state 状态
     * @return
     */
    int getAppCount(AppState state);

    /**
     * 分页获取应用列表
     *
     * @param lastAppId 最后一次获取的应用ID
     * @param pageSize  一页的大小
     * @param state     状态
     * @return
     */
    List<AppEntity> getAppList(long lastAppId, int pageSize, AppState state);
}
