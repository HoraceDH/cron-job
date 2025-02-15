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
public class UserResult {
    private String id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private int state;
    private String avatar;
    private String address;
    private String signature;
    private String createTime;
}