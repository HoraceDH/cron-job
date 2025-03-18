package cn.horace.cronjob.scheduler.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 告警群信息
 * <p>
 * Created in 2025-03-18 23:29.
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class AlarmGroup {
    /**
     * 群ID
     */
    private String id;
    /**
     * 群名称
     */
    private String name;
    /**
     * 群头像
     */
    private String avatar;
}