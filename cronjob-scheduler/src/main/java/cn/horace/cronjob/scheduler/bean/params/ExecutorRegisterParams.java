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
public class ExecutorRegisterParams {
    /**
     * 租户编码
     */
    @NotBlank
    private String tenant;
    /**
     * 应用名称
     */
    @NotBlank
    private String appName;
    /**
     * 应用描述
     */
    @NotBlank
    private String appDesc;
    /**
     * 主机名
     */
    @NotBlank
    private String hostName;
    /**
     * 标签，用于给特定执行器做标记
     */
    @NotBlank
    private String tag;
    /**
     * 执行器版本
     */
    private String version;
    /**
     * 执行器地址
     */
    @NotBlank
    private String address;
}