package cn.horace.cronjob.scheduler.bean.params;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 登录参数
 * <p>
 *
 * @author Horace
 */
@Data
@ToString
@NoArgsConstructor
public class LoginParams {
    /**
     * 登录类型
     */
    private String type;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码，加密后的文本
     */
    private String password;
}