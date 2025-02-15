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
public class ExecutorItem {
    private String address;
    private String tenant;
    private String appName;
    private String appDesc;
    private String hostName;
    private String tag;
    private int state;
    private String version;
    private String createTime;
    private String modifyTime;
}