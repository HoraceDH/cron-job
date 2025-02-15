package cn.horace.cronjob.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行器状态
 * <p>
 *
 * @author Horace
 */
public enum ExecutorState {
    /**
     * 在线
     */
    ONLINE(1, "在线"),
    OFFLINE(2, "离线"),

    ;
    private int value;
    private String msg;

    private static Map<Integer, ExecutorState> map = new HashMap<>();

    static {
        for (ExecutorState object : ExecutorState.values()) {
            map.put(object.getValue(), object);
        }
    }

    ExecutorState(int value, String msg) {
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
    public static ExecutorState from(int value) {
        return map.get(value);
    }
}