package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.TaskRunState;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.scheduler.bean.params.ExecuteNowParams;
import cn.horace.cronjob.scheduler.bean.params.GetTaskListParams;
import cn.horace.cronjob.scheduler.bean.result.TaskItem;
import cn.horace.cronjob.scheduler.bean.result.TaskListResult;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.service.TaskService;
import cn.horace.cronjob.scheduler.context.WebContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 调度器
 * <p>
 *
 * @author Horace
 */
@RestController
@RequestMapping("/manager-api/task")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Resource
    private TaskService taskService;

    /**
     * 获取任务列表
     *
     * @param params 请求参数
     * @return
     */
    @PostMapping(name = "获取任务列表", value = "/getTaskList")
    public MsgObject getCronTaskList(@RequestBody GetTaskListParams params) {
        long userId = WebContext.getContext().getUserId();
        Result<TaskListResult> result = this.taskService.getTaskList(userId, params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 获取任务详情
     *
     * @param id 任务ID
     * @return
     */
    @PostMapping(name = "获取任务详情", value = "/getTask")
    public MsgObject getCronTask(String id) {
        if (StringUtils.isBlank(id) || Long.parseLong(id) <= 0) {
            return MsgObject.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        long userId = WebContext.getContext().getUserId();
        Result<TaskItem> result = this.taskService.getTaskDetail(userId, Long.parseLong(id));
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 更新任务运行状态
     *
     * @param id       任务ID
     * @param runState 运行状态
     * @return
     */
    @PostMapping(name = "更新任务运行状态", value = "/updateRunState")
    public MsgObject updateRunState(String id, int runState) {
        TaskRunState taskRunState = TaskRunState.from(runState);
        if (StringUtils.isBlank(id) || Long.parseLong(id) <= 0 || taskRunState == null) {
            return MsgObject.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        long userId = WebContext.getContext().getUserId();
        Result<Void> result = this.taskService.updateRunState(userId, Long.parseLong(id), taskRunState);
        if (result.isSuccess()) {
            return MsgObject.success();
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 立即执行一次任务
     *
     * @param params 参数
     * @return
     */
    @PostMapping(name = "立即执行一次任务", value = "/executeTaskNow")
    public MsgObject executeTaskNow(@RequestBody ExecuteNowParams params) {
        if (StringUtils.isBlank(params.getTaskId()) || Long.parseLong(params.getTaskId()) <= 0) {
            return MsgObject.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        long userId = WebContext.getContext().getUserId();
        Result<String> result = this.taskService.executeTaskNow(userId, params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 获取任务列表，提供给搜索框用
     *
     * @param tenantId 租户ID
     * @param appName  应用名
     * @return
     */
    @PostMapping(name = "获取任务列表，提供给搜索框用", value = "/getSearchList")
    public MsgObject getSearchList(String tenantId, String appName) {
        long userId = WebContext.getContext().getUserId();
        Result<List<SearchItem>> result = this.taskService.getSearchList(userId, tenantId, appName);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }

    /**
     * 根据任务ID更新任务信息
     *
     * @param params 参数
     * @return
     */
    @PostMapping(name = "根据任务ID更新任务信息", value = "/updateById")
    public MsgObject updateById(@RequestBody TaskItem params) {
        long userId = WebContext.getContext().getUserId();
        Result<Void> result = this.taskService.updateById(userId, params);
        if (result.isSuccess()) {
            return MsgObject.success();
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }

    /**
     * 获取最近几次的执行时间
     *
     * @param cron Cron表达式
     * @return
     */
    @PostMapping(name = "获取最近几次的执行时间", value = "/getRecentExecutionTime")
    public MsgObject getRecentExecutionTime(String cron) {
        Result<String[]> result = this.taskService.getRecentExecutionTime(cron);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }
}