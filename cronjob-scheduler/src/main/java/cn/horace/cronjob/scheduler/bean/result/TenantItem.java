package cn.horace.cronjob.scheduler.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 菜单项
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class TenantItem {
    private String key;
    private String name;
    private String tenant;
    private int appCount;
    private String remark;
    private String createTime;
}