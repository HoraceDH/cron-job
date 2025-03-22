package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.params.GetTenantListParams;
import cn.horace.cronjob.scheduler.bean.params.GetTenantParams;
import cn.horace.cronjob.scheduler.bean.params.GrantTenantParams;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.bean.result.TenantItem;
import cn.horace.cronjob.scheduler.bean.result.TenantListResult;
import cn.horace.cronjob.scheduler.context.WebContext;
import cn.horace.cronjob.scheduler.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 租户
 * <p>
 *
 * @author Horace
 */
@RestController
@RequestMapping("/manager-api/tenant")
public class TenantController {
    private static final Logger logger = LoggerFactory.getLogger(TenantController.class);
    @Resource
    private TenantService tenantService;

    /**
     * 获取用户的租户ID列表
     *
     * @return
     */
    @PostMapping(name = "获取用户的租户ID列表", value = "/getTenantIds")
    public MsgObject getTenantIds(String userId) {
        long uid = Long.parseLong(userId);
        Result<List<String>> result = this.tenantService.getMyTenantIds(uid);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 获取所有的租户
     *
     * @param params 参数
     * @return
     */
    @PostMapping(name = "获取所有的租户", value = "/getAllList")
    public MsgObject getAllList(@RequestBody GetTenantListParams params) {
        Result<TenantListResult> result = this.tenantService.getAllTenant(params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 授权租户给用户
     *
     * @param params 参数
     * @return
     */
    @PostMapping(name = "授权租户给用户", value = "/grant")
    public MsgObject grantUser(@RequestBody GrantTenantParams params) {
        Result<Void> result = this.tenantService.grantTenant(params);
        if (result.isSuccess()) {
            return MsgObject.success();
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }

    /**
     * 获取租户列表，提供给搜索框用
     *
     * @return
     */
    @PostMapping(name = "获取租户列表，提供给搜索框用", value = "/getSearchList")
    public MsgObject getSearchList() {
        long userId = WebContext.getContext().getUserId();
        Result<List<SearchItem>> result = this.tenantService.getSearchList(userId);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }

    /**
     * 获取租户信息
     *
     * @return
     */
    @PostMapping(name = "获取租户信息", value = "/getTenantDetail")
    public MsgObject getTenant(@RequestBody GetTenantParams params) {
        long userId = WebContext.getContext().getUserId();
        Result<TenantItem> result = this.tenantService.getTenantDetail(userId, params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }

    /**
     * 更新租户信息
     *
     * @return
     */
    @PostMapping(name = "更新租户信息", value = "/updateById")
    public MsgObject updateById(@RequestBody TenantItem params) {
        long userId = WebContext.getContext().getUserId();
        Result<Void> result = this.tenantService.updateTenant(userId, params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }
}