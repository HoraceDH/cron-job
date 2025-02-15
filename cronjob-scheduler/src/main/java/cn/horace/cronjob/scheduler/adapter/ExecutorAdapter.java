package cn.horace.cronjob.scheduler.adapter;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.commons.constants.ExecutorState;
import cn.horace.cronjob.scheduler.bean.params.ExecutorRegisterParams;
import cn.horace.cronjob.scheduler.bean.result.ExecutorItem;
import cn.horace.cronjob.scheduler.entities.AppEntity;
import cn.horace.cronjob.scheduler.entities.ExecutorEntity;
import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.service.TenantService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Horace
 */
@Component
public class ExecutorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorAdapter.class);
    @Resource
    private TenantService tenantService;

    /**
     * 对象转换
     *
     * @param entityList 实体列表
     * @return
     */
    public List<ExecutorItem> convert(List<ExecutorEntity> entityList) {
        List<ExecutorItem> result = new ArrayList<ExecutorItem>();
        for (ExecutorEntity entity : entityList) {
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
    private ExecutorItem convert(ExecutorEntity entity) {
        ExecutorItem item = new ExecutorItem();
        item.setAddress(entity.getAddress());
        item.setTenant(this.tenantService.getTenant(entity.getTenantId()).getTenant());
        item.setAppName(entity.getAppName());
        item.setAppDesc(entity.getAppDesc());
        item.setHostName(entity.getHostName());
        item.setTag(entity.getTag());
        item.setState(entity.getState());
        item.setVersion(entity.getVersion());

        if (entity.getCreateTime() != null) {
            item.setCreateTime(DateFormatUtils.format(entity.getCreateTime(), Constants.DATE_FORMAT));
        }
        if (entity.getModifyTime() != null) {
            item.setModifyTime(DateFormatUtils.format(entity.getModifyTime(), Constants.DATE_FORMAT));
        }
        return item;
    }

    /**
     * 转换为实体对象
     *
     * @param params 参数
     * @param tenant 租户
     * @param app    应用
     * @return
     */
    public ExecutorEntity convert(ExecutorRegisterParams params, TenantEntity tenant, AppEntity app) {
        String hostName = params.getHostName();
        if (hostName.length() > 100) {
            hostName = hostName.substring(0, 100);
        }
        Date date = new Date();
        ExecutorEntity entity = new ExecutorEntity();
        entity.setAddress(params.getAddress());
        entity.setTenantId(tenant.getId());
        entity.setAppId(app.getId());
        entity.setAppName(app.getAppName());
        entity.setAppDesc(app.getAppDesc());
        entity.setHostName(hostName);
        entity.setTag(params.getTag());
        entity.setState(ExecutorState.ONLINE.getValue());
        entity.setVersion(params.getVersion());
        entity.setCreateTime(date);
        entity.setModifyTime(date);
        return entity;
    }
}