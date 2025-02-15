package cn.horace.cronjob.scheduler.bean.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created in 2024-11-07 21:39.
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LineDataItem {
    /**
     * 指标名称
     */
    private String name;
    /**
     * 指标时间
     */
    private String date;
    /**
     * 指标值
     */
    private double value;
}