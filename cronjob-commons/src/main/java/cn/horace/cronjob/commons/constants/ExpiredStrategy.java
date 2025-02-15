package cn.horace.cronjob.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 调度策略
 * <p>
 *
 * @author Horace
 */
public enum ExpiredStrategy {
    /**
     * 过期的任务直接丢弃，适合密集型场景
     */
    EXPIRED_DISCARD(1, "过期的任务直接丢弃，适合密集型场景"),
    /**
     * 过期的任务依然调度，适合松散型场景
     */
    EXPIRED_EXECUTE(2, "过期的任务依然调度，适合松散型场景"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, ExpiredStrategy> map = new HashMap<>();

    static {
        for (ExpiredStrategy object : ExpiredStrategy.values()) {
            map.put(object.getValue(), object);
        }
    }

    ExpiredStrategy(int value, String msg) {
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
    public static ExpiredStrategy from(int value) {
        return map.get(value);
    }
}