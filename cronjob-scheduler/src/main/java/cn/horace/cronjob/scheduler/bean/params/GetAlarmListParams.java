package cn.horace.cronjob.scheduler.bean.params;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Horace
 */

@Data
@ToString
@NoArgsConstructor
public class GetAlarmListParams {
    private int current;
    private int pageSize;
    private String id;
    private String taskLogId;
    private String appName;
    private Integer state;
    private String taskName;
    private String executorAddress;
    private String executorHostName;
    private String method;
    private String createTime;
    private String[] createTimeRange;
}