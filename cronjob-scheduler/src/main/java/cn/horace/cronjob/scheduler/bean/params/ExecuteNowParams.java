package cn.horace.cronjob.scheduler.bean.params;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created in 2024-12-14 22:50.
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class ExecuteNowParams {
    private String taskId;
    private String taskParams;
}