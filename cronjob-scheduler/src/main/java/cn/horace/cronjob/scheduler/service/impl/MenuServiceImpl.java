package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.constants.Constants;
import cn.horace.cronjob.scheduler.entities.MenuEntity;
import cn.horace.cronjob.scheduler.entities.MenuEntityExample;
import cn.horace.cronjob.scheduler.entities.TokenEntity;
import cn.horace.cronjob.scheduler.mappers.MenuEntityMapper;
import cn.horace.cronjob.scheduler.bean.params.GetMenuListParams;
import cn.horace.cronjob.scheduler.bean.params.GrantUserParams;
import cn.horace.cronjob.scheduler.bean.result.MenuItem;
import cn.horace.cronjob.scheduler.bean.result.MenuListResult;
import cn.horace.cronjob.scheduler.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单
 * <p>
 *
 * @author Horace
 */
@Service
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Resource
    private MenuEntityMapper mapper;
    @Resource
    private TokenService tokenService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private RoleService roleService;

    /**
     * 获取菜单列表
     *
     * @param token      用户Token
     * @param tree       是否返回树形结构
     * @param permission 是否包含权限
     * @return
     */
    @Override
    public List<MenuItem> getMenuList(String token, boolean tree, boolean permission) {
        TokenEntity tokenEntity = this.tokenService.getToken(token);
        List<Long> menuIds;
        // 如果是超级管理员，则获取所有菜单
        if (Objects.equals(tokenEntity.getUserId(), Constants.ADMIN_USER_ID)) {
            menuIds = this.getAllMenuIds();
        } else {
            // 获取角色ID列表
            List<Long> roleIds = this.userRoleService.getRoleIds(tokenEntity.getUserId());
            if (roleIds == null || roleIds.isEmpty()) {
                return new ArrayList<>();
            }
            // 获取菜单ID列表
            menuIds = this.roleMenuService.getMenuIds(roleIds);
        }
        TreeSet<Long> menuIdsSet = this.fillParent(new TreeSet<>(menuIds), menuIds);
        List<MenuEntity> menuEntities = this.getMenuList(new ArrayList<>(menuIdsSet), permission);
        return convertMenuItems(menuEntities, tree, false);
    }

    /**
     * 填充父菜单
     *
     * @param menuIds        菜单列表
     * @param queryParentIds 需要查询的父ID
     * @return
     */
    private TreeSet<Long> fillParent(TreeSet<Long> menuIds, List<Long> queryParentIds) {
        TreeSet<Long> parentIds = new TreeSet<>();
        MenuEntityExample example = new MenuEntityExample();
        example.or().andIdIn(queryParentIds);
        List<MenuEntity> entities = this.mapper.selectByExample(example);
        for (MenuEntity entity : entities) {
            if (entity.getParentId() != 0 && entity.getMenu()) {
                parentIds.add(entity.getParentId());
            }
        }
        if (parentIds.isEmpty()) {
            return menuIds;
        }
        menuIds.addAll(parentIds);
        return this.fillParent(menuIds, new ArrayList<>(parentIds));
    }

    /**
     * 对象转换
     *
     * @param menuEntities 菜单列表
     * @param tree         是否树形结构
     * @param children     是否选择children字段
     * @return
     */
    private List<MenuItem> convertMenuItems(List<MenuEntity> menuEntities, boolean tree, boolean children) {
        List<MenuItem> results = new ArrayList<>();
        for (MenuEntity menuEntity : menuEntities) {
            results.add(this.convert(menuEntity));
        }
        if (tree) {
            results = this.handlerMenuList(results, children);
        }
        return results;
    }

    /**
     * 处理菜单列表，形成树形结构
     *
     * @param menuItems 所有的菜单
     * @param children  是否选择children字段
     * @return
     */
    private List<MenuItem> handlerMenuList(List<MenuItem> menuItems, boolean children) {
        List<MenuItem> results = new ArrayList<>();
        // 创建一个映射表，以菜单的ID作为键，菜单对象作为值，以便更轻松地查找和组装菜单
        Map<String, MenuItem> menuEntityMap = new HashMap<>();
        for (MenuItem menu : menuItems) {
            menuEntityMap.put(menu.getKey(), menu);
        }
        // 遍历菜单列表，将子菜单添加到父菜单的subMenus属性中
        for (MenuItem menu : menuItems) {
            String parentId = menu.getParentId();
            if ("0".equals(parentId)) {
                // 如果父级菜单则直接添加到结果中
                results.add(menu);
            } else {
                MenuItem parentMenu = menuEntityMap.get(parentId);
                if (parentMenu != null) {
                    if (children) {
                        parentMenu.getChildren().add(menu);
                    } else {
                        parentMenu.getSubMenus().add(menu);
                    }
                }
            }
        }
        return results;
    }

    /**
     * 对象转换
     *
     * @param entity 实体
     * @return
     */
    private MenuItem convert(MenuEntity entity) {
        MenuItem itemResult = new MenuItem();
        itemResult.setKey(String.valueOf(entity.getId()));
        itemResult.setParentId(String.valueOf(entity.getParentId()));
        itemResult.setMenu(entity.getMenu());
        itemResult.setName(entity.getName());
        itemResult.setIcon(entity.getIcon());
        itemResult.setPath(entity.getPath());
        itemResult.setLocale(entity.getLocale());
        itemResult.setComponent(entity.getComponent());
        itemResult.setCreateTime(DateFormatUtils.format(entity.getCreateTime(), cn.horace.cronjob.commons.constants.Constants.DATE_FORMAT));
        itemResult.setSubMenus(new ArrayList<>());
        itemResult.setChildren(new ArrayList<>());
        return itemResult;
    }

    /**
     * 获取菜单列表
     *
     * @param menuIds     菜单ID列表
     * @param permissions 是否包含权限
     * @return
     */
    @Override
    public List<MenuEntity> getMenuList(List<Long> menuIds, boolean permissions) {
        MenuEntityExample example = new MenuEntityExample();
        if (!permissions) {
            example.or().andIdIn(menuIds).andMenuEqualTo(true);
        } else {
            example.or().andIdIn(menuIds);
        }
        return this.mapper.selectByExample(example);
    }

    /**
     * 获取菜单列表
     *
     * @param params 参数
     * @return
     */
    @Override
    public Result<MenuListResult> getAllMenuList(GetMenuListParams params) {
        Result<MenuListResult> result = Result.success();
        MenuListResult menuListResult = new MenuListResult();
        menuListResult.setCurrent(params.getCurrent());
        menuListResult.setPageSize(params.getPageSize());
        MenuEntityExample example = new MenuEntityExample();
        List<MenuEntity> menuEntities = this.mapper.selectByExample(example);
        long total = this.mapper.countByExample(example);
        menuListResult.setTotal(total);

        List<MenuItem> items = this.convertMenuItems(menuEntities, true, true);
        menuListResult.setData(items);
        result.setData(menuListResult);
        return result;
    }

    /**
     * 根据路径查询菜单实体
     *
     * @param path 路径
     * @return
     */
    @Override
    public MenuEntity getMenu(String path) {
        MenuEntityExample example = new MenuEntityExample();
        example.or().andPathEqualTo(path);
        List<MenuEntity> entities = this.mapper.selectByExample(example);
        if (entities != null && !entities.isEmpty()) {
            return entities.get(0);
        }
        return null;
    }

    /**
     * 添加菜单
     *
     * @param menuEntity 菜单实体
     */
    @Override
    public void addMenu(MenuEntity menuEntity) {
        this.mapper.insertSelective(menuEntity);
    }

    /**
     * 获取有权限的菜单ID
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public Result<List<String>> getMyMenuIds(long userId) {
        Result<List<String>> result = Result.success();
        List<Long> menuIds;
        if (userId == Constants.ADMIN_USER_ID) {
            menuIds = this.getAllMenuIds();
        } else {
            List<Long> roleIds = this.userRoleService.getRoleIds(userId);
            menuIds = this.roleMenuService.getMenuIds(roleIds);
        }
        List<String> stringIds = menuIds.stream().map(String::valueOf).collect(Collectors.toList());
        result.setData(stringIds);
        return result;
    }

    /**
     * 获取所有的菜单ID列表
     *
     * @return
     */
    @Override
    public List<Long> getAllMenuIds() {
        List<MenuEntity> menuEntities = this.mapper.selectByExample(new MenuEntityExample());
        return menuEntities.stream().map(MenuEntity::getId).collect(Collectors.toList());
    }

    /**
     * 给用户授予菜单权限
     *
     * @param params 参数
     * @return
     */
    @Override
    public Result<String> grantUser(GrantUserParams params) {
        long userId = Long.parseLong(params.getUserId());
        // 删除权限
        if (StringUtils.isBlank(params.getMenuIds())) {
            this.userRoleService.deleteUserRole(userId);
        } else {
            // 新建角色
            String roleName = "角色-" + params.getUserId();
            long roleId = this.roleService.addRole(roleName);
            // 新建角色与菜单关系
            this.roleMenuService.addRoleMenu(roleId, params.getMenuIds());
            // 删除原来的角色
            this.roleService.deleteRole(roleName);
            this.userRoleService.deleteUserRole(userId);
            // 新建用户与角色关系
            this.userRoleService.addUserRole(userId, roleId);
        }
        return Result.success();
    }
}