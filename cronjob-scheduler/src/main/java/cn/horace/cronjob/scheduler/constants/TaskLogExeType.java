package cn.horace.cronjob.scheduler.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务日志执行类型
 * <p>
 *
 * @author Horace
 */
public enum TaskLogExeType {
    NORMAL(0, "常规任务调度"),
    MANAGER_NOW(1, "管理后台立即执行"),
    EXPIRED(2, "过期执行"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, TaskLogExeType> map = new HashMap<>();

    static {
        for (TaskLogExeType object : TaskLogExeType.values()) {
            map.put(object.getValue(), object);
        }
    }

    TaskLogExeType(int value, String msg) {
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
    public static TaskLogExeType from(int value) {
        return map.get(value);
    }
}