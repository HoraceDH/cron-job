package cn.horace.cronjob.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 路由策略
 * <p>
 *
 * @author Horace
 */
public enum RouterStrategy {
    /**
     * 随机策略
     */
    RANDOM(1, "随机策略，适合绝大多数场景"),
    SHARDING(2, "分片策略，适合大任务分片拆分执行场景"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, RouterStrategy> map = new HashMap<>();

    static {
        for (RouterStrategy object : RouterStrategy.values()) {
            map.put(object.getValue(), object);
        }
    }

    RouterStrategy(int value, String msg) {
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
    public static RouterStrategy from(int value) {
        return map.get(value);
    }
}