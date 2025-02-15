package cn.horace.cronjob.scheduler.adapter;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.scheduler.bean.result.TaskItem;
import cn.horace.cronjob.scheduler.entities.TaskEntity;
import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.service.TenantService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Horace
 */
@Component
public class TaskAdapter {
    @Resource
    private TenantService tenantService;

    /**
     * 对象转换
     *
     * @param entityList 实体列表
     * @return
     */
    public List<TaskItem> convert(List<TaskEntity> entityList) {
        List<TaskItem> result = new ArrayList<>();
        for (TaskEntity entity : entityList) {
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
    public TaskItem convert(TaskEntity entity) {
        TaskItem item = new TaskItem();
        item.setId(String.valueOf(entity.getId()));
        TenantEntity tenant = this.tenantService.getTenant(entity.getTenantId());
        if (tenant != null) {
            item.setTenant(tenant.getTenant());
        }

        item.setAppName(entity.getAppName());
        item.setAppDesc(entity.getAppDesc());
        item.setName(entity.getName());
        item.setOwner(entity.getOwner());
        item.setCron(entity.getCron());
        item.setTag(entity.getTag());
        item.setRunState(entity.getRunState());
        item.setMethod(entity.getMethod());
        item.setRouterStrategy(entity.getRouterStrategy());
        item.setExpiredStrategy(entity.getExpiredStrategy());
        item.setExpiredTime(entity.getExpiredTime());
        item.setFailureStrategy(entity.getFailureStrategy());
        item.setMaxRetryCount(entity.getMaxRetryCount());
        item.setFailureRetryInterval(entity.getFailureRetryInterval());
        item.setParams(entity.getTaskParams());
        item.setRemark(entity.getRemark());
        if (entity.getCreateTime() != null) {
            item.setCreateTime(DateFormatUtils.format(entity.getCreateTime(), Constants.DATE_FORMAT));
        }
        if (entity.getModifyTime() != null) {
            item.setModifyTime(DateFormatUtils.format(entity.getModifyTime(), Constants.DATE_FORMAT));
        }
        return item;
    }
}