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
public class GetAppListParams {
    private int current;
    private int pageSize;
    private int state;
    private String id;
    private String appName;
    private String tenantId;
}