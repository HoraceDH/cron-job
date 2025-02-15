package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.bean.TaskResult;
import cn.horace.cronjob.scheduler.bean.params.TaskRegisterParams;
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
 * 任务相关接口
 * <p>
 *
 * @author Horace
 */
@RestController
@RequestMapping("/openapi/task")
public class ClientTaskController {
    private static final Logger logger = LoggerFactory.getLogger(ClientTaskController.class);
    @Resource
    private TaskService taskService;

    /**
     * 注册任务
     *
     * @return
     */
    @PostMapping("/register")
    public MsgObject register(@RequestBody List<TaskRegisterParams> params) {
        Result<Void> result = this.taskService.register(params);
        return MsgObject.msgCodes(result);
    }

    /**
     * 任务执行完成
     *
     * @return
     */
    @PostMapping("/complete")
    public MsgObject complete(@RequestBody TaskResult params) {
        Result<Void> result = this.taskService.complete(params);
        return MsgObject.msgCodes(result);
    }
}