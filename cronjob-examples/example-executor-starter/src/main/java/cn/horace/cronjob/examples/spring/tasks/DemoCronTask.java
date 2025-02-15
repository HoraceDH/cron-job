package cn.horace.cronjob.examples.spring.tasks;

import cn.horace.cronjob.commons.bean.TaskParams;
import cn.horace.cronjob.commons.constants.RouterStrategy;
import cn.horace.cronjob.executor.annotation.TaskConfig;
import cn.horace.cronjob.executor.bean.HandlerResult;
import cn.horace.cronjob.executor.handler.TaskHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 演示任务
 *
 * @author Horace
 */
@Component
@TaskConfig(name = "Spring测试任务", cron = "* * * * * ? ", routerStrategy = RouterStrategy.RANDOM)
public class DemoCronTask implements TaskHandler {
    private static final Logger logger = LoggerFactory.getLogger(DemoCronTask.class);

    /**
     * 执行任务的方法
     *
     * @param params 任务参数
     * @return 任务执行结果，如果执行成功，则返回HandlerResult.success()，如果执行失败，则返回HandlerResult.fail()，返回null，也判定是失败
     */
    @Override
    public HandlerResult handle(TaskParams params) {
        logger.info("task handler..., params:{}", params);
        Random random = new Random();
        int delay = random.nextInt(200);
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(delay));
        return HandlerResult.success();
    }
}