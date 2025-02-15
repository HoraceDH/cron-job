package cn.horace.cronjob.commons.utils.shutdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 具有优先级顺序的关闭钩子
 * <p>
 *
 * @author Horace
 */
public abstract class ShutdownHook implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);
    /**
     * 给Hook起一个名字，方便管理与追溯
     */
    private String name;
    /**
     * 优先级，值越小，越靠前
     */
    private int priority;

    public ShutdownHook(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}