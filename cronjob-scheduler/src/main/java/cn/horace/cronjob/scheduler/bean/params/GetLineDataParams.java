package cn.horace.cronjob.scheduler.bean.params;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created in 2024-11-07 21:36.
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class GetLineDataParams {
    private String taskId;
    private String startDate;
    private String endDate;
}