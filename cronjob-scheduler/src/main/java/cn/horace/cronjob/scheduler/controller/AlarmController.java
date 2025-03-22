package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.params.GetGroupListParams;
import cn.horace.cronjob.scheduler.bean.params.SendAlarmParams;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.service.AlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 告警管理
 * Created in 2025-03-19 22:43.
 *
 * @author Horace
 */
@RestController
@RequestMapping("/manager-api/alarm")
public class AlarmController {
    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);
    @Resource
    private AlarmService alarmService;

    /**
     * 获取告警渠道列表，提供给搜索框用
     *
     * @return
     */
    @PostMapping(name = "获取租户列表，提供给搜索框用", value = "/getSearchList")
    public MsgObject getSearchList() {
        Result<List<SearchItem>> result = this.alarmService.getSearchList();
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }

    /**
     * 获取告警渠道的群组列表，提供给搜索框用
     *
     * @return
     */
    @PostMapping(name = "获取告警渠道的群组列表，提供给搜索框用", value = "/getGroupList")
    public MsgObject getGroupList(@RequestBody GetGroupListParams params) {
        Result<List<SearchItem>> result = this.alarmService.getGroupList(params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }

    /**
     * 发送一次告警信息
     *
     * @return
     */
    @PostMapping(name = "发送一次告警信息", value = "/sendAlarm")
    public MsgObject sendAlarm(@RequestBody SendAlarmParams params) {
        params.setOwner("horace");
        params.setTenantName("horace");
        params.setAppName("example-executor");
        params.setTaskName("测试任务");
        params.setTaskMethod("cn.horace.cronjob.examples.tasks.DemoCronTask.handle");
        params.setFailedReason("测试告警信息");
        params.setUrl("https://cronjob.horace.cn");
        Result<Void> result = this.alarmService.sendAlarm(params);
        if (result.isSuccess()) {
            return MsgObject.success();
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }
}