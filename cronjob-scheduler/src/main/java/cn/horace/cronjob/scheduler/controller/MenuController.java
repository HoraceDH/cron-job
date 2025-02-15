package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.scheduler.bean.params.GetMenuListParams;
import cn.horace.cronjob.scheduler.bean.params.GrantUserParams;
import cn.horace.cronjob.scheduler.bean.result.MenuItem;
import cn.horace.cronjob.scheduler.bean.result.MenuListResult;
import cn.horace.cronjob.scheduler.context.WebContext;
import cn.horace.cronjob.scheduler.service.MenuService;
import cn.horace.cronjob.scheduler.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单
 * <p>
 *
 * @author Horace
 */
@RestController
@RequestMapping("/manager-api/menu")
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Resource
    private MenuService menuService;
    @Resource
    private TokenService tokenService;

    /**
     * 获取用户菜单列表
     *
     * @return
     */
    @PostMapping(name = "获取用户菜单列表", value = "/getUserMenuList")
    public MsgObject getList() {
        String token = WebContext.getContext().getToken();
        boolean isValid = this.tokenService.isValid(this.tokenService.getToken(token));
        if (!isValid) {
            return MsgObject.msgCodes(MsgCodes.ERROR_USER_INVALID_TOKEN);
        }
        List<MenuItem> results = this.menuService.getMenuList(token, true, false);
        return MsgObject.success(results);
    }

    /**
     * 获取所有的菜单列表
     *
     * @param params 参数
     * @return
     */
    @PostMapping(name = "获取所有的菜单", value = "/getAllList")
    public MsgObject getAllList(GetMenuListParams params) {
        Result<MenuListResult> result = this.menuService.getAllMenuList(params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 获取用户的菜单ID列表
     *
     * @return
     */
    @PostMapping(name = "获取用户的菜单ID列表", value = "/getUserMenuIds")
    public MsgObject getMenuIds(String userId) {
        long uid = Long.parseLong(userId);
        Result<List<String>> result = this.menuService.getMyMenuIds(uid);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 授权菜单给用户
     *
     * @param params 参数
     * @return
     */
    @PostMapping(name = "授权菜单给用户", value = "/grant")
    public MsgObject grant(@RequestBody GrantUserParams params) {
        Result<String> result = this.menuService.grantUser(params);
        if (result.isSuccess()) {
            return MsgObject.success();
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }
}