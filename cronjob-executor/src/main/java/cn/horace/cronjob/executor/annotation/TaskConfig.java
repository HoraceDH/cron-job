package cn.horace.cronjob.executor.annotation;

import cn.horace.cronjob.commons.constants.ExpiredStrategy;
import cn.horace.cronjob.commons.constants.FailureStrategy;
import cn.horace.cronjob.commons.constants.RouterStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 周期任务配置注解，只会在首次注册的时候才会生效，后续可在调度管理平台修改
 * <p>
 *
 * @author Horace
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskConfig {
    /**
     * 任务名
     *
     * @return
     */
    String name();

    /**
     * CRON表达式
     *
     * @return
     */
    String cron();

    /**
     * 路由策略，默认随机策略
     *
     * @return
     */
    RouterStrategy routerStrategy() default RouterStrategy.RANDOM;

    /**
     * 过期策略，默认过期依然调度
     *
     * @return
     */
    ExpiredStrategy expiredStrategy() default ExpiredStrategy.EXPIRED_EXECUTE;

    /**
     * 过期时间，毫秒，默认3分钟过期，任务超过过期时间调度时，则走过期策略，最大过期时间5分钟
     *
     * @return
     */
    int expireTime() default 3 * 60 * 1000;

    /**
     * 失败策略，默认失败重试
     *
     * @return
     */
    FailureStrategy failureStrategy() default FailureStrategy.FAILURE_RETRY;

    /**
     * 失败最大重试次数
     *
     * @return
     */
    int maxRetryCount() default 5;

    /**
     * 失败重试间隔时间，毫秒
     *
     * @return
     */
    int failureRetryInterval() default 5000;

    /**
     * 任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒，最大10秒钟，如果是消耗大量时间的任务，建立使用独立线程池运行
     *
     * @return
     */
    int timeout() default 10 * 1000;

    /**
     * 预留tag标识
     *
     * @return
     */
    String tag() default "common";

    /**
     * 任务备注，主要是用来描述任务详情，用来做什么样的任务？方便后期维护和管理
     *
     * @return
     */
    String remark() default "";
}
