package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.AppState;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.scheduler.adapter.AppAdapter;
import cn.horace.cronjob.scheduler.bean.params.GetAppListParams;
import cn.horace.cronjob.scheduler.bean.result.AppListResult;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.entities.AppEntity;
import cn.horace.cronjob.scheduler.entities.AppEntityExample;
import cn.horace.cronjob.scheduler.mappers.AppEntityMapper;
import cn.horace.cronjob.scheduler.service.AppService;
import cn.horace.cronjob.scheduler.service.TaskService;
import cn.horace.cronjob.scheduler.service.UserTenantService;
import cn.horace.cronjob.scheduler.utils.LikeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Horace
 */
@Service
public class AppServiceImpl implements AppService {
    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);
    @Resource
    private AppEntityMapper mapper;
    @Resource
    private GuidGenerate guidGenerate;
    @Resource
    private AppAdapter appAdapter;
    @Resource
    private UserTenantService userTenantService;
    @Lazy
    @Resource
    private TaskService taskService;

    /**
     * 获取或者初始化应用实体
     *
     * @param tenantId 租户ID
     * @param appName  应用名称
     * @param appDesc  应用描述
     * @return
     */
    @Override
    public AppEntity getOrInitApp(long tenantId, String appName, String appDesc) {
        AppEntityExample example = new AppEntityExample();
        example.or().andTenantIdEqualTo(tenantId).andAppNameEqualTo(appName);
        List<AppEntity> entities = this.mapper.selectByExample(example);
        if (entities != null && !entities.isEmpty()) {
            return entities.get(0);
        }

        // 新增
        AppEntity entity = new AppEntity();
        entity.setId(this.guidGenerate.genId());
        entity.setTenantId(tenantId);
        entity.setAppName(appName);
        entity.setAppDesc(appDesc);
        entity.setState(AppState.RUN.getValue());
        this.mapper.insertSelective(entity);
        return entity;
    }

    /**
     * 获取应用数
     *
     * @param tenantId 租户ID
     * @return
     */
    @Override
    public int getAppCount(long tenantId) {
        AppEntityExample example = new AppEntityExample();
        example.or().andTenantIdEqualTo(tenantId);
        return (int) this.mapper.countByExample(example);
    }

    /**
     * 获取应用列表
     *
     * @param userId 当前用户ID
     * @param params 查询参数
     * @return
     */
    @Override
    public Result<AppListResult> getAppList(long userId, GetAppListParams params) {
        long id = 0;
        if (StringUtils.isNotBlank(params.getId())) {
            id = Long.parseLong(params.getId());
        }
        Result<AppListResult> result = Result.success();
        AppEntityExample example = new AppEntityExample();

        // 条件设置
        AppEntityExample.Criteria criteria = example.or();
        if (id > 0) {
            criteria.andIdEqualTo(id);
        }

        // 处理用户的租户权限
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (tenantIds == null || tenantIds.isEmpty()) {
            return Result.success();
        }
        if (StringUtils.isBlank(params.getTenantId()) || Long.parseLong(params.getTenantId()) <= 0) {
            criteria.andTenantIdIn(tenantIds);
        } else {
            // 如果指定了租户ID，那么需要判断该租户ID是否属于当前用户
            long tenantId = Long.parseLong(params.getTenantId());
            if (tenantIds.contains(tenantId)) {
                criteria.andTenantIdEqualTo(tenantId);
            } else {
                // 租户参数不属于当前用户
                return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
            }
        }

        if (params.getState() != -1) {
            criteria.andStateEqualTo(params.getState());
        }

        if (StringUtils.isNotBlank(params.getAppName())) {
            criteria.andAppNameLike(LikeUtils.toLikeString(params.getAppName()));
        }

        AppListResult userListResult = new AppListResult();
        userListResult.setTotal(this.mapper.countByExample(example));
        int offset = (params.getCurrent() - 1) * params.getPageSize();
        int limit = params.getPageSize();
        example.setOrderByClause("`create_time` desc limit " + offset + ", " + limit);
        List<AppEntity> entityList = this.mapper.selectByExample(example);
        userListResult.setCurrent(params.getCurrent());
        userListResult.setPageSize(params.getPageSize());
        userListResult.setData(this.appAdapter.convert(entityList));
        result.setData(userListResult);
        return result;
    }

    /**
     * 获取搜索列表，提供给搜索框用
     *
     * @param userId   当前用户ID
     * @param tenantId 租户ID
     * @return
     */
    @Override
    public Result<List<SearchItem>> getSearchList(long userId, String tenantId) {
        List<SearchItem> items = new ArrayList<>();
        AppEntityExample example = new AppEntityExample();
        AppEntityExample.Criteria criteria = example.or();
        items.add(new SearchItem("全部", ""));
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        long tenantIdLong = 0;
        if (StringUtils.isNotBlank(tenantId)) {
            tenantIdLong = Long.parseLong(tenantId);
        }
        if (tenantIds == null || tenantIds.isEmpty()) {
            return Result.success(items);
        }
        if (tenantIdLong > 0 && !tenantIds.contains(tenantIdLong)) {
            return Result.success(items);
        }
        if (tenantIdLong > 0) {
            criteria.andTenantIdEqualTo(tenantIdLong);
        } else {
            criteria.andTenantIdIn(tenantIds);
        }
        List<AppEntity> tenants = this.mapper.selectByExample(example);
        for (AppEntity app : tenants) {
            String label = app.getAppName();
            items.add(new SearchItem(label, label));
        }
        return Result.success(items);
    }

    @Override
    public AppEntity getApp(long tenantId, String appName) {
        return this.getOrInitApp(tenantId, appName, "");
    }

    /**
     * 获取应用
     *
     * @param id 应用ID
     * @return
     */
    @Override
    public AppEntity getApp(long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 更新应用状态
     *
     * @param id    应用ID
     * @param state 状态
     */
    @Override
    public boolean updateState(long id, AppState state) {
        AppEntity entity = new AppEntity();
        entity.setId(id);
        entity.setState(state.getValue());
        entity.setModifyTime(new Date());
        int count = this.mapper.updateByPrimaryKeySelective(entity);
        if (count <= 0) {
            logger.error("update app state error, id:{}, state:{}, count:{}", id, state, count);
        }
        return count > 0;
    }

    /**
     * 更新应用状态
     *
     * @param userId 用户ID
     * @param id     应用ID
     * @param state  状态值
     * @return
     */
    @Override
    public Result<Void> updateState(long userId, long id, AppState state) {
        AppEntity entity = this.getApp(id);
        if (entity == null) {
            logger.warn("update app state failed, params error, userId:{}, appId:{}", userId, id);
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        boolean contains = tenantIds.contains(entity.getTenantId());
        if (!contains) {
            return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }
        boolean success = this.updateState(id, state);
        if (success && state == AppState.STOP) {
            this.taskService.stopAllTask(id);
        }
        return Result.success();
    }

    @Override
    public int getAppCount(AppState state) {
        AppEntityExample example = new AppEntityExample();
        example.or().andStateEqualTo(state.getValue());
        return (int) this.mapper.countByExample(example);
    }

    /**
     * 分页获取应用列表
     *
     * @param lastAppId 最后一次获取的应用ID
     * @param pageSize  一页的大小
     * @param state     状态
     * @return
     */
    @Override
    public List<AppEntity> getAppList(long lastAppId, int pageSize, AppState state) {
        AppEntityExample example = new AppEntityExample();
        example.or().andIdGreaterThan(lastAppId).andStateEqualTo(state.getValue());
        example.setOrderByClause("`id` asc limit " + pageSize);
        return this.mapper.selectByExample(example);
    }
}