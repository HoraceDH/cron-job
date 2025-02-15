package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.commons.constants.ExpiredStrategy;
import cn.horace.cronjob.commons.cron.CronExpression;
import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import cn.horace.cronjob.commons.utils.executor.GracefulThreadPoolExecutor;
import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.constants.Locks;
import cn.horace.cronjob.scheduler.constants.TaskLogExeType;
import cn.horace.cronjob.scheduler.entities.TaskEntity;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import cn.horace.cronjob.scheduler.service.LocksService;
import cn.horace.cronjob.scheduler.service.TaskLogGenerateService;
import cn.horace.cronjob.scheduler.service.TaskLogService;
import cn.horace.cronjob.scheduler.service.TaskService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 生成任务日志
 * <p>
 *
 * @author Horace
 */
@Service
public class TaskLogGenerateServiceImpl implements TaskLogGenerateService {
    private static final Logger logger = LoggerFactory.getLogger(TaskLogGenerateServiceImpl.class);
    private GracefulThreadPoolExecutor executor = new GracefulThreadPoolExecutor(5, 50, 5, TimeUnit.MINUTES, new SynchronousQueue<>(), new DefaultThreadFactory("generate-task-log"), false);
    @Resource
    private AppConfig appConfig;
    @Resource
    private LocksService locksService;
    @Resource
    private TaskService taskService;
    @Resource
    private TaskLogService taskLogService;
    @Resource
    private CronExpression cronExpression;
    private volatile boolean generateTaskLog = true;

    /**
     * 生成任务日志
     */
    @Override
    public void generate() {
        if (!this.generateTaskLog) {
            logger.info("task log generate is stopped");
            return;
        }

        String lockId = Locks.LOCK_GENERATE_TASK.name();
        // 获取分布式锁
        String lockOwner = this.locksService.getLockOwner();
        boolean lock = false;
        try {
            lock = this.locksService.lock(lockId, lockOwner, 120, "生成任务日志");
            // 获取锁不成功
            if (!lock) {
                return;
            }

            List<Future<?>> futures = new ArrayList<>();
            long lastTaskId = 0;
            while (true) {
                List<TaskEntity> tasks = this.taskService.getTaskList(lastTaskId, 20);
                if (tasks == null || tasks.isEmpty()) {
                    break;
                }
                lastTaskId = tasks.get(tasks.size() - 1).getId();
                for (TaskEntity task : tasks) {
                    Future<?> future = this.executor.submit(() -> this.generate(task));
                    futures.add(future);
                }
            }

            // 等待任务完成
            for (Future<?> future : futures) {
                future.get();
            }
        } catch (Exception e) {
            logger.error("generate cron task log error, serverId:{}, msg:{}", this.appConfig.getServerId(), e.getMessage(), e);
        } finally {
            if (lock) {
                this.locksService.releaseLock(lockId, lockOwner);
            }
        }
    }

    /**
     * 停止生成任务日志
     */
    @Override
    public void stopGenerateTaskLog() {
        this.generateTaskLog = false;
        this.executor.shutdownGracefully();
        logger.info("stop generate task log");
    }

    /**
     * 生成对应任务的任务日志
     *
     * @param task 任务
     */
    private void generate(TaskEntity task) {
        TaskLogEntity lastTaskLog = this.taskLogService.getLastTaskLog(task.getId(), false, null);
        long lastExecutionTime;

        // 1. lastTaskLog 为空，则表示没有历史日志
        if (lastTaskLog == null) {
            lastExecutionTime = System.currentTimeMillis();
        } else {
            // 3. lastTaskLog 不为空，则以这条日志为参考，生成下一次执行时间的日志
            lastExecutionTime = lastTaskLog.getExecutionTime().getTime();
            boolean isExistsMulti = this.isExistsMulti(task.getCron(), lastExecutionTime);

            // 2. 如果上一条日志距离现在的时间存在多个执行任务，并且任务是过期执行的类型，则生成一条立即执行的任务
            if (isExistsMulti && task.getExpiredStrategy() == ExpiredStrategy.EXPIRED_EXECUTE.getValue()) {
                lastExecutionTime = System.currentTimeMillis();
                this.taskLogService.addNowExecuteTaskLog(task, lastExecutionTime, TaskLogExeType.EXPIRED);
                logger.info("generate expired task log success, executionTime:{}, task:{}", DateFormatUtils.format(new Date(lastExecutionTime), Constants.DATE_FORMAT), task);
            }
        }

        // 基于上面计算的 lastExecutionTime 继续生成后续的任务日志
        while (this.isNeedGenerate(task.getId(), lastExecutionTime)) {
            lastExecutionTime = this.taskLogService.addTaskLog(task, lastExecutionTime);
            logger.debug("generate task log success, executionTime:{}, task:{}", DateFormatUtils.format(new Date(lastExecutionTime), "yyyy-MM-dd HH:mm:ss"), task);
        }
    }

    /**
     * 针对基准时间，判断距离当前时间是否存在多个执行时间
     *
     * @param cron              表达式
     * @param lastExecutionTime 基准时间
     * @return
     */
    private boolean isExistsMulti(String cron, long lastExecutionTime) {
        Date nextExecutionTime1 = this.cronExpression.getNextExecutionTime(cron, lastExecutionTime);
        Date nextExecutionTime2 = this.cronExpression.getNextExecutionTime(cron, nextExecutionTime1.getTime());

        // 连续两个执行时间都小于当前时间，则判定中间断层，并且存在多个执行时间
        if (nextExecutionTime2.getTime() < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    /**
     * 根据最后执行时间判断是否需要继续生成任务日志
     *
     * @param taskId            任务ID
     * @param lastExecutionTime 最后执行时间
     * @return
     */
    private boolean isNeedGenerate(long taskId, long lastExecutionTime) {
        // 如果最后一条的执行时间与当前时间的差值超过阈值，则不再生成
        long diff = lastExecutionTime - System.currentTimeMillis();
        // 转成分钟
        int diffMinutes = (int) (diff / 1000 / 60);
        if (diffMinutes >= this.appConfig.getSchedulersTaskPreGenerationMaxTimeMinutes()) {
            return false;
        }
        return true;
    }

}