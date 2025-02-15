package cn.horace.cronjob.scheduler.bean.params;

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
public class GrantUserParams {
    private String userId;
    private String menuIds;
}