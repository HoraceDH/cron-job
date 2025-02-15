package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.scheduler.entities.RoleEntity;
import cn.horace.cronjob.scheduler.entities.RoleEntityExample;
import cn.horace.cronjob.scheduler.mappers.RoleEntityMapper;
import cn.horace.cronjob.scheduler.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色
 *
 * @author Horace
 */
@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Resource
    private RoleEntityMapper mapper;
    @Resource
    private GuidGenerate guidGenerate;

    /**
     * 添加角色
     *
     * @param name 角色名
     * @return
     */
    @Override
    public long addRole(String name) {
        RoleEntity roleEntity = new RoleEntity();
        long roleId = this.guidGenerate.genId();
        roleEntity.setId(roleId);
        roleEntity.setName(name);
        int count = this.mapper.insertSelective(roleEntity);
        if (count <= 0) {
            logger.error("add role error, roleId:{}, name:{}", roleId, name);
        }
        return roleId;
    }

    /**
     * 删除角色
     *
     * @param roleName 角色名
     */
    @Override
    public void deleteRole(String roleName) {
        RoleEntityExample example = new RoleEntityExample();
        example.or().andNameEqualTo(roleName);
        this.mapper.deleteByExample(example);
    }
}