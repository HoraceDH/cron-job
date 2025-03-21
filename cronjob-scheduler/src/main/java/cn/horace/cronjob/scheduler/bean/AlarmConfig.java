package cn.horace.cronjob.scheduler.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created in 2025-03-21 21:06.
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AlarmConfig {
    /**
     * 0：不告警，1：飞书告警，2：企微告警，3：邮件告警
     */
    private int type;
    private String chatId;
    private String groupName;
}