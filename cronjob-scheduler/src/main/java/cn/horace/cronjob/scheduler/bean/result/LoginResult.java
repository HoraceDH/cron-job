package cn.horace.cronjob.scheduler.bean.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Horace
 */
@Data
@ToString
@AllArgsConstructor
public class LoginResult {
    /**
     * 登录成功后，返回token给前端
     */
    private String token;
}