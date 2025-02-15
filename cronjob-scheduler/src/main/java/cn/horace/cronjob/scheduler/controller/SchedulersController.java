package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.params.GetSchedulerInstanceListParams;
import cn.horace.cronjob.scheduler.bean.result.SchedulerInstanceListResult;
import cn.horace.cronjob.scheduler.service.SchedulerInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 调度器
 * <p>
 *
 * @author Horace
 */
@RestController
@RequestMapping("/manager-api/schedulers")
public class SchedulersController {
    private static final Logger logger = LoggerFactory.getLogger(SchedulersController.class);
    @Resource
    private SchedulerInstanceService schedulerInstanceService;

    /**
     * 获取调度器列表
     *
     * @param params 请求参数
     * @return
     */
    @PostMapping(name = "获取调度器列表", value = "/getSchedulersList")
    public MsgObject getSchedulerList(@RequestBody GetSchedulerInstanceListParams params) {
        Result<SchedulerInstanceListResult> result = this.schedulerInstanceService.getSchedulerInstanceList(params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }
}