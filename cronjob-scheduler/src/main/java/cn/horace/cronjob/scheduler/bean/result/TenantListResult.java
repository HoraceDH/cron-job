package cn.horace.cronjob.scheduler.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 菜单项
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class TenantListResult {
    private int current;
    private int pageSize;
    private long total;
    private List<TenantItem> data;
}