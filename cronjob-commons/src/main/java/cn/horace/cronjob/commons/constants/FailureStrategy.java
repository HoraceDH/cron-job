package cn.horace.cronjob.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 失败策略
 * <p>
 *
 * @author Horace
 */
public enum FailureStrategy {
    /**
     * 失败重试
     */
    FAILURE_RETRY(1, "失败重试"),
    /**
     * 失败丢弃
     */
    FAILURE_DISCARD(2, "失败丢弃"),

    ;
    private int value;
    private String msg;

    private static Map<Integer, FailureStrategy> map = new HashMap<>();

    static {
        for (FailureStrategy object : FailureStrategy.values()) {
            map.put(object.getValue(), object);
        }
    }

    FailureStrategy(int value, String msg) {
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
    public static FailureStrategy from(int value) {
        return map.get(value);
    }
}