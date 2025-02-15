package cn.horace.cronjob.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务失败类型
 * <p>
 *
 * @author Horace
 */
public enum FailureType {
    /**
     * 未找到在线的执行器
     */
    FAILURE_NOT_FOUND_EXECUTOR(0, "未找到在线的执行器"),
    /**
     * 路由错误，路由后未找到执行器
     */
    FAILURE_ROUTE_ERROR(1, "路由错误，路由后未找到执行器"),
    /**
     * 更新状态失败
     */
    FAILURE_UPDATE_STATE(2, "调度时，更新状态失败"),
    /**
     * 发起请求给执行器时抛出异常
     */
    FAILURE_EXCEPTION(3, "发起请求给执行器时抛出异常"),
    /**
     * 执行器处理失败
     */
    FAILURE_EXECUTOR(4, "执行器处理失败"),
    /**
     * 执行器未反馈结果
     */
    FAILURE_NO_COMPLETE(5, "执行器未反馈结果"),
    /**
     * 执行器已关闭
     */
    FAILURE_EXECUTOR_SHUTDOWN(6, "执行器已关闭"),

    ;
    private int value;
    private String msg;

    private static Map<Integer, FailureType> map = new HashMap<>();

    static {
        for (FailureType object : FailureType.values()) {
            map.put(object.getValue(), object);
        }
    }

    FailureType(int value, String msg) {
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
    public static FailureType from(int value) {
        return map.get(value);
    }
}