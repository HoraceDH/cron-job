package cn.horace.cronjob.scheduler.adapter;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.scheduler.bean.result.AppItem;
import cn.horace.cronjob.scheduler.bean.result.TenantItem;
import cn.horace.cronjob.scheduler.entities.AppEntity;
import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.service.ExecutorService;
import cn.horace.cronjob.scheduler.service.TaskService;
import cn.horace.cronjob.scheduler.service.TenantService;
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
public class AppAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AppAdapter.class);
    @Lazy
    @Resource
    private TenantService tenantService;
    @Resource
    private TenantAdapter tenantAdapter;
    @Lazy
    @Resource
    private ExecutorService executorService;
    @Lazy
    @Resource
    private TaskService taskService;

    /**
     * 对象转换
     */
    public List<AppItem> convert(List<AppEntity> entityList) {
        List<AppItem> appItems = new ArrayList<>();
        for (AppEntity app : entityList) {
            AppItem appItem = new AppItem();
            appItem.setId(String.valueOf(app.getId()));
            appItem.setAppName(app.getAppName());
            appItem.setAppDesc(app.getAppDesc());
            appItem.setState(app.getState());
            appItem.setTenantItem(this.getTenantItem(app.getTenantId()));
            appItem.setExecutorCount(this.executorService.getOnlineCount(app.getId()));
            appItem.setTaskCount(this.taskService.getTaskCount(app.getId()));
            if (app.getCreateTime() != null) {
                appItem.setCreateTime(DateFormatUtils.format(app.getCreateTime(), Constants.DATE_FORMAT));
            }
            if (app.getModifyTime() != null) {
                appItem.setModifyTime(DateFormatUtils.format(app.getModifyTime(), Constants.DATE_FORMAT));
            }
            appItems.add(appItem);
        }
        return appItems;
    }

    /**
     * 获取租户信息
     *
     * @param tenantId 租户ID
     * @return
     */
    private TenantItem getTenantItem(long tenantId) {
        TenantEntity tenant = this.tenantService.getTenant(tenantId);
        return this.tenantAdapter.convertItem(tenant);
    }
}