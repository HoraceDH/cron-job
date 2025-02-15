package cn.horace.cronjob.executor.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务对象相关适配器
 * <p>
 *
 * @author Horace
 */
public class TaskAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TaskAdapter.class);
    private static TaskAdapter INSTANCE = new TaskAdapter();

    private TaskAdapter() {
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static TaskAdapter getInstance() {
        return INSTANCE;
    }

}