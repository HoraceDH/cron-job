package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.params.GetExecutorListParams;
import cn.horace.cronjob.scheduler.bean.params.GetTagListParams;
import cn.horace.cronjob.scheduler.bean.result.ExecutorListResult;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.context.WebContext;
import cn.horace.cronjob.scheduler.entities.TaskEntity;
import cn.horace.cronjob.scheduler.service.ExecutorService;
import cn.horace.cronjob.scheduler.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 执行器
 * <p>
 *
 * @author Horace
 */
@RestController
@RequestMapping("/manager-api/executor")
public class ExecutorController {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorController.class);
    @Resource
    private ExecutorService executorService;
    @Resource
    private TaskService taskService;

    /**
     * 获取调度器列表
     *
     * @param params 请求参数
     * @return
     */
    @PostMapping(name = "获取执行器列表", value = "/getExecutorList")
    public MsgObject getExecutorList(@RequestBody GetExecutorListParams params) {
        long userId = WebContext.getContext().getUserId();
        Result<ExecutorListResult> result = this.executorService.getExecutorList(userId, params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 获取租户列表，提供给搜索框用
     *
     * @return
     */
    @PostMapping(name = "获取租户列表，提供给搜索框用", value = "/getSearchList")
    public MsgObject getSearchList(@RequestBody GetTagListParams params) {
        TaskEntity task = this.taskService.getTaskDetail(Long.parseLong(params.getTaskId()));
        Result<List<SearchItem>> result = this.executorService.getSearchList(task.getAppId());
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }
}