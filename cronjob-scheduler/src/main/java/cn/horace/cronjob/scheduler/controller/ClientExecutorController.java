package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.params.ExecutorHeartbeatParams;
import cn.horace.cronjob.scheduler.bean.params.ExecutorRegisterParams;
import cn.horace.cronjob.scheduler.bean.params.ExecutorUnregisterParams;
import cn.horace.cronjob.scheduler.service.ExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 执行器相关接口
 * <p>
 *
 * @author Horace
 */
@RestController
@RequestMapping("/openapi/executor")
public class ClientExecutorController {
    private static final Logger logger = LoggerFactory.getLogger(ClientExecutorController.class);
    @Resource
    private ExecutorService executorService;

    /**
     * 注册执行器
     *
     * @return
     */
    @PostMapping("/register")
    public MsgObject register(@RequestBody ExecutorRegisterParams params) {
        Result<Void> result = this.executorService.register(params);
        return MsgObject.msgCodes(result);
    }

    /**
     * 注销下线执行器
     *
     * @return
     */
    @PostMapping("/unregister")
    public MsgObject unregister(@RequestBody ExecutorUnregisterParams params) {
        Result<Void> result = this.executorService.unregister(params);
        return MsgObject.msgCodes(result);
    }

    /**
     * 心跳
     *
     * @return
     */
    @PostMapping("/heartbeat")
    public MsgObject heartbeat(@RequestBody ExecutorHeartbeatParams params) {
        Result<Void> result = this.executorService.heartbeat(params);
        return MsgObject.msgCodes(result);
    }
}