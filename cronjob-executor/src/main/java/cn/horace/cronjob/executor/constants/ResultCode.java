package cn.horace.cronjob.executor.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务执行结果码
 *
 * Created in 2025-01-10 12:04.
 *
 * @author Horace
 */
public enum ResultCode {
    // 成功
    SUCCESS(0, ""),
    FAILED(1, "任务执行失败"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, ResultCode> map = new HashMap<>();

    static {
        for (ResultCode object : ResultCode.values()) {
            map.put(object.getValue(), object);
        }
    }

    ResultCode(int value, String msg) {
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
    public static ResultCode from(int value) {
        return map.get(value);
    }
}