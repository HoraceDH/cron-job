package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.SchedulerInstanceState;
import cn.horace.cronjob.commons.utils.HostUtils;
import cn.horace.cronjob.commons.utils.IPUtils;
import cn.horace.cronjob.scheduler.adapter.SchedulersAdapter;
import cn.horace.cronjob.scheduler.bean.params.GetSchedulerInstanceListParams;
import cn.horace.cronjob.scheduler.bean.result.SchedulerInstanceListResult;
import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.entities.SchedulerInstanceEntity;
import cn.horace.cronjob.scheduler.entities.SchedulerInstanceEntityExample;
import cn.horace.cronjob.scheduler.mappers.SchedulerInstanceEntityMapper;
import cn.horace.cronjob.scheduler.service.SchedulerInstanceService;
import cn.horace.cronjob.scheduler.utils.LikeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 调度器服务类
 * <p>
 *
 * @author Horace
 */
@Service
public class SchedulerInstanceServiceImpl implements SchedulerInstanceService {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerInstanceServiceImpl.class);
    @Resource
    private AppConfig appConfig;
    @Resource
    private SchedulersAdapter schedulersAdapter;
    @Resource
    private SchedulerInstanceEntityMapper mapper;
    private volatile boolean running = true;


    @Override
    public void heartbeat() {
        if (running) {
            this.updateState(this.appConfig.getServerId(), this.getCurrentAddress(), SchedulerInstanceState.ONLINE);
            logger.debug("scheduler instance heartbeat, serverId:{}, address:{}", this.appConfig.getServerId(), this.getCurrentAddress());
        }
    }

    /**
     * 注册调度器实例到数据库中
     *
     * @param serverId 服务实例ID
     * @param address  主机地址，例如：localhost:9527
     * @param state    实例状态
     */
    @Override
    public void updateState(int serverId, String address, SchedulerInstanceState state) {
        String hostName = HostUtils.getHostName();
        if (hostName.length() > 100) {
            hostName = hostName.substring(0, 100);
        }
        // 这里很低概率存在并发
        SchedulerInstanceEntity entity = this.mapper.selectByPrimaryKey(serverId);
        if (entity == null) {
            entity = new SchedulerInstanceEntity();
            entity.setId(serverId);
            entity.setAddress(address);
            entity.setHostName(hostName);
            entity.setState(state.getValue());
            int insert = this.mapper.insertSelective(entity);
            if (insert != 1) {
                logger.error("failed to insert scheduler instance, serverId:{}, address:{}", serverId, address);
            }
        } else {
            entity.setAddress(address);
            entity.setHostName(hostName);
            entity.setState(state.getValue());
            entity.setCreateTime(null);
            entity.setModifyTime(new Date());
            int update = this.mapper.updateByPrimaryKeySelective(entity);
            if (update != 1) {
                logger.error("failed to update scheduler instance, serverId:{}, address:{}", serverId, address);
            }
        }
    }

    /**
     * 下线过期的调度器
     */
    @Override
    public void offlineExpiredSchedulerInstance() {
        // 其他调度器实例有超时，就将状态置为离线
        SchedulerInstanceEntityExample example = new SchedulerInstanceEntityExample();
        example.or().andStateEqualTo(SchedulerInstanceState.ONLINE.getValue());
        List<SchedulerInstanceEntity> entities = mapper.selectByExample(example);
        for (SchedulerInstanceEntity entity : entities) {
            long time = entity.getModifyTime().getTime();
            // 超时时间超过10秒，置为下线
            if (entity.getState() == SchedulerInstanceState.ONLINE.getValue() && System.currentTimeMillis() - time > this.appConfig.getSchedulersTimeout()) {
                entity.setState(SchedulerInstanceState.OFFLINE.getValue());
                entity.setModifyTime(new Date());
                this.mapper.updateByPrimaryKeySelective(entity);
                logger.warn("the scheduler instance is offline, {}", entity);
            }
        }
    }

    /**
     * 获取全部的调度器
     *
     * @return
     */
    @Override
    public List<SchedulerInstanceEntity> getAllSchedulerInstance() {
        SchedulerInstanceEntityExample example = new SchedulerInstanceEntityExample();
        return this.mapper.selectByExample(example);
    }

    /**
     * 根据服务ID获取调度器信息
     *
     * @param serverId 服务ID
     * @return
     */
    @Override
    public SchedulerInstanceEntity getSchedulerInstance(int serverId) {
        return this.mapper.selectByPrimaryKey(serverId);
    }

    /**
     * 获取调度器列表
     *
     * @param params 查询参数
     * @return
     */
    @Override
    public Result<SchedulerInstanceListResult> getSchedulerInstanceList(GetSchedulerInstanceListParams params) {
        Result<SchedulerInstanceListResult> result = Result.success();
        SchedulerInstanceEntityExample example = new SchedulerInstanceEntityExample();
        int id = 0;
        int state = 0;
        if (StringUtils.isNotBlank(params.getId())) {
            id = Integer.parseInt(params.getId());
        }
        if (StringUtils.isNotBlank(params.getState())) {
            state = Integer.parseInt(params.getState());
        }

        // 条件设置
        SchedulerInstanceEntityExample.Criteria criteria = example.or();
        if (id > 0) {
            criteria.andIdEqualTo(id);
        }
        if (state > 0) {
            criteria.andStateEqualTo(state);
        }
        if (StringUtils.isNotBlank(params.getAddress())) {
            criteria.andAddressLike(LikeUtils.toLikeString(params.getAddress()));
        }

        SchedulerInstanceListResult userListResult = new SchedulerInstanceListResult();
        userListResult.setTotal(this.mapper.countByExample(example));
        int offset = (params.getCurrent() - 1) * params.getPageSize();
        int limit = params.getPageSize();
        example.setOrderByClause("`create_time` desc limit " + offset + ", " + limit);
        List<SchedulerInstanceEntity> entityList = this.mapper.selectByExample(example);
        userListResult.setCurrent(params.getCurrent());
        userListResult.setPageSize(params.getPageSize());
        userListResult.setData(this.schedulersAdapter.convert(entityList));
        result.setData(userListResult);
        return result;
    }

    /**
     * 获取当前实例的调度器地址
     *
     * @return
     */
    @Override
    public String getCurrentAddress() {
        return IPUtils.getLocalIpAddress() + ":" + this.appConfig.getPort();
    }

    /**
     * 根据调度器地址获取调度器实例
     *
     * @param address 调度器地址
     * @return
     */
    @Override
    public SchedulerInstanceEntity getSchedulerInstance(String address) {
        SchedulerInstanceEntityExample example = new SchedulerInstanceEntityExample();
        example.or().andAddressEqualTo(address);
        List<SchedulerInstanceEntity> list = this.mapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public int getSchedulerInstanceCount(SchedulerInstanceState state) {
        SchedulerInstanceEntityExample example = new SchedulerInstanceEntityExample();
        example.or().andStateEqualTo(state.getValue());
        return (int) this.mapper.countByExample(example);
    }

    /**
     * 停止心跳，并将调度器设置为下线状态
     */
    @Override
    public void stopSchedulerInstance() {
        this.running = false;
        this.updateState(this.appConfig.getServerId(), this.getCurrentAddress(), SchedulerInstanceState.OFFLINE);
        logger.info("stop scheduler instance");
    }
}