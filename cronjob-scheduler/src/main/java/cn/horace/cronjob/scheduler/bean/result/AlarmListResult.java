package cn.horace.cronjob.scheduler.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class AlarmListResult {
    private int current;
    private int pageSize;
    private long total;
    private List<AlarmItem> data;
}