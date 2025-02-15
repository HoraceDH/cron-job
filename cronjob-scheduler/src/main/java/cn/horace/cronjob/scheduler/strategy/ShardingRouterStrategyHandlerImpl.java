package cn.horace.cronjob.scheduler.strategy;

import cn.horace.cronjob.commons.constants.RouterStrategy;
import cn.horace.cronjob.scheduler.entities.ExecutorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分片路由策略
 * <p>
 *
 * @author Horace
 */
@Component
public class ShardingRouterStrategyHandlerImpl implements RouterStrategyHandler {
    private static final Logger logger = LoggerFactory.getLogger(ShardingRouterStrategyHandlerImpl.class);

    /**
     * 路由策略
     *
     * @return
     */
    @Override
    public RouterStrategy getStrategy() {
        return RouterStrategy.SHARDING;
    }

    @Override
    public List<ExecutorEntity> route(List<ExecutorEntity> executors) {
        return executors;
    }
}