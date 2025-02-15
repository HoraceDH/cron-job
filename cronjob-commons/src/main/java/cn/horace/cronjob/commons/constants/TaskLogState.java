package cn.horace.cronjob.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务日志状态
 * <p>
 *
 * @author Horace
 */
public enum TaskLogState {
    INITIALIZE(1, "初始化"),
    QUEUEING(2, "队列中"),
    EXECUTION(3, "调度中"),
    EXECUTION_SUCCESS(4, "执行成功"),
    EXECUTION_FAILED(5, "执行失败"),
    EXECUTION_CANCEL(6, "取消执行（预生成日志后，任务取消等情况）"),
    EXECUTION_EXPIRED(7, "任务过期（超过执行时间而未被调度）"),
    EXECUTION_FAILED_DISCARD(8, "执行失败，已丢弃"),
    EXECUTION_FAILED_RETRYING(9, "执行失败，重试中"),
    EXECUTION_FAILED_NOT_FOUND(10, "执行失败，未找到执行方法"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, TaskLogState> map = new HashMap<>();

    static {
        for (TaskLogState object : TaskLogState.values()) {
            map.put(object.getValue(), object);
        }
    }

    TaskLogState(int value, String msg) {
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
    public static TaskLogState from(int value) {
        return map.get(value);
    }
}