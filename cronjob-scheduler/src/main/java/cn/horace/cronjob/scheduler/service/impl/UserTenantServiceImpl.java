package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.scheduler.constants.Constants;
import cn.horace.cronjob.scheduler.entities.UserTenantEntity;
import cn.horace.cronjob.scheduler.entities.UserTenantEntityExample;
import cn.horace.cronjob.scheduler.mappers.UserTenantEntityMapper;
import cn.horace.cronjob.scheduler.service.TenantService;
import cn.horace.cronjob.scheduler.service.UserTenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Horace
 */
@Service
public class UserTenantServiceImpl implements UserTenantService {
    private static final Logger logger = LoggerFactory.getLogger(UserTenantServiceImpl.class);
    @Resource
    private UserTenantEntityMapper mapper;
    @Resource
    private GuidGenerate guidGenerate;
    @Lazy
    @Resource
    private TenantService tenantService;

    /**
     * 根据用户ID获取租户ID列表
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<Long> getTenantIds(long userId) {
        UserTenantEntityExample example = new UserTenantEntityExample();
        // 如果是管理员账户，查询所有的租户
        if (userId == Constants.ADMIN_USER_ID) {
            return this.tenantService.getAllTenantIds();
        }
        example.or().andUserIdEqualTo(userId);
        List<UserTenantEntity> entities = this.mapper.selectByExample(example);
        return entities.stream().map(UserTenantEntity::getTenantId).collect(Collectors.toList());
    }

    /**
     * 删除用户与租户的关系
     *
     * @param userId 用户ID
     */
    @Override
    public void deleteUserTenant(long userId) {
        UserTenantEntityExample example = new UserTenantEntityExample();
        example.or().andUserIdEqualTo(userId);
        this.mapper.deleteByExample(example);
    }

    /**
     * 添加用户与租户的关联关系
     *
     * @param userId    用户ID
     * @param tenantIds 租户ID
     */
    @Override
    public void addUserTenant(long userId, List<Long> tenantIds) {
        for (Long tenantId : tenantIds) {
            UserTenantEntity temp = this.getTenant(userId, tenantId);
            if (temp == null) {
                UserTenantEntity entity = new UserTenantEntity();
                entity.setId(this.guidGenerate.genId());
                entity.setUserId(userId);
                entity.setTenantId(tenantId);
                this.mapper.insertSelective(entity);
            }
        }
    }

    /**
     * 获取用户与租户的关系
     *
     * @param userId   用户ID
     * @param tenantId 租户ID
     * @return
     */
    @Override
    public UserTenantEntity getTenant(long userId, long tenantId) {
        UserTenantEntityExample example = new UserTenantEntityExample();
        example.or().andUserIdEqualTo(userId).andTenantIdEqualTo(tenantId);
        List<UserTenantEntity> list = this.mapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}