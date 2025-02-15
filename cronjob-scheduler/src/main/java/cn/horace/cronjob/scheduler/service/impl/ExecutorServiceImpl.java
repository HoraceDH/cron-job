package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.ExecutorState;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.scheduler.adapter.ExecutorAdapter;
import cn.horace.cronjob.scheduler.bean.params.ExecutorHeartbeatParams;
import cn.horace.cronjob.scheduler.bean.params.ExecutorRegisterParams;
import cn.horace.cronjob.scheduler.bean.params.ExecutorUnregisterParams;
import cn.horace.cronjob.scheduler.bean.params.GetExecutorListParams;
import cn.horace.cronjob.scheduler.bean.result.ExecutorListResult;
import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.entities.AppEntity;
import cn.horace.cronjob.scheduler.entities.ExecutorEntity;
import cn.horace.cronjob.scheduler.entities.ExecutorEntityExample;
import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.mappers.ExecutorEntityMapper;
import cn.horace.cronjob.scheduler.service.*;
import cn.horace.cronjob.scheduler.utils.LikeUtils;
import cn.horace.cronjob.scheduler.utils.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 执行器服务类
 * <p>
 *
 * @author Horace
 */
@Service
public class ExecutorServiceImpl implements ExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorServiceImpl.class);
    @Resource
    private AppConfig appConfig;
    @Resource
    private ExecutorEntityMapper mapper;
    @Resource
    private TenantService tenantService;
    @Resource
    private AppService appService;
    @Resource
    private ExecutorAdapter executorAdapter;
    @Resource
    private UserTenantService userTenantService;
    @Lazy
    @Resource
    private SchedulerInstanceService schedulerInstanceService;

    /**
     * 服务启动时，检测当前在线的执行器是否超时，如果超时则置为下线状态
     */
    @PostConstruct
    public void init() {
        this.startupOfflineExpiredExecutor();
    }

    @Override
    public Result<Void> register(ExecutorRegisterParams params) {
        Result<Void> result = ValidationUtils.validate(params);
        if (!result.isSuccess()) {
            logger.warn("executor register failed, params error, params:{}, result:{}", params, result);
            return result;
        }
        // 获取或者初始化租户
        TenantEntity tenant = this.tenantService.getOrInitTenant(params.getTenant());
        // 获取或者初始化应用
        AppEntity app = this.appService.getOrInitApp(tenant.getId(), params.getAppName(), params.getAppDesc());
        // 插入执行器表
        ExecutorEntity entity = this.getExecutor(params.getAddress());
        if (entity == null) {
            entity = this.executorAdapter.convert(params, tenant, app);
            int count = this.mapper.insert(entity);
            if (count != 1) {
                return Result.msgCodes(MsgCodes.ERROR_SYSTEM);
            }
        } else {
            entity = this.executorAdapter.convert(params, tenant, app);
            int count = this.mapper.updateByPrimaryKey(entity);
            if (count != 1) {
                return Result.msgCodes(MsgCodes.ERROR_SYSTEM);
            }
        }
        logger.info("executor register success, params:{}", params);
        return result;
    }

    @Override
    public Result<Void> heartbeat(ExecutorHeartbeatParams params) {
        Result<Void> result = ValidationUtils.validate(params);
        if (!result.isSuccess()) {
            logger.warn("executor heartbeat failed, params error, params:{}, result:{}", params, result);
            return result;
        }
        ExecutorEntity entity = this.getExecutor(params.getAddress());
        if (entity == null) {
            logger.error("executor heartbeat failed, executor not found, params:{}", params);
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }

        // 更新状态和修改时间
        entity.setState(ExecutorState.ONLINE.getValue());
        entity.setModifyTime(new Date());
        int count = this.mapper.updateByPrimaryKey(entity);
        if (count != 1) {
            return Result.msgCodes(MsgCodes.ERROR_SYSTEM);
        }
        logger.debug("executor heartbeat success, params:{}", params);
        return result;
    }

    /**
     * 获取指定应用下的在线执行器数量
     *
     * @param appId 应用ID
     * @return
     */
    @Override
    public int getOnlineCount(long appId) {
        ExecutorEntityExample example = new ExecutorEntityExample();
        example.or().andAppIdEqualTo(appId).andStateEqualTo(ExecutorState.ONLINE.getValue());
        return (int) this.mapper.countByExample(example);
    }

    /**
     * 获取执行器列表
     *
     * @param userId 当前用户ID
     * @param params 查询参数
     * @return
     */
    @Override
    public Result<ExecutorListResult> getExecutorList(long userId, GetExecutorListParams params) {
        Result<ExecutorListResult> result = Result.success();
        ExecutorEntityExample example = new ExecutorEntityExample();
        ExecutorEntityExample.Criteria criteria = example.or();

        // 处理用户的租户权限
        List<Long> tenantIds = this.userTenantService.getTenantIds(userId);
        if (tenantIds == null || tenantIds.isEmpty()) {
            return Result.success();
        }
        if (StringUtils.isBlank(params.getTenantId()) || Long.parseLong(params.getTenantId()) <= 0) {
            criteria.andTenantIdIn(tenantIds);
        } else {
            // 如果指定了租户ID，那么需要判断该租户ID是否属于当前用户
            long tenantId = Long.parseLong(params.getTenantId());
            if (tenantIds.contains(tenantId)) {
                criteria.andTenantIdEqualTo(tenantId);
            } else {
                // 租户参数不属于当前用户
                return Result.msgCodes(MsgCodes.ERROR_PERMISSION);
            }
        }

        if (StringUtils.isNotBlank(params.getAddress())) {
            criteria.andAddressLike(LikeUtils.toLikeString(params.getAddress()));
        }
        if (StringUtils.isNotBlank(params.getAppName())) {
            criteria.andAppNameLike(params.getAppName());
        }
        if (StringUtils.isNotBlank(params.getAppDesc())) {
            criteria.andAppDescLike(LikeUtils.toLikeString(params.getAppDesc()));
        }
        if (StringUtils.isNotBlank(params.getTag())) {
            criteria.andTagLike(LikeUtils.toLikeString(params.getTag()));
        }
        if (params.getState() > 0) {
            criteria.andStateEqualTo(params.getState());
        }
        ExecutorListResult executorListResult = new ExecutorListResult();
        executorListResult.setTotal(this.mapper.countByExample(example));
        int offset = (params.getCurrent() - 1) * params.getPageSize();
        int limit = params.getPageSize();
        example.setOrderByClause("`create_time` desc limit " + offset + ", " + limit);
        List<ExecutorEntity> entityList = this.mapper.selectByExample(example);
        executorListResult.setCurrent(params.getCurrent());
        executorListResult.setPageSize(params.getPageSize());
        executorListResult.setData(this.executorAdapter.convert(entityList));
        result.setData(executorListResult);
        return result;
    }

    /**
     * 获取执行器实体
     *
     * @param address 执行器地址
     * @return
     */
    @Override
    public ExecutorEntity getExecutor(String address) {
        return this.mapper.selectByPrimaryKey(address);
    }

    /**
     * 删除过期的下线的执行器
     *
     * @param maxRetainDays 最大保留天数
     */
    @Override
    public void deleteExpiredExecutors(int maxRetainDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -maxRetainDays);
        Date date = calendar.getTime();
        ExecutorEntityExample example = new ExecutorEntityExample();
        example.or().andModifyTimeLessThan(date).andStateEqualTo(ExecutorState.OFFLINE.getValue());
        int count = this.mapper.deleteByExample(example);
        if (count > 0) {
            logger.info("delete expired executor, count:{}", count);
        }
    }

    @Override
    public List<ExecutorEntity> getExecutorList(long appId, ExecutorState state) {
        ExecutorEntityExample example = new ExecutorEntityExample();
        example.or().andAppIdEqualTo(appId).andStateEqualTo(state.getValue());
        return this.mapper.selectByExample(example);
    }

    @Override
    public void offlineExpiredExecutor() {
        long currentTime = System.currentTimeMillis();
        // 往前偏移5秒，避免查询范围太大
        long skewTime = currentTime - 5000;
        Date endDate = new Date(skewTime);
        Date startDate = new Date(skewTime - this.appConfig.getExecutorTimeout());
        ExecutorEntityExample example = new ExecutorEntityExample();
        example.or()
                .andModifyTimeGreaterThanOrEqualTo(startDate)
                .andModifyTimeLessThanOrEqualTo(endDate)
                .andStateEqualTo(ExecutorState.ONLINE.getValue());
        List<ExecutorEntity> executorEntities = this.mapper.selectByExample(example);
        this.offlineExpiredExecutor0(executorEntities);
    }

    /**
     * 检测指定的执行器集合中的执行器是否超过了过期时间，如果超过过期时间则置为下线状态
     *
     * @param executorEntities 执行器集合
     */
    private void offlineExpiredExecutor0(List<ExecutorEntity> executorEntities) {
        long currentTime = System.currentTimeMillis();
        for (ExecutorEntity executorEntity : executorEntities) {
            long time = executorEntity.getModifyTime().getTime();
            long diffTime = currentTime - time;
            if (diffTime >= this.appConfig.getExecutorTimeout()) {
                executorEntity.setState(ExecutorState.OFFLINE.getValue());
                boolean success = this.mapper.updateByPrimaryKeySelective(executorEntity) > 0;
                logger.info("offline expired executor, success:{}, executor:{}", success, executorEntity);
            }
        }
    }

    @Override
    public boolean updateState(String address, ExecutorState state) {
        ExecutorEntity entity = new ExecutorEntity();
        entity.setAddress(address);
        entity.setState(state.getValue());
        entity.setModifyTime(new Date());
        return this.mapper.updateByPrimaryKeySelective(entity) > 0;
    }

    @Override
    public int getExecutorCount(ExecutorState state) {
        ExecutorEntityExample example = new ExecutorEntityExample();
        example.or().andStateEqualTo(state.getValue());
        return (int) this.mapper.countByExample(example);
    }

    @Override
    public Result<Void> unregister(ExecutorUnregisterParams params) {
        Result<Void> result = Result.success();
        boolean success = this.updateState(params.getAddress(), ExecutorState.OFFLINE);
        if (!success) {
            result.setMsgCodes(MsgCodes.ERROR_SYSTEM);
        }
        logger.info("executor unregister, result:{}, address:{}", success, params.getAddress());
        return result;
    }

    /**
     * 服务启动时，检测当前在线的执行器是否超时，如果超时则置为下线状态
     */
    private void startupOfflineExpiredExecutor() {
        ExecutorEntityExample example = new ExecutorEntityExample();
        example.or()
                .andStateEqualTo(ExecutorState.ONLINE.getValue());
        List<ExecutorEntity> executorEntities = this.mapper.selectByExample(example);
        this.offlineExpiredExecutor0(executorEntities);
    }

}