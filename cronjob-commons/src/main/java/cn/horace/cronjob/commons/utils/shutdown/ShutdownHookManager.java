package cn.horace.cronjob.commons.utils.shutdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 应用程序关闭钩子管理器，可以优先于系统的关闭钩子运行，可用于一些需要在停止应用程序前，提前关闭资源的操作
 * <p>
 *
 * @author Horace
 */
public class ShutdownHookManager {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHookManager.class);
    private static final Runnable ORIGIN_RUNNABLE;
    private static final List<ShutdownHook> BEFORE_HOOKS = new ArrayList<>();

    private ShutdownHookManager() {
    }

    static {
        // 该现成占位使用，不需要做什么，主要是保证Shutdown.hooks被初始化
        Thread thread = new Thread(() -> {
        }, "no-work");
        thread.setDaemon(true);
        Runtime.getRuntime().addShutdownHook(thread);

        try {
            Class<?> clazz = Class.forName("java.lang.Shutdown");
            Field hooksField = clazz.getDeclaredField("hooks");
            hooksField.setAccessible(true);
            Runnable[] hooks = (Runnable[]) hooksField.get(null);
            // 获取原始的Hook运行对象
            ORIGIN_RUNNABLE = hooks[1];
            // 替换为自己的包装器
            hooks[1] = new RunnableWrapper();
            hooksField.setAccessible(false);
        } catch (Exception e) {
            logger.error("modify shutdown hook error, msg:{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 包装器，用于控制优先级高的Hook先运行
     */
    static class RunnableWrapper implements Runnable {
        @Override
        public void run() {
            runShutdownHooks();
        }
    }

    /**
     * 运行关闭钩子
     */
    private static void runShutdownHooks() {
        BEFORE_HOOKS.sort(Comparator.comparingInt(ShutdownHook::getPriority));

        // 优先运行系统之前的hook
        for (ShutdownHook hook : BEFORE_HOOKS) {
            try {
                logger.info("start run before shutdown hook:{}, priority:{}", hook.getName(), hook.getPriority());
                hook.run();
            } catch (Exception e) {
                logger.error("run before shutdown hook error, hook:{}, priority:{}, msg:{}", hook.getName(), hook.getPriority(), e.getMessage(), e);
            }
        }

        // 运行常规的hook
        ORIGIN_RUNNABLE.run();
    }

    /**
     * 添加一个关闭钩子
     *
     * @param shutdownHook 关闭钩子
     */
    public static void addShutdownHook(ShutdownHook shutdownHook) {
        BEFORE_HOOKS.add(shutdownHook);
    }
}