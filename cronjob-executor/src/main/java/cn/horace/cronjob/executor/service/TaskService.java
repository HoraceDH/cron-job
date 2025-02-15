package cn.horace.cronjob.executor.service;

import cn.horace.cronjob.commons.bean.TaskParams;
import cn.horace.cronjob.commons.bean.TaskResult;
import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.commons.constants.TaskLogState;
import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import cn.horace.cronjob.commons.utils.ExceptionUtils;
import cn.horace.cronjob.commons.utils.executor.GracefulThreadPoolExecutor;
import cn.horace.cronjob.executor.CronJobExecutorClient;
import cn.horace.cronjob.executor.annotation.TaskConfig;
import cn.horace.cronjob.executor.bean.HandlerResult;
import cn.horace.cronjob.executor.config.ExecutorConfig;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 任务管理服务
 * <p>
 *
 * @author Horace
 */
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final GracefulThreadPoolExecutor executeTaskExecutor = new GracefulThreadPoolExecutor(5, 80, 5, TimeUnit.MINUTES, new BlockingArrayQueue<>(1000), new DefaultThreadFactory(true, "executor-task"), false);
    private final GracefulThreadPoolExecutor sendResultExecutor = new GracefulThreadPoolExecutor(5, 80, 5, TimeUnit.MINUTES, new BlockingArrayQueue<>(1000), new DefaultThreadFactory(true, "send-result-task"), false);
    private static TaskService INSTANCE;
    private final ConcurrentHashMap<String, Object> targetObjects = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Method> targetMethods = new ConcurrentHashMap<>();
    private final ExecutorConfig config;
    private final List<cn.horace.cronjob.executor.bean.TaskConfig> tasks = new ArrayList<>();

    public TaskService(ExecutorConfig config) {
        this.config = config;

        // 解析出CronTask
        for (Object object : config.getTaskObjects()) {
            this.parseTask(object);
        }
    }

    /**
     * 停止任务
     */
    public void stop() {
        this.executeTaskExecutor.shutdownGracefully();
        this.sendResultExecutor.shutdownGracefully();
    }

    /**
     * 解析CronTask
     *
     * @param object 实例对象
     */
    private void parseTask(Object object) {
        Class<?> clazz = object.getClass();
        TaskConfig cronTaskConfig = clazz.getAnnotation(TaskConfig.class);
        if (cronTaskConfig == null) {
            throw new RuntimeException("cron job, parse task failed, the @CronTaskConfig annotation is missing, class:" + clazz.getName());
        }
        try {
            Method method = clazz.getDeclaredMethod("handle", TaskParams.class);
            String methodFullName = clazz.getName() + "." + method.getName();
            cn.horace.cronjob.executor.bean.TaskConfig taskConfig = new cn.horace.cronjob.executor.bean.TaskConfig();
            taskConfig.setTenant(this.config.getTenant());
            taskConfig.setTag(this.config.getTag());
            taskConfig.setAppName(this.config.getAppName());
            taskConfig.setAppDesc(this.config.getAppDesc());
            taskConfig.setName(cronTaskConfig.name());
            taskConfig.setCron(cronTaskConfig.cron());
            taskConfig.setTag(StringUtils.isBlank(cronTaskConfig.tag()) ? this.config.getTag() : cronTaskConfig.tag());
            taskConfig.setRemark(cronTaskConfig.remark());
            taskConfig.setRouterStrategy(cronTaskConfig.routerStrategy().getValue());
            taskConfig.setExpiredStrategy(cronTaskConfig.expiredStrategy().getValue());
            taskConfig.setExpiredTime(cronTaskConfig.expireTime());
            taskConfig.setFailureStrategy(cronTaskConfig.failureStrategy().getValue());
            taskConfig.setMaxRetryCount(cronTaskConfig.maxRetryCount());
            taskConfig.setFailureRetryInterval(cronTaskConfig.failureRetryInterval());
            taskConfig.setTimeout(cronTaskConfig.timeout());
            taskConfig.setMethod(methodFullName);
            this.targetMethods.put(methodFullName, method);
            this.targetObjects.put(methodFullName, object);
            this.tasks.add(taskConfig);
            logger.info("found cron job task, {}", taskConfig);
        } catch (Exception e) {
            logger.error("cron job, parse task exception, class:{}, msg:{}", clazz.getName(), e.getMessage(), e);
        }
    }

    /**
     * 创建实例对象
     *
     * @return
     */
    public static TaskService init(ExecutorConfig config) {
        if (INSTANCE == null) {
            synchronized (TaskService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TaskService(config);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static TaskService getInstance() {
        return INSTANCE;
    }

    /**
     * 获取目标实例对象
     *
     * @param className 类全限定名
     * @return
     */
    public Object getTaskObject(String className) {
        return this.targetObjects.get(className);
    }

    /**
     * 获取目标方法
     *
     * @param className 类全限定名
     * @return
     */
    public Method getTaskMethod(String className) {
        return this.targetMethods.get(className);
    }

    /**
     * 注册任务
     *
     * @param address 执行器地址
     */
    public void register(String address) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(this.tasks);
        String params = jsonArray.toJSONString();
        boolean success = OpenApiService.getInstance().registerTasks(address, params);

        // 如果已经停止，则不再重试
        if (CronJobExecutorClient.getInstance().isShutdown()) {
            logger.warn("cronjob executor is shutdown, don't retry register task, address:{}, config:{}", address, config);
            return;
        }

        if (!success) {
            // 如果注册不成功，则重试一次
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
            this.register(address);
        }
    }

    /**
     * 分发任务
     *
     * @param taskParams 任务对象
     */
    public void invoke(TaskParams taskParams) {
        this.executeTaskExecutor.submit(() -> {
            this.invoke0(taskParams);
        });
    }

    /**
     * 调用任务方法，立即执行
     *
     * @param taskParams 任务参数
     */
    public void invoke0(TaskParams taskParams) {
        String methodFullName = taskParams.getMethod();
        Object targetObject = this.getTaskObject(methodFullName);
        Method targetMethod = this.getTaskMethod(methodFullName);
        if (targetObject == null || targetMethod == null) {
            logger.error("dispatch task error, target object or target method is null, task:{},", taskParams);
            this.sendResult(TaskResult.failed(taskParams.getTaskLogId(), taskParams.getTaskId(), TaskLogState.EXECUTION_FAILED_NOT_FOUND));
            return;
        }

        // 开始执行目标方法
        long executionTime = taskParams.getExecutionTime();
        TaskResult taskResult = new TaskResult();
        TaskLogState state = TaskLogState.EXECUTION_SUCCESS;
        String failureReason = "";
        long startTime = System.currentTimeMillis();

        try {
            // 如果执行发生延迟，则打印日志
            long delayTime = startTime - executionTime;
            long receivedDispatcherTime = taskParams.getReceivedDispatcherTime();
            if (delayTime > 0) {
                // 接收服务端调度延迟，可能的原因有：网络波动、新的调度实例上线时短期波动
                if (taskParams.getReceivedDispatcherTime() > taskParams.getExecutionTime()) {
                    logger.warn("cron job task invoke delay, maybe the network fluctuates or new scheduler instance start, id:{}, delayTime:{}ms, executionTime:{}, receivedDispatcherTime:{}, realExecutionTime:{}, taskParams:{}",
                            taskParams.getTaskLogId(), delayTime, DateFormatUtils.format(executionTime, Constants.DATE_FORMAT), DateFormatUtils.format(receivedDispatcherTime, Constants.DATE_FORMAT),
                            DateFormatUtils.format(startTime, Constants.DATE_FORMAT), taskParams);
                } else {
                    // 也许是执行器的GC或者CPU繁忙
                    logger.warn("cron job task invoke delay, maybe the GC or CPU of the executor is busy, id:{}, delayTime:{}ms, executionTime:{}, receivedDispatcherTime:{}, realExecutionTime:{}, taskParams:{}",
                            taskParams.getTaskLogId(), delayTime, DateFormatUtils.format(executionTime, Constants.DATE_FORMAT), DateFormatUtils.format(receivedDispatcherTime, Constants.DATE_FORMAT),
                            DateFormatUtils.format(startTime, Constants.DATE_FORMAT), taskParams);
                }
            }
            HandlerResult methodResult = (HandlerResult) targetMethod.invoke(targetObject, taskParams);
            if (methodResult == null) {
                state = TaskLogState.EXECUTION_FAILED;
                failureReason = "result is null, please check the return value of the method: " + methodFullName;
                logger.error("cron job task handler failed, result is null, realExecutionTime:{}, executionTime:{}, params:{}",
                        DateFormatUtils.format(startTime, Constants.DATE_FORMAT), DateFormatUtils.format(executionTime, Constants.DATE_FORMAT), taskParams);
            } else if (!methodResult.isSuccess()) {
                state = TaskLogState.EXECUTION_FAILED;
                failureReason = "cron job task handler failed, code: " + methodResult.getCode() + ", msg: " + methodResult.getMsg();
                logger.error("cron job task handler failed, handler failed, code:{}, msg:{}, realExecutionTime:{}, executionTime:{}, params:{}",
                        methodResult.getCode(), methodResult.getMsg(), DateFormatUtils.format(startTime, Constants.DATE_FORMAT), DateFormatUtils.format(executionTime, Constants.DATE_FORMAT), taskParams);
            }
        } catch (Throwable e) {
            logger.error("cron job task handler exception, handler exception, realExecutionTime:{}, executionTime:{}, task:{}, msg:{}",
                    DateFormatUtils.format(startTime, Constants.DATE_FORMAT), DateFormatUtils.format(executionTime, Constants.DATE_FORMAT),
                    taskParams, e.getMessage(), e);
            state = TaskLogState.EXECUTION_FAILED;
            failureReason = ExceptionUtils.getStackTrace(e);
        }
        long endTime = System.currentTimeMillis();
        taskResult.setTaskLogId(taskParams.getTaskLogId());
        taskResult.setTaskId(taskParams.getTaskId());
        taskResult.setState(state.getValue());
        taskResult.setFailedReason(failureReason);
        taskResult.setRealExecutionTime(startTime);
        taskResult.setElapsedTime((int) (endTime - startTime));
        this.sendResult(taskResult);
    }

    /**
     * 发送结果给调度器
     *
     * @param result 执行结果
     */
    private void sendResult(TaskResult result) {
        this.sendResultExecutor.submit(() -> {
            boolean success = OpenApiService.getInstance().sendResult(result);
            if (!success) {
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
                this.sendResult(result);
            }
        });
    }
}