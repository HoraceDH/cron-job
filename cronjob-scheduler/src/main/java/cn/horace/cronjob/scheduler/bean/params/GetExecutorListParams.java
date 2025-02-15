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
public class GetExecutorListParams {
    private int current;
    private int pageSize;
    private String address;
    private String appName;
    private String appDesc;
    private String tenantId;
    private String tag;
    private int state;
}