package cn.horace.cronjob.scheduler.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created in 2025-03-22 19:16.
 *
 * @author Horace
 */
public enum AlarmState {
    /**
     * 初始化
     */
    INIT(0, "初始化"),
    SUCCESS(1, "告警成功"),
    FAILED(2, "告警失败"),

    ;
    private int value;
    private String msg;

    private static Map<Integer, AlarmState> map = new HashMap<>();

    static {
        for (AlarmState object : AlarmState.values()) {
            map.put(object.getValue(), object);
        }
    }

    AlarmState(int value, String msg) {
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
    public static AlarmState from(int value) {
        return map.get(value);
    }
}