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
import cn.horace.cronjob.scheduler.bean.params.UpdateAlarmConfigParams;
import cn.horace.cronjob.scheduler.bean.result.AlarmGroup;
import cn.horace.cronjob.scheduler.bean.result.SearchItem;
import cn.horace.cronjob.scheduler.constants.AlarmChannel;
import cn.horace.cronjob.scheduler.entities.AlarmEntity;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.mappers.AlarmEntityMapper;
import cn.horace.cronjob.scheduler.service.AlarmService;
import cn.horace.cronjob.scheduler.service.TenantService;
import com.alibaba.fastjson2.JSONObject;
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
    private static final String SEPARATE = "___SEP___";
    private GracefulThreadPoolExecutor executor = new GracefulThreadPoolExecutor(5, 50, 5, TimeUnit.MINUTES, new SynchronousQueue<>(), new DefaultThreadFactory("task-alarm"), false);
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

    /**
     * 获取告警渠道列表，提供给搜索框用
     *
     * @return
     */
    @Override
    public Result<List<SearchItem>> getSearchList() {
        List<SearchItem> searchItems = new ArrayList<>();
        for (AlarmHandler alarmHandler : this.alarmHandlers) {
            if (alarmHandler.isAvailable()) {
                AlarmChannel channel = alarmHandler.getAlarmChannel();
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
            String value = group.getName() + SEPARATE + group.getId();
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
        String chatId = params.getNameAndChatId().split(SEPARATE)[1].trim();
        AlarmHandler alarmHandler = this.alarmHandlerMap.get(params.getType());
        if (alarmHandler == null || !alarmHandler.isAvailable()) {
            logger.error("send alarm, params is invalid, params:{}", params);
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        Message message = new Message();
        message.setChatId(chatId);
        message.setMsg("{\"text\": \"test message\"}");
        return alarmHandler.sendMessage(message);
    }

    /**
     * 更新告警配置
     *
     * @param params 参数
     * @return
     */
    @Override
    public Result<Void> updateAlarmConfig(UpdateAlarmConfigParams params) {
        try {
            String[] split = params.getNameAndChatId().split(SEPARATE);
            if (split.length != 2) {
                logger.error("update alarm config, params is invalid, params:{}", params);
                return Result.msgCodes(MsgCodes.ERROR_PARAMS);
            }

            String name = split[0];
            String chatId = split[1];
            TenantEntity tenant = this.tenantService.getTenant(Long.parseLong(params.getTenantId()));
            if (tenant == null) {
                logger.error("update alarm config, tenant is not exist, tenantId:{}", params.getTenantId());
                return Result.msgCodes(MsgCodes.ERROR_PARAMS);
            }
            AlarmConfig alarmConfig = new AlarmConfig(params.getType(), chatId, name);
            tenant.setAlarmConfig(JSONObject.toJSONString(alarmConfig));
            boolean success = this.tenantService.updateTenant(tenant);
            if (success) {
                return Result.success();
            }
        } catch (Exception e) {
            logger.error("update alarm config exception, params:{}, msg:{}", params, e.getMessage(), e);
        }
        return Result.msgCodes(MsgCodes.ERROR_SYSTEM);
    }
}