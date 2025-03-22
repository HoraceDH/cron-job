package cn.horace.cronjob.scheduler.alarm;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.scheduler.bean.Message;
import cn.horace.cronjob.scheduler.bean.params.SendAlarmParams;
import cn.horace.cronjob.scheduler.bean.result.AlarmGroup;
import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.constants.AlarmType;
import com.lark.oapi.Client;
import com.lark.oapi.service.im.v1.enums.CreateMessageReceiveIdTypeEnum;
import com.lark.oapi.service.im.v1.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 飞书或者Lark告警
 * <p>
 * Created in 2025-03-18 23:40.
 *
 * @author Horace
 */
@Service
public class LarkAlarmHandlerImpl implements AlarmHandler {
    private static final Logger logger = LoggerFactory.getLogger(LarkAlarmHandlerImpl.class);
    @Resource
    private AppConfig appConfig;
    private boolean available;
    private Client client;

    @PostConstruct
    public void init() {
        if (StringUtils.isBlank(this.appConfig.getLarkAppId()) || StringUtils.isBlank(this.appConfig.getLarkAppSecret())) {
            logger.info("lark/feishu app id or app secret are empty, skip lark alarm handler init");
            return;
        }
        this.client = Client.newBuilder(this.appConfig.getLarkAppId(), this.appConfig.getLarkAppSecret()).build();
        this.available = true;
        logger.info("init lark/feishu alarm handler success.");
    }

    /**
     * 获取告警通道
     *
     * @return
     */
    @Override
    public AlarmType getAlarmChannel() {
        return AlarmType.Lark;
    }

    /**
     * 获取消息类型
     *
     * @return
     */
    @Override
    public String getMsgType() {
        // 卡片消息
        return "interactive";
    }

    /**
     * 是否可用
     *
     * @return
     */
    @Override
    public boolean isAvailable() {
        return this.available;
    }

    /**
     * 获取告警群列表
     *
     * @return
     */
    @Override
    public Result<List<AlarmGroup>> getGroupList() {
        Result<List<AlarmGroup>> result = Result.success();
        try {
            // 一次性拿100个最近的群够用了
            ListChatReq listChatReq = ListChatReq.newBuilder().sortType("ByCreateTimeAsc").pageSize(100).build();
            ListChatResp listChatResp = client.im().v1().chat().list(listChatReq);
            if (listChatResp.getCode() != 0) {
                logger.error("get group list from lark/feishu failed, code: {}, msg: {}", listChatResp.getCode(), listChatResp.getMsg());
                result = Result.msgCodes(MsgCodes.ERROR_EXTERNAL);
                result.setMsg("get group list from lark/feishu failed, " + listChatResp.getCode() + ":" + listChatResp.getMsg());
                return result;
            }

            List<AlarmGroup> alarmGroups = new ArrayList<>();
            ListChatRespBody data = listChatResp.getData();
            for (ListChat item : data.getItems()) {
                AlarmGroup alarmGroup = new AlarmGroup();
                alarmGroup.setId(item.getChatId());
                alarmGroup.setName(item.getName());
                alarmGroup.setAvatar(item.getAvatar());
                alarmGroups.add(alarmGroup);
            }
            result.setData(alarmGroups);
            return result;
        } catch (Exception e) {
            logger.error("get group list from lark/feishu error, msg: {}", e.getMessage(), e);
            return Result.msgCodes(MsgCodes.ERROR_EXTERNAL);
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息对象
     * @return
     */
    @Override
    public Result<Void> sendMessage(Message message) {
        try {
            CreateMessageReq createMsgReq = CreateMessageReq.newBuilder()
                    .createMessageReqBody(CreateMessageReqBody.newBuilder()
                            .receiveId(message.getChatId())
                            .msgType(this.getMsgType())
                            .content(message.getMsg())
                            .uuid(UUID.randomUUID().toString())
                            .build())
                    .receiveIdType(CreateMessageReceiveIdTypeEnum.CHAT_ID)
                    .build();

            // 发起请求
            CreateMessageResp createMsgResp = client.im().v1().message().create(createMsgReq);
            if (createMsgResp.getCode() != 0) {
                logger.error("send message to lark/feishu failed, code: {}, msg: {}", createMsgResp.getCode(), createMsgResp.getMsg());
                Result<Void> result = Result.msgCodes(MsgCodes.ERROR_EXTERNAL);
                result.setMsg("send message to lark/feishu failed, " + createMsgResp.getCode() + ":" + createMsgResp.getMsg());
                return result;
            }
            return Result.success();
        } catch (Exception e) {
            logger.error("send message to lark/feishu error, message:{}, msg: {}", message, e.getMessage(), e);
            return Result.msgCodes(MsgCodes.ERROR_EXTERNAL);
        }
    }

    /**
     * 构建告警消息
     *
     * @param params 参数
     * @return
     */
    @Override
    public String buildMessage(SendAlarmParams params) {
        String msg = "{\"config\":{\"wide_screen_mode\":true},\"elements\":[{\"tag\":\"div\",\"text\":{\"content\":\"**负责人：${owner}**\\r\\n**租户名称：${tenantName}**\\r\\n**应用名称：${appName}**\\r\\n**任务名称：${taskName}**\\r\\n**任务方法：${taskMethod}**\\r\\n**日志ID：${taskLogId}**\\r\\n**失败原因：[点击查看](${url})**\",\"tag\":\"lark_md\"}},{\"actions\":[{\"tag\":\"button\",\"text\":{\"content\":\"查看详情\",\"tag\":\"plain_text\"},\"type\":\"primary\",\"url\":\"${url}\"}],\"tag\":\"action\"}],\"header\":{\"template\":\"red\",\"title\":{\"content\":\"任务执行失败\",\"tag\":\"plain_text\"}}}";
        msg = msg.replace("${tenantName}", params.getTenantName());
        msg = msg.replace("${appName}", params.getAppName());
        msg = msg.replace("${taskName}", params.getTaskName());
        msg = msg.replace("${taskMethod}", params.getTaskMethod());
        msg = msg.replace("${failedReason}", params.getFailedReason());
        msg = msg.replace("${taskLogId}", params.getTaskLogId() + "");
        msg = msg.replace("${url}", params.getUrl());
        msg = msg.replace("${owner}", params.getOwner());
        return msg;
    }
}