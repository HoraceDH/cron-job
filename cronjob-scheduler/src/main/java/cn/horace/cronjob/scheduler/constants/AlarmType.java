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
public enum AlarmType {
    /**
     * 不设置告警
     */
    NONE(0, "不设置告警"),
    FeiShu(1, "飞书"),
    Lark(2, "Lark"),
    WEIXIN_WORK(3, "企业微信"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, AlarmType> map = new HashMap<>();

    static {
        for (AlarmType object : AlarmType.values()) {
            map.put(object.getValue(), object);
        }
    }

    AlarmType(int value, String msg) {
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
    public static AlarmType from(int value) {
        return map.get(value);
    }
}