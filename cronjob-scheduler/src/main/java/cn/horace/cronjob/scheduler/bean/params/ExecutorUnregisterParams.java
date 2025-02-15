package cn.horace.cronjob.scheduler.bean.params;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * Created in 2024-12-07 12:52.
 *
 * @author Horace
 */
@Data
@ToString
public class ExecutorUnregisterParams {
    /**
     * 执行器地址
     */
    @NotBlank
    private String address;
}