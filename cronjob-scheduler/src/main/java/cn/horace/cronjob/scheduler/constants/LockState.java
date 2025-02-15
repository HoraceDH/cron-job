package cn.horace.cronjob.scheduler.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 锁状态
 * <p>
 *
 * @author Horace
 */
public enum LockState {
    /**
     * 已加锁
     */
    LOCKED(1, "已加锁"),
    RELEASED(2, "已释放"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, LockState> map = new HashMap<>();

    static {
        for (LockState object : LockState.values()) {
            map.put(object.getValue(), object);
        }
    }

    LockState(int value, String msg) {
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
    public static LockState from(int value) {
        return map.get(value);
    }
}