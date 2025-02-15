package cn.horace.cronjob.scheduler.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class AppItem {
    private String id;
    private String appName;
    private String appDesc;
    private int state;
    private TenantItem tenantItem;
    private int executorCount;
    private int taskCount;
    private String createTime;
}