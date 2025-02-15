package cn.horace.cronjob.scheduler.strategy;

import cn.horace.cronjob.commons.constants.RouterStrategy;
import cn.horace.cronjob.scheduler.entities.ExecutorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机路由策略
 * <p>
 *
 * @author Horace
 */
@Component
public class RandomRouterStrategyHandlerImpl implements RouterStrategyHandler {
    private static final Logger logger = LoggerFactory.getLogger(RandomRouterStrategyHandlerImpl.class);
    private Random random = new Random();

    /**
     * 路由策略
     *
     * @return
     */
    @Override
    public RouterStrategy getStrategy() {
        return RouterStrategy.RANDOM;
    }

    @Override
    public List<ExecutorEntity> route(List<ExecutorEntity> executors) {
        int index = this.random.nextInt(executors.size());
        ExecutorEntity executor = executors.get(index);
        List<ExecutorEntity> result = new ArrayList<>(1);
        result.add(executor);
        return result;
    }
}