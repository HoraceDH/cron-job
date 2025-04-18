package cn.horace.cronjob.scheduler.bean.params;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created in 2025-03-19 23:23.
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class SendAlarmParams {
    private int type;
    private String chatId;
    private String tenantName;
    private String appName;
    private String taskName;
    private String taskMethod;
    private String failedReason;
    private long taskLogId;
    private String url;
    private String owner;
}