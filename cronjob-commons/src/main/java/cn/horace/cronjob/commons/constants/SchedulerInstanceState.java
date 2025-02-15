package cn.horace.cronjob.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 调度器实例状态
 * <p>
 *
 * @author Horace
 */
public enum SchedulerInstanceState {
    /**
     * 在线
     */
    ONLINE(1, "在线"),
    OFFLINE(2, "离线"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, SchedulerInstanceState> map = new HashMap<>();

    static {
        for (SchedulerInstanceState object : SchedulerInstanceState.values()) {
            map.put(object.getValue(), object);
        }
    }

    SchedulerInstanceState(int value, String msg) {
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
    public static SchedulerInstanceState from(int value) {
        return map.get(value);
    }
}