package cn.horace.cronjob.scheduler.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created in 2025-03-19 21:37.
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class Message {
    /**
     * 群聊ID
     */
    private String chatId;
    /**
     * 消息内容
     */
    private String msg;
}