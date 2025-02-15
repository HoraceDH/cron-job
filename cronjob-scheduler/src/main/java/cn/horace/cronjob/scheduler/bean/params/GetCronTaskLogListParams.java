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
public class GetCronTaskLogListParams {
    private int current;
    private int pageSize;
    private String id;
    private String tenantId;
    private String taskId;
    private String appName;
    private String method;
    private String executorAddress;
    private String tag;
    private String schedulersAddress;
    private int state;
    private String params;
    private String remark;
    private String[] executionTimeRange;
}