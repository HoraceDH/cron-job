package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.AppState;
import cn.horace.cronjob.scheduler.bean.params.GetAppListParams;
import cn.horace.cronjob.scheduler.bean.params.StartAppParams;
import cn.horace.cronjob.scheduler.bean.params.StopAppParams;
import cn.horace.cronjob.scheduler.bean.result.AppListResult;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.context.WebContext;
import cn.horace.cronjob.scheduler.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 应用控制器
 * <p>
 *
 * @author Horace
 */
@RestController
@RequestMapping("/manager-api/app")
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    @Resource
    private AppService appService;

    /**
     * 获取应用列表
     *
     * @param params 请求参数
     * @return
     */
    @PostMapping(name = "获取应用列表", value = "/getAppList")
    public MsgObject getAppList(@RequestBody GetAppListParams params) {
        long userId = WebContext.getContext().getUserId();
        Result<AppListResult> result = this.appService.getAppList(userId, params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 获取应用列表，提供给搜索框用
     *
     * @return
     */
    @PostMapping(name = "获取应用列表，提供给搜索框用", value = "/getSearchList")
    public MsgObject getSearchList(String tenantId) {
        long userId = WebContext.getContext().getUserId();
        Result<List<SearchItem>> result = this.appService.getSearchList(userId, tenantId);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }

    /**
     * 停止应用
     *
     * @param params 请求参数
     * @return
     */
    @PostMapping(name = "停止应用", value = "/stop")
    public MsgObject stopApp(@RequestBody StopAppParams params) {
        long userId = WebContext.getContext().getUserId();
        Result<Void> result = this.appService.updateState(userId, params.getId(), AppState.STOP);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 启动应用
     *
     * @param params 请求参数
     * @return
     */
    @PostMapping(name = "启动应用", value = "/start")
    public MsgObject startApp(@RequestBody StartAppParams params) {
        long userId = WebContext.getContext().getUserId();
        Result<Void> result = this.appService.updateState(userId, params.getId(), AppState.RUN);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }
}