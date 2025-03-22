package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.scheduler.adapter.TenantAdapter;
import cn.horace.cronjob.scheduler.bean.params.GetTenantListParams;
import cn.horace.cronjob.scheduler.bean.params.GetTenantParams;
import cn.horace.cronjob.scheduler.bean.params.GrantTenantParams;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.bean.result.TenantItem;
import cn.horace.cronjob.scheduler.bean.result.TenantListResult;
import cn.horace.cronjob.scheduler.constants.Constants;
import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.entities.TenantEntityExample;
import cn.horace.cronjob.scheduler.mappers.TenantEntityMapper;
import cn.horace.cronjob.scheduler.service.TenantService;
import cn.horace.cronjob.scheduler.service.UserTenantService;
import cn.horace.cronjob.scheduler.utils.LikeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户服务类
 * <p>
 *
 * @author Horace
 */
@Service
public class TenantServiceImpl implements TenantService {
    private static final Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);
    @Resource
    private GuidGenerate guidGenerate;
    @Resource
    private TenantEntityMapper mapper;
    @Resource
    private UserTenantService userTenantService;
    @Resource
    private TenantAdapter tenantAdapter;

    /**
     * 获取租户
     *
     * @param tenant 租户编码
     * @return
     */
    @Override
    public TenantEntity getOrInitTenant(String tenant) {
        TenantEntityExample example = new TenantEntityExample();
        example.or().andTenantEqualTo(tenant);
        List<TenantEntity> entities = this.mapper.selectByExample(example);
        TenantEntity tenantEntity;
        if (entities == null || entities.isEmpty()) {
            tenantEntity = new TenantEntity();
            tenantEntity.setId(this.guidGenerate.genId());
            tenantEntity.setTenant(tenant);
            tenantEntity.setName("租户" + tenant);
            tenantEntity.setRemark(tenantEntity.getName());
            this.mapper.insertSelective(tenantEntity);
        } else {
            tenantEntity = entities.get(0);
        }
        return tenantEntity;
    }


    /**
     * 获取当前用户所属的租户ID列表
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public Result<List<String>> getMyTenantIds(long userId) {
        Result<List<String>> result = Result.success();
        List<Long> tenantIds;
        if (userId == Constants.ADMIN_USER_ID) {
            tenantIds = this.getAllTenantIds();
        } else {
            tenantIds = this.userTenantService.getTenantIds(userId);
        }
        List<String> stringIds = tenantIds.stream().map(String::valueOf).collect(Collectors.toList());
        result.setData(stringIds);
        return result;
    }

    /**
     * 获取所有的租户ID
     *
     * @return
     */
    @Override
    public List<Long> getAllTenantIds() {
        List<TenantEntity> menuEntities = this.mapper.selectByExample(new TenantEntityExample());
        return menuEntities.stream().map(TenantEntity::getId).collect(Collectors.toList());
    }

    /**
     * 获取租户列表
     *
     * @param params 参数
     * @return
     */
    @Override
    public Result<TenantListResult> getAllTenant(GetTenantListParams params) {
        Result<TenantListResult> result = Result.success();
        TenantListResult tenantListResult = new TenantListResult();
        tenantListResult.setCurrent(params.getCurrent());
        tenantListResult.setPageSize(params.getPageSize());
        TenantEntityExample example = new TenantEntityExample();
        TenantEntityExample.Criteria criteria = example.or();
        if (StringUtils.isNotBlank(params.getKey())) {
            criteria.andIdEqualTo(Long.parseLong(params.getKey()));
        }
        if (StringUtils.isNotBlank(params.getName())) {
            criteria.andNameLike(LikeUtils.toLikeString(params.getName()));
        }
        if (StringUtils.isNotBlank(params.getTenant())) {
            criteria.andTenantLike(LikeUtils.toLikeString(params.getTenant()));
        }
        if (StringUtils.isNotBlank(params.getRemark())) {
            criteria.andRemarkLike(LikeUtils.toLikeString(params.getRemark()));
        }
        List<TenantEntity> tenantEntities = this.mapper.selectByExample(example);
        long total = this.mapper.countByExample(example);
        tenantListResult.setTotal(total);
        List<TenantItem> items = this.tenantAdapter.convertTenantItems(tenantEntities);
        tenantListResult.setData(items);
        result.setData(tenantListResult);
        return result;
    }

    /**
     * 授权租户给用户
     *
     * @param params 参数
     * @return
     */
    @Override
    public Result<Void> grantTenant(GrantTenantParams params) {
        long userId = Long.parseLong(params.getUserId());
        // 删除原来的租户关系
        this.userTenantService.deleteUserTenant(userId);
        // 建立新的租户关系
        if (StringUtils.isNotBlank(params.getTenantIds())) {
            String[] split = params.getTenantIds().split(",");
            ArrayList<Long> tenantIds = new ArrayList<>();
            for (String id : split) {
                tenantIds.add(Long.valueOf(id));
            }
            this.userTenantService.addUserTenant(userId, tenantIds);
        }
        return Result.success();
    }

    /**
     * 获取租户
     *
     * @param tenantId
     * @return
     */
    @Override
    public TenantEntity getTenant(long tenantId) {
        return this.mapper.selectByPrimaryKey(tenantId);
    }

    /**
     * 获取租户列表，提供给搜索框用，只能查询到自己有权限的租户
     *
     * @param userId 当前用户ID
     * @return
     */
    @Override
    public Result<List<SearchItem>> getSearchList(long userId) {
        List<SearchItem> items = new ArrayList<>();
        items.add(new SearchItem("全部", "-1"));
        TenantEntityExample example = new TenantEntityExample();
        if (userId != Constants.ADMIN_USER_ID) {
            List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
            if (tenantIds == null || tenantIds.isEmpty()) {
                return Result.success(items);
            }
            example.or().andIdIn(tenantIds);
        }
        List<TenantEntity> tenants = this.mapper.selectByExample(example);
        for (TenantEntity tenant : tenants) {
            String label = tenant.getTenant();
            items.add(new SearchItem(label, String.valueOf(tenant.getId())));
        }
        Collections.sort(items, (o1, o2) -> (int) (Long.parseLong(o1.getValue()) - Long.parseLong(o2.getValue())));
        return Result.success(items);
    }

    /**
     * 更新租户信息
     *
     * @param tenant 租户信息
     * @return
     */
    @Override
    public boolean updateTenant(TenantEntity tenant) {
        return this.mapper.updateByPrimaryKeySelective(tenant) > 0;
    }

    /**
     * 获取租户信息
     *
     * @param userId 用户ID
     * @param params 参数
     * @return
     */
    @Override
    public Result<TenantItem> getTenantDetail(long userId, GetTenantParams params) {
        String id = params.getId();
        long tenantId = Long.parseLong(id);
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (!tenantIds.contains(tenantId)) {
            return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }
        TenantEntity entity = this.getTenant(tenantId);
        return Result.success(this.tenantAdapter.convertItem(entity));
    }

    /**
     * 更新租户信息
     *
     * @param userId 用户ID
     * @param params 参数
     * @return
     */
    @Override
    public Result<Void> updateTenant(long userId, TenantItem params) {
        String id = params.getKey();
        long tenantId = Long.parseLong(id);
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (!tenantIds.contains(tenantId)) {
            return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
        }
        TenantEntity entity = this.tenantAdapter.convertEntity(params);
        boolean success = this.updateTenant(entity);
        if (success) {
            return Result.success();
        }
        return Result.msgCodes(MsgCodes.ERROR_SYSTEM);
    }
}