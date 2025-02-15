package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.constants.UserState;
import cn.horace.cronjob.scheduler.entities.UserEntity;
import cn.horace.cronjob.scheduler.bean.params.CreateUserParams;
import cn.horace.cronjob.scheduler.bean.params.GetUserListParams;
import cn.horace.cronjob.scheduler.bean.params.LoginParams;
import cn.horace.cronjob.scheduler.bean.result.LoginResult;
import cn.horace.cronjob.scheduler.bean.result.UserListResult;
import cn.horace.cronjob.scheduler.bean.result.UserResult;

/**
 * 用户服务
 * <p>
 *
 * @author Horace
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param params 登录参数
     * @return
     */
    Result<LoginResult> userLogin(LoginParams params);

    /**
     * 获取用户信息
     *
     * @param token 用户Token
     * @return
     */
    Result<UserResult> getUser(String token);

    /**
     * 登出
     *
     * @param token 用户Token
     */
    void logout(String token);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    UserEntity getUserById(long id);

    /**
     * 获取用户列表
     *
     * @param params 请求参数
     * @return
     */
    Result<UserListResult> getUserList(GetUserListParams params);

    /**
     * 重置用户密码
     *
     * @param token  当前用户的Token
     * @param userId 需要重置的用户ID
     * @return
     */
    Result<String> resetPassword(String token, String userId);

    /**
     * 创建用户
     *
     * @param params 参数
     * @return
     */
    Result<String> createUser(CreateUserParams params);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return
     */
    UserEntity getUserByName(String username);

    /**
     * 删除用户
     *
     * @param userId    用户ID
     * @param userState 用户状态
     * @return
     */
    Result<Void> updateUserState(long userId, UserState userState);
}
