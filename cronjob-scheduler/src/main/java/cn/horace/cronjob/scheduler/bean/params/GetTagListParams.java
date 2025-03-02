package cn.horace.cronjob.scheduler.bean.params;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created in 2025-03-02 15:20.
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class GetTagListParams {
    private String taskId;
}