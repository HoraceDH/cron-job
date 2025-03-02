package cn.horace.cronjob.scheduler.adapter;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.scheduler.bean.result.TaskLogItem;
import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.service.TenantService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Horace
 */
@Component
public class TaskLogAdapter {
    @Resource
    private TenantService tenantService;

    /**
     * 对象转换
     *
     * @param entityList 实体列表
     * @return
     */
    public List<TaskLogItem> convert(List<TaskLogEntity> entityList) {
        List<TaskLogItem> result = new ArrayList<>();
        for (TaskLogEntity entity : entityList) {
            result.add(this.convert(entity));
        }
        return result;
    }

    /**
     * 对象转换
     *
     * @param entity 实体对象
     * @return
     */
    public TaskLogItem convert(TaskLogEntity entity) {
        TaskLogItem item = new TaskLogItem();
        item.setId(String.valueOf(entity.getId()));
        item.setTaskId(String.valueOf(entity.getTaskId()));
        TenantEntity tenant = this.tenantService.getTenant(entity.getTenantId());
        if (tenant != null) {
            item.setTenant(tenant.getTenant());
        }
        item.setAppName(entity.getAppName());
        item.setMethod(entity.getMethod());
        item.setTaskName(entity.getName());
        item.setOwner(entity.getOwner());
        item.setCron(entity.getCron());
        item.setExecutorAddress(entity.getExecutorAddress());
        item.setExecutorHostName(entity.getExecutorHostName());
        if (entity.getElapsedTime() != null) {
            item.setElapsedTime(entity.getElapsedTime());
        }
        item.setTag(entity.getTag());
        item.setExeType(entity.getExeType());
        item.setRouterStrategy(entity.getRouterStrategy());
        item.setExpiredStrategy(entity.getExpiredStrategy());
        item.setExpiredTime(entity.getExpiredTime());
        item.setFailureStrategy(entity.getFailureStrategy());
        item.setMaxRetryCount(entity.getMaxRetryCount());
        item.setRetryCount(entity.getRetryCount());
        item.setFailureRetryInterval(entity.getFailureRetryInterval());
        item.setSchedulersAddress(entity.getSchedulerAddress());
        item.setState(entity.getState());
        item.setParams(entity.getTaskParams());
        item.setRemark(entity.getRemark());
        item.setFailedReason(entity.getFailedReason());
        if (entity.getExecutionTime() != null) {
            int retryTime = entity.getRetryCount() * entity.getFailureRetryInterval();
            long firstTime = entity.getExecutionTime().getTime() - retryTime;
            item.setFirstExecutionTime(DateFormatUtils.format(firstTime, Constants.DATE_FORMAT));
            item.setExecutionTime(DateFormatUtils.format(entity.getExecutionTime(), Constants.DATE_FORMAT));
        }
        if (entity.getRealExecutionTime() != null) {
            item.setRealExecutionTime(DateFormatUtils.format(entity.getRealExecutionTime(), Constants.DATE_FORMAT));
        }
        if (entity.getCreateTime() != null) {
            item.setCreateTime(DateFormatUtils.format(entity.getCreateTime(), Constants.DATE_FORMAT));
        }
        if (entity.getModifyTime() != null) {
            item.setModifyTime(DateFormatUtils.format(entity.getModifyTime(), Constants.DATE_FORMAT));
        }
        if (entity.getDispatchTime() != null) {
            item.setDispatcherTime(DateFormatUtils.format(entity.getDispatchTime(), Constants.DATE_FORMAT));
        }
        if (entity.getRealExecutionTime() != null && entity.getExecutionTime() != null) {
            int delayTime = (int) (entity.getRealExecutionTime().getTime() - entity.getExecutionTime().getTime());
            item.setDelayTime(delayTime);
        }
        item.setPage(entity.getPage());
        item.setTotal(entity.getTotal());
        return item;
    }

    /**
     * 拷贝对象属性
     *
     * @param taskLog 任务日志
     * @return
     */
    public TaskLogEntity copy(TaskLogEntity taskLog) {
        TaskLogEntity entity = new TaskLogEntity();
        entity.setId(taskLog.getId());
        entity.setTenantId(taskLog.getTenantId());
        entity.setAppId(taskLog.getAppId());
        entity.setAppName(taskLog.getAppName());
        entity.setAppDesc(taskLog.getAppDesc());
        entity.setName(taskLog.getName());
        entity.setTaskId(taskLog.getTaskId());
        entity.setExecutorAddress(taskLog.getExecutorAddress());
        entity.setMethod(taskLog.getMethod());
        entity.setExeType(taskLog.getExeType());
        entity.setElapsedTime(taskLog.getElapsedTime());
        entity.setVersion(taskLog.getVersion());
        entity.setCron(taskLog.getCron());
        entity.setTag(taskLog.getTag());
        entity.setRouterStrategy(taskLog.getRouterStrategy());
        entity.setExpiredStrategy(taskLog.getExpiredStrategy());
        entity.setExpiredTime(taskLog.getExpiredTime());
        entity.setFailureStrategy(taskLog.getFailureStrategy());
        entity.setMaxRetryCount(taskLog.getMaxRetryCount());
        entity.setRetryCount(taskLog.getRetryCount());
        entity.setFailureRetryInterval(taskLog.getFailureRetryInterval());
        entity.setTimeout(taskLog.getTimeout());
        entity.setSchedulerAddress(taskLog.getSchedulerAddress());
        entity.setState(taskLog.getState());
        entity.setTaskParams(taskLog.getTaskParams());
        entity.setPage(taskLog.getPage());
        entity.setTotal(taskLog.getTotal());
        entity.setParentId(taskLog.getParentId());
        entity.setRemark(taskLog.getRemark());
        entity.setExecutionTime(taskLog.getExecutionTime());
        entity.setRealExecutionTime(taskLog.getRealExecutionTime());
        entity.setCreateTime(new Date());
        entity.setModifyTime(new Date());
        entity.setFailedReason(taskLog.getFailedReason());
        return entity;
    }
}