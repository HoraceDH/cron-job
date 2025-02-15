package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.TaskParams;
import cn.horace.cronjob.commons.constants.*;
import cn.horace.cronjob.commons.httpclient.HttpClient;
import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import cn.horace.cronjob.commons.utils.ExceptionUtils;
import cn.horace.cronjob.commons.utils.executor.GracefulThreadPoolExecutor;
import cn.horace.cronjob.scheduler.bean.ComparableTaskLog;
import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.constants.TaskLogExeType;
import cn.horace.cronjob.scheduler.entities.ExecutorEntity;
import cn.horace.cronjob.scheduler.entities.SchedulerInstanceEntity;
import cn.horace.cronjob.scheduler.entities.TaskEntity;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import cn.horace.cronjob.scheduler.service.*;
import cn.horace.cronjob.scheduler.strategy.RouterStrategyHandler;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 调度服务类
 * <p>
 *
 * @author Horace
 */
@Service
public class SchedulerServiceImpl implements SchedulerService, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
    private ApplicationContext applicationContext;
    private final ConcurrentHashMap<RouterStrategy, RouterStrategyHandler> routerStrategyHandlers = new ConcurrentHashMap<>();
    private final GracefulThreadPoolExecutor executor = new GracefulThreadPoolExecutor(10, 300, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1000), new DefaultThreadFactory("dispatcher-task"), false);
    private final PriorityBlockingQueue<ComparableTaskLog> taskQueue = new PriorityBlockingQueue<>();
    private volatile boolean firstRun = true;
    private volatile boolean firstDispatcher = true;
    private volatile boolean updateTaskQueueRunning = true;
    private volatile boolean dispatcherTaskRunning = true;
    @Resource
    private AppConfig appConfig;
    @Resource
    private TaskLogService taskLogService;
    @Resource
    private TaskService taskService;
    @Resource
    private ExecutorService executorService;
    @Resource
    private SchedulerInstanceService schedulerInstanceService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.initRouterStrategyHandler();
    }

    /**
     * 初始化路由处理器
     */
    private void initRouterStrategyHandler() {
        Map<String, RouterStrategyHandler> handlers = this.applicationContext.getBeansOfType(RouterStrategyHandler.class);
        for (Map.Entry<String, RouterStrategyHandler> entry : handlers.entrySet()) {
            RouterStrategyHandler handler = entry.getValue();
            this.routerStrategyHandlers.put(handler.getStrategy(), handler);
            logger.info("find router strategy handler, name:{}, handler:{}", entry.getKey(), handler);
        }
    }

    /**
     * 开始更新任务队列，从数据库记录中获取任务日志到内存队列中
     */
    @Override
    public void startUpdateTaskQueue() {
        while (this.updateTaskQueueRunning) {
            try {
                // 如果队列内的任务还没调度完，则本次不入队
                if (this.taskQueue.size() >= this.appConfig.getSchedulersTaskQueueCount()) {
                    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));
                    continue;
                }

                // 预先获取5秒内将要被执行的任务
                int count = this.appConfig.getSchedulersTaskQueueCount() - this.taskQueue.size();
                long currentTimeMillis = System.currentTimeMillis();
                Date startDate = new Date(currentTimeMillis - TimeUnit.MINUTES.toMillis(5));
                Date endDate = new Date(currentTimeMillis + 5 * 1000);

                // 首次运行，先取后几秒执行的任务，避免调度延迟
                if (this.firstRun) {
                    startDate = new Date(currentTimeMillis + TimeUnit.SECONDS.toMillis(15));
                    endDate = new Date(currentTimeMillis + TimeUnit.SECONDS.toMillis(20));
                    this.firstRun = false;
                }

                List<TaskLogEntity> taskList = this.taskLogService.getPendingTaskList(startDate, endDate, count);
                if (taskList == null || taskList.isEmpty()) {
                    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
                    continue;
                }
                this.addAllTaskToQueue(taskList);
            } catch (Exception e) {
                logger.error("update task queue error, msg:{}", e.getMessage(), e);
            }
        }
        logger.info("stop update task queue.");
    }

    @Override
    public void startDispatcherTask() {
        while (this.dispatcherTaskRunning || !this.taskQueue.isEmpty()) {
            ComparableTaskLog comparableTaskLog = null;
            try {
                comparableTaskLog = this.taskQueue.poll(10, TimeUnit.SECONDS);
                if (comparableTaskLog == null) {
                    continue;
                }
                TaskLogEntity taskLog = comparableTaskLog.getEntity();
                // 如果任务已经被停止，则取消调度
                TaskEntity taskEntity = this.taskService.getTaskDetail(taskLog.getTaskId());
                if (taskEntity.getRunState() == TaskRunState.STOP.getValue() && taskLog.getExeType() != TaskLogExeType.MANAGER_NOW.getValue()) {
                    this.taskLogService.updateTaskLogState(taskLog.getId(), TaskLogState.EXECUTION_CANCEL, taskLog.getVersion());
                    logger.info("dispatcher task cancel, the task is stopped, task:{}, taskLog:{}", taskEntity, taskLog);
                    continue;
                }

                long nowTime = System.currentTimeMillis();
                long interval = nowTime - taskLog.getExecutionTime().getTime();

                // 任务已经过期，并且过期策略是丢弃
                if (interval >= taskLog.getExpiredTime() && ExpiredStrategy.from(taskLog.getExpiredStrategy()) == ExpiredStrategy.EXPIRED_DISCARD) {
                    logger.warn("dispatching task, discard expired task, interval:{}, expireTime:{}, taskLog:{}", interval, taskLog.getExpiredTime(), taskLog);
                    this.taskLogService.updateTaskLogState(taskLog.getTaskId(), TaskLogState.EXECUTION_EXPIRED, taskLog.getVersion(), "", "任务已过期，且过期策略为丢弃", null);
                    continue;
                }

                // 如果是首次调度，则立马调度给执行器，提前的时间由加载的队列来决定，避免调度器首次启动延迟
                if (this.firstDispatcher) {
                    this.firstDispatcher = false;
                    this.executor.submit(() -> this.dispatcher(taskLog));
                    continue;
                }

                // 满足提前调度时间再开始调度，或者已经超过调度时间则立即调度
                if (Math.abs(interval) <= this.appConfig.getSchedulersBeforeInterval() || nowTime >= taskLog.getExecutionTime().getTime()) {
                    this.executor.submit(() -> this.dispatcher(taskLog));
                } else {
                    // 否则再放回去队列中
                    this.taskQueue.add(comparableTaskLog);
                    // 还没到任务执行的时间，适当休眠一段时间
                    long sleepTime = taskLog.getExecutionTime().getTime() - nowTime - this.appConfig.getSchedulersBeforeInterval() - 50;
                    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(sleepTime));
                }
            } catch (Exception e) {
                logger.error("dispatching task error, entity:{}, msg:{}", comparableTaskLog, e.getMessage(), e);
            }
        }
        this.executor.shutdownGracefully();
        logger.info("stop dispatcher task.");
    }

    @Override
    public void handlerTimeoutTaskLog() {
        long time = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(20);
        Date startDate = new Date(time - TimeUnit.SECONDS.toMillis(30));
        Date endDate = new Date(time);
        List<TaskLogEntity> entities = this.taskLogService.getMayBeTimeoutTaskLogList(startDate, endDate);
        for (TaskLogEntity entity : entities) {
            long executionTime = entity.getExecutionTime().getTime();
            Integer timeout = entity.getTimeout();
            boolean isTimeout = System.currentTimeMillis() >= (executionTime + timeout);
            if (isTimeout) {
                this.handlerFailureTaskLog(entity, FailureType.FAILURE_NO_COMPLETE, "执行器超时未反馈结果");
                logger.warn("handler timeout task log, the task is timeout, taskLog:{}", entity);
            }
        }
    }

    /**
     * 停止从数据库获取任务日志
     */
    @Override
    public void stopUpdateTaskQueue() {
        this.updateTaskQueueRunning = false;
    }

    /**
     * 停止派发任务，队列中的任务依然等待调度完成
     */
    @Override
    public void stopDispatcherTask() {
        this.dispatcherTaskRunning = false;
    }

    /**
     * 分发任务
     *
     * @param taskLog 任务日志
     */
    private void dispatcher(TaskLogEntity taskLog) {
        // 获取在线的执行器
        List<ExecutorEntity> executors = this.executorService.getExecutorList(taskLog.getAppId(), ExecutorState.ONLINE);
        // 没有在线的执行器
        if (executors == null || executors.isEmpty()) {
            this.handlerFailureTaskLog(taskLog, FailureType.FAILURE_NOT_FOUND_EXECUTOR, null);
            logger.warn("dispatcher task failed, not found online executor, taskLog:{}", taskLog);
            return;
        }

        RouterStrategy routerStrategy = RouterStrategy.from(taskLog.getRouterStrategy());
        if (routerStrategy == null) {
            routerStrategy = RouterStrategy.RANDOM;
        }
        RouterStrategyHandler strategyHandler = this.routerStrategyHandlers.get(routerStrategy);
        executors = strategyHandler.route(executors);
        if (executors == null || executors.isEmpty()) {
            this.handlerFailureTaskLog(taskLog, FailureType.FAILURE_ROUTE_ERROR, null);
            logger.error("dispatcher task failed, route error, not found any executor, taskLog:{}", taskLog);
            return;
        }

        // 单执行器执行的情况
        int size = executors.size();
        if (size == 1) {
            this.dispatcherToExecutor(taskLog, executors.get(0));
            return;
        }

        // 如果是分片策略，则根据父任务生成子任务派发给执行器
        if (routerStrategy == RouterStrategy.SHARDING) {
            dispatcherSharding(taskLog, executors);
        }
    }

    /**
     * 分发分片任务
     *
     * @param taskLog   任务日志
     * @param executors 执行器列表
     */
    private void dispatcherSharding(TaskLogEntity taskLog, List<ExecutorEntity> executors) {
        // 如果是首次执行，非重试的场景下
        Long parentId = taskLog.getParentId();
        if ((parentId == null || parentId <= 0) && taskLog.getRetryCount() <= 0) {
            int size = executors.size();
            for (int i = 0; i < size; i++) {
                TaskLogEntity temp;
                if (i == 0) {
                    temp = taskLog;
                    temp.setTotal(size);
                    this.taskLogService.updateTotal(temp.getId(), size);
                } else {
                    temp = this.taskLogService.generateChildTaskLog(taskLog, i + 1, size);
                }
                ExecutorEntity executorEntity = executors.get(i);
                this.dispatcherToExecutor(temp, executorEntity);
            }
            return;
        }

        // 重试的情况下
        RouterStrategyHandler strategyHandler = this.routerStrategyHandlers.get(RouterStrategy.RANDOM);
        executors = strategyHandler.route(executors);
        this.dispatcherToExecutor(taskLog, executors.get(0));
    }

    /**
     * 派发任务给指定的执行器
     *
     * @param taskLog  任务日志对象
     * @param executor 执行器
     */
    private void dispatcherToExecutor(TaskLogEntity taskLog, ExecutorEntity executor) {
        String address = this.schedulerInstanceService.getCurrentAddress();
        boolean success = this.taskLogService.updateTaskLogState(taskLog.getId(), TaskLogState.EXECUTION, taskLog.getVersion(), address, null, executor.getAddress());
        if (!success) {
            this.handlerFailureTaskLog(taskLog, FailureType.FAILURE_UPDATE_STATE, null);
            logger.error("dispatcher task failed, update state failed, taskLog:{}", taskLog);
            return;
        }
        taskLog.setVersion(taskLog.getVersion() + 1);
        String params = JSONObject.toJSONString(this.builderExecutorParams(taskLog));
        String url = this.builderExecutorURL(executor);
        try {
            MsgObject result = HttpClient.getInstance().postMsgObject(url, null, null, params);
            if (!result.isSuccess()) {
                if (result.getCode() == MsgCodes.ERROR_EXECUTE_SHUTDOWN.getCode()) {
                    boolean offlineSuccess = this.executorService.updateState(executor.getAddress(), ExecutorState.OFFLINE);
                    this.handlerFailureTaskLog(taskLog, FailureType.FAILURE_EXECUTOR_SHUTDOWN, result.getMsg());
                    logger.error("dispatcher task failed, executor is shutdown, updateStatus:{}, result:{}, taskLog:{}", offlineSuccess, result, taskLog);
                } else {
                    logger.error("dispatcher task failed, executor handler failed, result:{}, taskLog:{}", result, taskLog);
                    this.handlerFailureTaskLog(taskLog, FailureType.FAILURE_EXECUTOR, result.getMsg());
                }
                return;
            }
            logger.info("dispatcher task success, executionTime:{}, executorAddress:{}, taskLogId:{}, appName:{}",
                    DateFormatUtils.format(taskLog.getExecutionTime(), Constants.DATE_FORMAT),
                    executor.getAddress(), taskLog.getId(), taskLog.getAppName());
        } catch (Throwable e) {
            // 如果是连接拒绝，则可能是执行器已经下线，则设置为下线状态
            if (e.getMessage().contains("Connection refused") || e.getMessage().contains("failed to respond")) {
                boolean offlineSuccess = this.executorService.updateState(executor.getAddress(), ExecutorState.OFFLINE);
                logger.warn("dispatcher task failed, the executor may be offline, set state is offline, success:{}, executorAddress:{}, msg:{}, executor:{}", offlineSuccess, executor.getAddress(), e.getMessage(), executor);
            } else {
                logger.error("dispatcher task exception, msg:{}, taskLog:{}", e.getMessage(), taskLog, e);
            }

            String failureReason = ExceptionUtils.getStackTrace(e);
            this.handlerFailureTaskLog(taskLog, FailureType.FAILURE_EXCEPTION, failureReason);
        }
    }

    /**
     * 构建执行器请求地址
     *
     * @param executor 执行器
     * @return
     */
    private String builderExecutorURL(ExecutorEntity executor) {
        return "http://" + executor.getAddress() + "/dispatch";
    }

    /**
     * 构建执行参数
     *
     * @param taskLog 任务对象
     * @return
     */
    private TaskParams builderExecutorParams(TaskLogEntity taskLog) {
        TaskParams taskParams = new TaskParams();
        taskParams.setPage(taskLog.getPage());
        taskParams.setTotal(taskLog.getTotal());
        taskParams.setTaskLogId(taskLog.getId());
        taskParams.setTaskId(taskLog.getTaskId());
        taskParams.setMethod(taskLog.getMethod());
        taskParams.setExeType(taskLog.getExeType());
        taskParams.setCron(taskLog.getCron());
        taskParams.setTag(taskLog.getTag());
        taskParams.setParams(taskLog.getTaskParams());
        taskParams.setExecutionTime(taskLog.getExecutionTime().getTime());
        return taskParams;
    }

    /**
     * 处理失败的任务日志
     *
     * @param taskLog       任务日志记录
     * @param failureType   失败类型
     * @param failureReason 失败原因
     */
    private void handlerFailureTaskLog(TaskLogEntity taskLog, FailureType failureType, String failureReason) {
        failureReason = failureReason == null ? "" : failureReason;
        if (StringUtils.isNotBlank(failureReason)) {
            failureReason = "\n\n" + failureReason;
        }
        failureReason = failureType.getMsg() + failureReason;
        Integer failureStrategy = taskLog.getFailureStrategy();
        FailureStrategy strategy = FailureStrategy.from(failureStrategy);
        if (strategy == null) {
            boolean success = this.taskLogService.updateTaskLogState(taskLog.getId(), TaskLogState.EXECUTION_FAILED, taskLog.getVersion(), null, failureReason, null);
            logger.error("handler failure task log, failureStrategy not found, updateStateSuccess:{}, failureStrategy:{}, failureType:{}, taskLog:{}, failureReason:{}",
                    success, failureStrategy, failureType, taskLog, failureReason);
            return;
        }

        if (strategy == FailureStrategy.FAILURE_DISCARD) {
            boolean success = this.taskLogService.updateTaskLogState(taskLog.getId(), TaskLogState.EXECUTION_FAILED_DISCARD, taskLog.getVersion(), null, failureReason, null);
            logger.info("handler failure task log, discard the task log, updateStateSuccess:{}, strategy:{}, failureType:{}, taskLog:{}, failureReason:{}",
                    success, strategy, failureType, taskLog, failureReason);
            return;
        }

        // 如果是重试
        if (strategy == FailureStrategy.FAILURE_RETRY) {
            // 如果已经到达最大重试次数
            if (taskLog.getRetryCount() >= taskLog.getMaxRetryCount()) {
                boolean success = this.taskLogService.updateTaskLogState(taskLog.getId(), TaskLogState.EXECUTION_FAILED, taskLog.getVersion(), null, failureReason, null);
                logger.error("handler failure task log, discard the task log, updateStateSuccess:{}, retryCount:{}, maxRetryCount:{}, taskLog:{}, failureReason:{}",
                        success, taskLog.getRetryCount(), taskLog.getMaxRetryCount(), taskLog, failureReason);
                return;
            }

            long time = taskLog.getExecutionTime().getTime() + taskLog.getFailureRetryInterval();
            boolean success = this.taskLogService.updateRetryTaskLog(taskLog.getId(), new Date(time), taskLog.getRetryCount() + 1, failureReason, taskLog.getVersion());
            logger.info("handler failure task log, retrying the task log, updateStateSuccess:{}, strategy:{}, failureType:{}, taskLog:{}, failureReason:{}",
                    success, strategy, failureType, taskLog, failureReason);
        }
    }

    /**
     * 将任务入队
     *
     * @param cronTaskList 任务列表
     */
    private void addAllTaskToQueue(List<TaskLogEntity> cronTaskList) {
        if (cronTaskList == null || cronTaskList.isEmpty()) {
            return;
        }
        SchedulerInstanceEntity schedulers = this.schedulerInstanceService.getSchedulerInstance(this.appConfig.getServerId());
        for (TaskLogEntity entity : cronTaskList) {
            // 更新状态成功则把任务放到内存队列中
            boolean success = this.taskLogService.updateTaskLogState(entity.getId(), TaskLogState.QUEUEING, entity.getVersion(), schedulers.getAddress(), null, null);
            if (!success) {
                logger.debug("add task log to queue failed, lock failure, taskLog:{}", entity);
                continue;
            }
            entity.setSchedulerAddress(schedulers.getAddress());
            entity.setState(TaskLogState.QUEUEING.getValue());
            entity.setVersion(entity.getVersion() + 1);
            entity.setModifyTime(new Date());
            this.taskQueue.add(new ComparableTaskLog(entity));
            logger.debug("add task log to queue, totalSize:{}, task:{}", this.taskQueue.size(), entity);
        }
    }
}