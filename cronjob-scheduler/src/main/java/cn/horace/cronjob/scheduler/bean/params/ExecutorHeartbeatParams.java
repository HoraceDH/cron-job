package cn.horace.cronjob.scheduler.bean.params;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author Horace
 */
@Data
@ToString
public class ExecutorHeartbeatParams {
    /**
     * 执行器地址
     */
    @NotBlank
    private String address;
}