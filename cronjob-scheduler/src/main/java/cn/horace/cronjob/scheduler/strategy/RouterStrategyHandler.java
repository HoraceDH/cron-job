package cn.horace.cronjob.scheduler.strategy;

import cn.horace.cronjob.commons.constants.RouterStrategy;
import cn.horace.cronjob.scheduler.entities.ExecutorEntity;

import java.util.List;

/**
 * 路由策略处理器
 *
 * <p>
 *
 * @author Horace
 */
public interface RouterStrategyHandler {
    /**
     * 路由策略
     *
     * @return
     */
    RouterStrategy getStrategy();

    /**
     * 根据路由规则计算路由到哪些处理器
     *
     * @param executors 执行器列表
     * @return
     */
    List<ExecutorEntity> route(List<ExecutorEntity> executors);
}
