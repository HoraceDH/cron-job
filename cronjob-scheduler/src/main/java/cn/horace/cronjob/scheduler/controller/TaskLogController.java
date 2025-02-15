package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.scheduler.bean.params.GetCronTaskLogListParams;
import cn.horace.cronjob.scheduler.bean.result.TaskLogItem;
import cn.horace.cronjob.scheduler.bean.result.TaskLogListResult;
import cn.horace.cronjob.scheduler.service.TaskLogService;
import cn.horace.cronjob.scheduler.context.WebContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * @author Horace
 */
@RestController
@RequestMapping("/manager-api/tasklog")
public class TaskLogController {
    private static final Logger logger = LoggerFactory.getLogger(TaskLogController.class);
    @Resource
    private TaskLogService taskLogService;

    /**
     * 获取任务日志列表
     *
     * @param params 请求参数
     * @return
     */
    @PostMapping(name = "获取任务日志列表", value = "/getTaskLogList")
    public MsgObject getTaskList(@RequestBody GetCronTaskLogListParams params) {
        long userId = WebContext.getContext().getUserId();
        try {
            Result<TaskLogListResult> result = this.taskLogService.getTaskLogList(userId, params);
            if (result.isSuccess()) {
                return MsgObject.success(result.getData());
            }
            return new MsgObject(result.getMsgCodes().getCode(), result.getMsgCodes().getMsg() + "请尝试缩短查询范围！");
        } catch (Exception e) {
            return new MsgObject(MsgCodes.ERROR_SYSTEM.getCode(), MsgCodes.ERROR_SYSTEM.getMsg() + "请尝试缩短查询范围！");
        }
    }


    /**
     * 获取任务日志详情
     *
     * @param id 任务日志ID
     * @return
     */
    @PostMapping(name = "获取任务日志详情", value = "/getTaskLog")
    public MsgObject getTaskLog(String id) {
        if (StringUtils.isBlank(id) || Long.parseLong(id) <= 0) {
            return MsgObject.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        long userId = WebContext.getContext().getUserId();
        Result<TaskLogItem> result = this.taskLogService.getTaskLog(userId, Long.parseLong(id));
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }
}