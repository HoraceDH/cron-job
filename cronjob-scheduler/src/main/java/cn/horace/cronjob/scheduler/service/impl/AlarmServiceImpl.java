package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.commons.constants.TaskLogState;
import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import cn.horace.cronjob.commons.utils.executor.GracefulThreadPoolExecutor;
import cn.horace.cronjob.scheduler.entities.AlarmEntity;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import cn.horace.cronjob.scheduler.mappers.AlarmEntityMapper;
import cn.horace.cronjob.scheduler.service.AlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created in 2025-03-18 21:33.
 *
 * @author Horace
 */
@Service
public class AlarmServiceImpl implements AlarmService {
    private static final Logger logger = LoggerFactory.getLogger(AlarmServiceImpl.class);
    private GracefulThreadPoolExecutor executor = new GracefulThreadPoolExecutor(5, 50, 5, TimeUnit.MINUTES, new SynchronousQueue<>(), new DefaultThreadFactory("task-alarm"), false);
    @Resource
    private AlarmEntityMapper mapper;
    @Resource
    private GuidGenerate guidGenerate;

    /**
     * 是否是需要告警的状态
     *
     * @param state 状态值
     * @return
     */
    @Override
    public boolean isAlarmState(TaskLogState state) {
        return state == TaskLogState.EXECUTION_FAILED || state == TaskLogState.EXECUTION_EXPIRED || state == TaskLogState.EXECUTION_FAILED_DISCARD || state == TaskLogState.EXECUTION_FAILED_NOT_FOUND;
    }

    /**
     * 告警方法
     *
     * @param taskLog 任务日志
     */
    @Override
    public void alarm(TaskLogEntity taskLog) {
        // 保存告警日志
        AlarmEntity entity = this.buildAlarmEntity(taskLog);
        int insert = this.mapper.insert(entity);
        logger.info("task execute failed, save alarm, count:{}, entity:{}", insert, entity);

        // 发送告警信息
        logger.info("task execute failed, send alarm, entity:{}", entity);
    }

    /**
     * 构建告警日志
     *
     * @param taskLog 任务日志
     * @return
     */
    private AlarmEntity buildAlarmEntity(TaskLogEntity taskLog) {
        Date date = new Date();
        AlarmEntity entity = new AlarmEntity();
        entity.setId(this.guidGenerate.genId());
        entity.setTaskLogId(taskLog.getId());
        entity.setAppName(taskLog.getAppName());
        entity.setTaskName(taskLog.getName());
        entity.setExecutorAddress(taskLog.getExecutorAddress());
        entity.setExecutorHostName(taskLog.getExecutorHostName());
        entity.setMethod(taskLog.getMethod());
        entity.setFailedReason(this.getSimpleReason(taskLog));
        entity.setCreateTime(date);
        entity.setModifyTime(date);
        return entity;
    }

    /**
     * 获取简单的失败原因
     *
     * @param taskLog 任务日志
     * @return
     */
    private String getSimpleReason(TaskLogEntity taskLog) {
        StringBuilder sb = new StringBuilder();
        TaskLogState state = TaskLogState.from(taskLog.getState());
        sb.append("state: ").append(state).append("\n");
        sb.append("msg: ").append(state.getMsg()).append("\n");
        sb.append("reason: ").append(taskLog.getFailedReason()).append("\n");
        return sb.toString();
    }

    /**
     * 优雅关闭
     */
    @Override
    public void shutdownGracefully() {
        this.executor.shutdownGracefully();
    }
}