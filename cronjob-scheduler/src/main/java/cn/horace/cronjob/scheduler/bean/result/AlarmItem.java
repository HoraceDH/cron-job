package cn.horace.cronjob.scheduler.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created in 2025-03-22 18:33.
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class AlarmItem {
    private String id;
    private String taskLogId;
    private String appName;
    private String taskName;
    private String executorAddress;
    private String executorHostName;
    private String method;
    private int state;
    private int alarmType;
    private String alarmGroupName;
    private String createTime;
    private String modifyTime;
}