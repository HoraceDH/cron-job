package cn.horace.cronjob.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用状态
 * <p>
 * Created in 2024-11-03 16:52.
 *
 * @author Horace
 */
public enum AppState {
    RUN(1, "运行状态"),
    STOP(2, "停止状态"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, AppState> map = new HashMap<>();

    static {
        for (AppState object : AppState.values()) {
            map.put(object.getValue(), object);
        }
    }

    AppState(int value, String msg) {
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
    public static AppState from(int value) {
        return map.get(value);
    }
}