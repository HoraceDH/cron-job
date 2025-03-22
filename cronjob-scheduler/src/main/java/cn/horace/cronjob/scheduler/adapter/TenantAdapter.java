package cn.horace.cronjob.scheduler.adapter;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.scheduler.bean.AlarmConfig;
import cn.horace.cronjob.scheduler.bean.result.TenantItem;
import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.service.AppService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
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
        if (entity.getAlarmConfig() != null) {
            AlarmConfig alarmConfig = JSONObject.parseObject(entity.getAlarmConfig(), AlarmConfig.class);
            tenantItem.setType(alarmConfig.getType() + "");
            tenantItem.setGroupName(alarmConfig.getGroupName());
            tenantItem.setChatId(alarmConfig.getChatId());
        }
        if (entity.getCreateTime() != null) {
            tenantItem.setCreateTime(DateFormatUtils.format(entity.getCreateTime(), Constants.DATE_FORMAT));
        }
        if (entity.getModifyTime() != null) {
            tenantItem.setModifyTime(DateFormatUtils.format(entity.getModifyTime(), Constants.DATE_FORMAT));
        }
        // 获取应用数
        tenantItem.setAppCount(this.appService.getAppCount(entity.getId()));
        return tenantItem;
    }

    /**
     * 对象转换
     *
     * @param params 参数
     * @return
     */
    public TenantEntity convertEntity(TenantItem params) {
        AlarmConfig alarmConfig = new AlarmConfig();
        alarmConfig.setType(Integer.parseInt(params.getType()));
        alarmConfig.setChatId(params.getChatId());
        alarmConfig.setGroupName(params.getGroupName());

        TenantEntity entity = new TenantEntity();
        entity.setId(Long.parseLong(params.getKey()));
        entity.setName(params.getName());
        entity.setTenant(params.getTenant());
        entity.setAlarmConfig(JSONObject.toJSONString(alarmConfig));
        entity.setRemark(params.getRemark());
        entity.setModifyTime(new Date());
        return entity;
    }
}