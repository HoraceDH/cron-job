package cn.horace.cronjob.scheduler.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 告警通道
 * <p>
 * Created in 2025-03-18 23:37.
 *
 * @author Horace
 */
public enum AlarmChannel {
    /**
     * 飞书或者Larkin
     */
    Lark(1, "飞书/Lark"),
    WEIXIN_WORK(2, "企业微信"),
    EMAIL(3, "邮件"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, AlarmChannel> map = new HashMap<>();

    static {
        for (AlarmChannel object : AlarmChannel.values()) {
            map.put(object.getValue(), object);
        }
    }

    AlarmChannel(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 根据值类型找枚举
     *
     * @param value 值
     * @return
     */
    public static AlarmChannel from(int value) {
        return map.get(value);
    }
}