package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.scheduler.entities.RoleMenuEntity;
import cn.horace.cronjob.scheduler.entities.RoleMenuEntityExample;
import cn.horace.cronjob.scheduler.mappers.RoleMenuEntityMapper;
import cn.horace.cronjob.scheduler.service.RoleMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Horace
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    private static final Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);
    @Resource
    private RoleMenuEntityMapper mapper;
    @Resource
    private GuidGenerate guidGenerate;

    /**
     * 根据角色ID列表获取菜单ID列表
     *
     * @param roleIds 角色ID列表
     * @return
     */
    @Override
    public List<Long> getMenuIds(List<Long> roleIds) {
        List<Long> menuIds = new ArrayList<>();
        if (roleIds == null || roleIds.isEmpty()) {
            return menuIds;
        }
        RoleMenuEntityExample example = new RoleMenuEntityExample();
        example.or().andRoleIdIn(roleIds);
        List<RoleMenuEntity> entities = this.mapper.selectByExample(example);
        if (entities != null) {
            for (RoleMenuEntity entity : entities) {
                menuIds.add(entity.getMenuId());
            }
        }
        return menuIds;
    }

    /**
     * 添加角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     */
    @Override
    public void addRoleMenu(long roleId, String menuIds) {
        String[] split = menuIds.split(",");
        for (String menuId : split) {
            RoleMenuEntity entity = new RoleMenuEntity();
            entity.setId(this.guidGenerate.genId());
            entity.setRoleId(roleId);
            entity.setMenuId(Long.valueOf(menuId));
            this.mapper.insertSelective(entity);
        }
    }
}