package cn.horace.cronjob.scheduler.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件类型
 * <p>
 *
 * @author Horace
 */
public enum EventType {
    /**
     * 任务队列已入库
     */
    TASK_QUEUE_TO_DATABASE(1, "任务队列已入库"),

    ;
    private int value;
    private String msg;

    private static Map<Integer, EventType> map = new HashMap<>();

    static {
        for (EventType object : EventType.values()) {
            map.put(object.getValue(), object);
        }
    }

    EventType(int value, String msg) {
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
    public static EventType from(int value) {
        return map.get(value);
    }
}