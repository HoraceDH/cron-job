package cn.horace.cronjob.scheduler.constants;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Horace
 */
public enum UserState {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    DISABLED(1, "禁用"),

    ;
    private int value;
    private String msg;

    private static Map<Integer, UserState> map = new HashMap<>();

    static {
        for (UserState object : UserState.values()) {
            map.put(object.getValue(), object);
        }
    }

    UserState(int value, String msg) {
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
    public static UserState from(int value) {
        return map.get(value);
    }
}