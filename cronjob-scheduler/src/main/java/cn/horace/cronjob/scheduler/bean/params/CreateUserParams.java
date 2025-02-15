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
public class CreateUserParams {
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private String address;
    private String signature;
}