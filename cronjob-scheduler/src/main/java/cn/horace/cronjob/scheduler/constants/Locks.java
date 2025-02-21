package cn.horace.cronjob.scheduler.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 注意需要手动插入一条记录到数据库中
 *
 * @author Horace
 */
public enum Locks {
    /**
     * 生成任务的分布式锁
     */
    LOCK_GENERATE_TASK(0, "生成调度任务的分布式锁"),
    /**
     * 生成统计数据的分布式锁
     */
    LOCK_GENERATE_STATISTICS(1, "生成统计数据的分布式锁"),
    /**
     * 定期检测是否需要停止没有执行器的应用
     */
    LOCK_DETECTION_AND_STOP_APP(2, "定期检测是否需要停止没有执行器的应用"),
    ;
    private int value;
    private String msg;

    private static Map<Integer, Locks> map = new HashMap<>();

    static {
        for (Locks object : Locks.values()) {
            map.put(object.getValue(), object);
        }
    }

    Locks(int value, String msg) {
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
    public static Locks from(int value) {
        return map.get(value);
    }
}