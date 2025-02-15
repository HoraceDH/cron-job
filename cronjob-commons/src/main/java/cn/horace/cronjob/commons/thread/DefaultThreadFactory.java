package cn.horace.cronjob.commons.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 * <p>
 *
 * @author Horace
 */
public class DefaultThreadFactory implements ThreadFactory {
    private ThreadGroup group;
    private AtomicInteger threadNumber = new AtomicInteger(1);
    private String namePrefix;
    private boolean daemon = false;

    public DefaultThreadFactory(String namePrefix) {
        this(false, namePrefix);
    }

    public DefaultThreadFactory(boolean daemon, String namePrefix) {
        this.daemon = daemon;
        this.namePrefix = namePrefix + "-";

        SecurityManager s = System.getSecurityManager();
        this.group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(this.group, r,
                this.namePrefix + this.threadNumber.getAndIncrement(),
                0);
        t.setDaemon(this.daemon);

        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
