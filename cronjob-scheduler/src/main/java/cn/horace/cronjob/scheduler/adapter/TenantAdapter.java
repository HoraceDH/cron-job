package cn.horace.cronjob.scheduler.adapter;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.scheduler.bean.result.TenantItem;
import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.service.AppService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Horace
 */
@Component
public class TenantAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TenantAdapter.class);
    @Lazy
    @Resource
    private AppService appService;

    /**
     * 对象转换
     *
     * @param tenantEntities 实体列表
     * @return
     */
    public List<TenantItem> convertTenantItems(List<TenantEntity> tenantEntities) {
        ArrayList<TenantItem> tenantItems = new ArrayList<>();
        for (TenantEntity entity : tenantEntities) {
            tenantItems.add(this.convertItem(entity));
        }
        return tenantItems;
    }

    /**
     * 对象转换
     *
     * @param entity 实体
     * @return
     */
    public TenantItem convertItem(TenantEntity entity) {
        TenantItem tenantItem = new TenantItem();
        tenantItem.setKey(String.valueOf(entity.getId()));
        tenantItem.setName(entity.getName());
        tenantItem.setTenant(entity.getTenant());
        tenantItem.setRemark(entity.getRemark());
        if (entity.getCreateTime() != null) {
            tenantItem.setCreateTime(DateFormatUtils.format(entity.getCreateTime(), Constants.DATE_FORMAT));
        }
        // 获取应用数
        tenantItem.setAppCount(this.appService.getAppCount(entity.getId()));
        return tenantItem;
    }
}