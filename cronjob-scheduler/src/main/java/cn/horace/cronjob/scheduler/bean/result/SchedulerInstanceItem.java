package cn.horace.cronjob.scheduler.bean.result;

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
public class SchedulerInstanceItem {
    private int id;
    private int state;
    private String address;
    private String hostName;
    private String createTime;
    private String modifyTime;
}