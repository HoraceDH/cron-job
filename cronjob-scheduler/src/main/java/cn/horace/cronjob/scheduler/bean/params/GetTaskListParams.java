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
public class GetTaskListParams {
    private int current;
    private int pageSize;
    private String id;
    private String tenantId;
    private String appName;
    private String appDesc;
    private String tag;
    private int runState;
    private String method;
    private String remark;
}