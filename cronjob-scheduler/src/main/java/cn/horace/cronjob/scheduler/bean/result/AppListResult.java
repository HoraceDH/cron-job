package cn.horace.cronjob.scheduler.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class AppListResult {
    private int current;
    private int pageSize;
    private long total;
    private List<AppItem> data;
}