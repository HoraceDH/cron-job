package cn.horace.cronjob.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务运行状态
 * <p>
 *
 * @author Horace
 */
public enum TaskRunState {
    /**
     * 启动
     */
    STARTUP(1, "启动"),
    STOP(2, "停止"),

    ;
    private int value;
    private String msg;

    private static Map<Integer, TaskRunState> map = new HashMap<>();

    static {
        for (TaskRunState object : TaskRunState.values()) {
            map.put(object.getValue(), object);
        }
    }

    TaskRunState(int value, String msg) {
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
    public static TaskRunState from(int value) {
        return map.get(value);
    }
}