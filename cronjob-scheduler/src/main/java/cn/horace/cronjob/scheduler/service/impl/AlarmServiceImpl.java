package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.commons.constants.TaskLogState;
import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import cn.horace.cronjob.commons.utils.executor.GracefulThreadPoolExecutor;
import cn.horace.cronjob.scheduler.alarm.AlarmHandler;
import cn.horace.cronjob.scheduler.bean.AlarmConfig;
import cn.horace.cronjob.scheduler.bean.Message;
import cn.horace.cronjob.scheduler.bean.params.GetGroupListParams;
import cn.horace.cronjob.scheduler.bean.params.SendAlarmParams;
import cn.horace.cronjob.scheduler.bean.result.AlarmGroup;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.constants.AlarmType;
import cn.horace.cronjob.scheduler.entities.AlarmEntity;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.mappers.AlarmEntityMapper;
import cn.horace.cronjob.scheduler.service.AlarmService;
import cn.horace.cronjob.scheduler.service.TenantService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
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
    private AppConfig appConfig;
    @Resource
    private AlarmEntityMapper mapper;
    @Resource
    private TenantService tenantService;
    @Resource
    private GuidGenerate guidGenerate;
    @Autowired
    private List<AlarmHandler> alarmHandlers;
    private Map<Integer, AlarmHandler> alarmHandlerMap;

    @PostConstruct
    public void init() {
        alarmHandlerMap = new HashMap<>();
        for (AlarmHandler alarmHandler : alarmHandlers) {
            alarmHandlerMap.put(alarmHandler.getAlarmChannel().getValue(), alarmHandler);
        }
    }

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
        TenantEntity tenant = this.tenantService.getTenant(taskLog.getTenantId());
        if (tenant == null) {
            logger.error("task execute failed, send alarm failed, tenant is null, taskLog:{}", taskLog);
            return;
        }

        String alarmConfig = tenant.getAlarmConfig();
        if (alarmConfig == null || alarmConfig.isEmpty()) {
            logger.error("task execute failed, send alarm failed, alarmConfig is empty, tenant:{}", tenant);
            return;
        }

        AlarmConfig config = JSONObject.parseObject(alarmConfig, AlarmConfig.class);
        if (config.getType() == AlarmType.NONE.getValue()) {
            logger.info("task execute failed, not need send alarm, tenant:{}", tenant);
            return;
        }

        SendAlarmParams params = new SendAlarmParams();
        params.setOwner(taskLog.getOwner() + " ");
        params.setType(config.getType());
        params.setChatId(config.getChatId());
        params.setTenantName(tenant.getName());
        params.setAppName(taskLog.getAppName());
        params.setTaskName(taskLog.getName());
        params.setTaskMethod(taskLog.getMethod());
        params.setFailedReason(TaskLogState.from(taskLog.getState()).getMsg());
        params.setTaskLogId(taskLog.getId());
        params.setUrl(this.appConfig.getDomain() + "/schedulers/tasklog/detail?id=" + taskLog.getId());
        Result<Void> result = this.sendAlarm(params);
        if (result.isSuccess()) {
            logger.info("task execute failed, send alarm success, result:{}, params:{}", result, params);
        } else {
            logger.error("task execute failed, send alarm failed, result:{}, params:{}", result, params);
        }
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
        entity.setCreateTime(date);
        entity.setModifyTime(date);
        return entity;
    }

    /**
     * 优雅关闭
     */
    @Override
    public void shutdownGracefully() {
        this.executor.shutdownGracefully();
    }

    /**
     * 获取告警渠道列表，提供给搜索框用
     *
     * @return
     */
    @Override
    public Result<List<SearchItem>> getSearchList() {
        List<SearchItem> searchItems = new ArrayList<>();
        searchItems.add(new SearchItem(AlarmType.NONE.getMsg(), String.valueOf(AlarmType.NONE.getValue())));
        for (AlarmHandler alarmHandler : this.alarmHandlers) {
            if (alarmHandler.isAvailable()) {
                AlarmType channel = alarmHandler.getAlarmChannel();
                searchItems.add(new SearchItem(channel.getMsg(), String.valueOf(channel.getValue())));
            }
        }
        return Result.success(searchItems);
    }

    /**
     * 获取告警渠道的群组列表，提供给搜索框用
     *
     * @param params 参数
     * @return
     */
    @Override
    public Result<List<SearchItem>> getGroupList(GetGroupListParams params) {
        AlarmHandler alarmHandler = this.alarmHandlerMap.get(params.getType());
        if (alarmHandler == null || !alarmHandler.isAvailable()) {
            logger.error("get group list, params is invalid, params:{}", params);
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }

        Result<List<AlarmGroup>> result = alarmHandler.getGroupList();
        if (!result.isSuccess()) {
            return Result.msgCodes(result.getMsgCodes());
        }

        List<AlarmGroup> data = result.getData();
        List<SearchItem> searchItems = new ArrayList<>();
        for (AlarmGroup group : data) {
            String value = group.getId();
            searchItems.add(new SearchItem(group.getName(), value));
        }
        return Result.success(searchItems);
    }

    /**
     * 发送告警
     *
     * @param params 参数
     * @return
     */
    @Override
    public Result<Void> sendAlarm(SendAlarmParams params) {
        AlarmHandler alarmHandler = this.alarmHandlerMap.get(params.getType());
        if (alarmHandler == null || !alarmHandler.isAvailable()) {
            logger.error("send alarm, params is invalid, params:{}", params);
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        Message message = new Message();
        message.setChatId(params.getChatId());
        message.setMsg(alarmHandler.buildMessage(params));
        return alarmHandler.sendMessage(message);
    }
}