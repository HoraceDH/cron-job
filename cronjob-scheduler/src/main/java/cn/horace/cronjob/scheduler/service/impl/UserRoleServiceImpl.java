package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.scheduler.entities.UserRoleEntity;
import cn.horace.cronjob.scheduler.entities.UserRoleEntityExample;
import cn.horace.cronjob.scheduler.mappers.UserRoleEntityMapper;
import cn.horace.cronjob.scheduler.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户-角色
 *
 * @author Horace
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
    @Resource
    private UserRoleEntityMapper mapper;
    @Resource
    private GuidGenerate guidGenerate;

    /**
     * 根据用户ID获取角色ID列表
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<Long> getRoleIds(long userId) {
        List<Long> roleIds = new ArrayList<>();
        UserRoleEntityExample example = new UserRoleEntityExample();
        example.or().andUserIdEqualTo(userId);
        List<UserRoleEntity> userRoleEntities = this.mapper.selectByExample(example);
        if (userRoleEntities != null) {
            for (UserRoleEntity entity : userRoleEntities) {
                roleIds.add(entity.getRoleId());
            }
        }
        return roleIds;
    }

    /**
     * 添加用户角色关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    @Override
    public void addUserRole(long userId, long roleId) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userId);
        userRoleEntity.setId(this.guidGenerate.genId());
        userRoleEntity.setRoleId(roleId);
        this.mapper.insertSelective(userRoleEntity);
    }

    /**
     * 根据用户ID删除用户与角色的关系
     *
     * @param userId 用户ID
     */
    @Override
    public void deleteUserRole(long userId) {
        UserRoleEntityExample example = new UserRoleEntityExample();
        example.or().andUserIdEqualTo(userId);
        this.mapper.deleteByExample(example);
    }
}