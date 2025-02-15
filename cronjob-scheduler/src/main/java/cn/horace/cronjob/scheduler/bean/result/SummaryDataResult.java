package cn.horace.cronjob.scheduler.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created in 2024-11-05 22:39.
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class SummaryDataResult {
    /**
     * 在线的执行器数
     */
    private int onlineSchedulerCount;
    /**
     * 总的租户数
     */
    private int totalTenantCount;
    /**
     * 在线的应用数
     */
    private int onlineAppCount;
    /**
     * 在线的执行器数
     */
    private int onlineExecutorCount;
    /**
     * 总的任务数
     */
    private int totalTaskCount;
    /**
     * 在线的任务数
     */
    private int onlineTaskCount;
}