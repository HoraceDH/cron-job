package cn.horace.cronjob.scheduler.starter;

import com.google.gson.JsonParser;
import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.im.v1.enums.CreateMessageReceiveIdTypeEnum;
import com.lark.oapi.service.im.v1.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created in 2025-03-18 23:11.
 *
 * @author Horace
 */
@Configuration
public class FeishuTest {
    private static final Logger logger = LoggerFactory.getLogger(FeishuTest.class);

    @PostConstruct
    public void init() throws Exception {
        // 构建client
        Client client = Client.newBuilder("cli_a752501b970c101c", "WKxCe7x0EgXlmfyaJq6lAcb5S4M6ndnF").build();

        // 创建请求对象
        ListChatReq listChatReq = ListChatReq.newBuilder()
                .sortType("ByCreateTimeAsc")
                .pageSize(20)
                .build();

        // 发起请求
        ListChatResp listChatResp = client.im().v1().chat().list(listChatReq);

        // 处理服务端错误
        if (!listChatResp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s, listChatResp:%s",
                    listChatResp.getCode(), listChatResp.getMsg(), listChatResp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(new String(listChatResp.getRawResponse().getBody(), StandardCharsets.UTF_8)))));
            return;
        }

        // 业务数据处理
        System.out.println(Jsons.DEFAULT.toJson(listChatResp.getData()));


//        // 构建client
//        Client client = Client.newBuilder("YOUR_APP_ID", "YOUR_APP_SECRET").build();

        // 创建请求对象
        CreateMessageReq createMsgReq = CreateMessageReq.newBuilder()
                .createMessageReqBody(CreateMessageReqBody.newBuilder()
                        .receiveId("oc_52daf27092f52584bfef977b6d0a99f6")
                        .msgType("text")
                        .content("{\"text\":\"test content\"}")
                        .uuid(UUID.randomUUID().toString())
                        .build())
                .receiveIdType(CreateMessageReceiveIdTypeEnum.CHAT_ID)
                .build();

        // 发起请求
        CreateMessageResp createMsgResp = client.im().v1().message().create(createMsgReq);

        // 处理服务端错误
        if (!createMsgResp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s, listChatResp:%s",
                    createMsgResp.getCode(), createMsgResp.getMsg(), createMsgResp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(new String(createMsgResp.getRawResponse().getBody(), StandardCharsets.UTF_8)))));
            return;
        }

        // 业务数据处理
        System.out.println(Jsons.DEFAULT.toJson(createMsgResp.getData()));
    }
}