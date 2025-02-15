package cn.horace.cronjob.scheduler.bean.params;

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
public class GetTenantListParams {
    private int current;
    private int pageSize;
    private String key;
    private String name;
    private String tenant;
    private String remark;
}