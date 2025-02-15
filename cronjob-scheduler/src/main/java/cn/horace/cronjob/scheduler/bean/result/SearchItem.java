package cn.horace.cronjob.scheduler.bean.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 租户搜索项
 * <p>
 *
 * @author Horace
 */
@Data
@ToString
@AllArgsConstructor
public class SearchItem {
    private String label;
    private String value;
}