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
public class GetSchedulerInstanceListParams {
    private int current;
    private int pageSize;
    private String id;
    private String state;
    private String address;
}