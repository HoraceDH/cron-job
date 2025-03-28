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
public class GetMenuListParams {
    private int current;
    private int pageSize;
    private String id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    private String signature;
}