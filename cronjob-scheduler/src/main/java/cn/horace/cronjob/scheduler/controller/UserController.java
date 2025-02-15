package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.scheduler.constants.Constants;
import cn.horace.cronjob.scheduler.constants.UserState;
import cn.horace.cronjob.scheduler.bean.params.CreateUserParams;
import cn.horace.cronjob.scheduler.bean.params.GetUserListParams;
import cn.horace.cronjob.scheduler.bean.params.LoginParams;
import cn.horace.cronjob.scheduler.bean.result.LoginResult;
import cn.horace.cronjob.scheduler.bean.result.UserListResult;
import cn.horace.cronjob.scheduler.bean.result.UserResult;
import cn.horace.cronjob.scheduler.context.WebContext;
import cn.horace.cronjob.scheduler.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户控制器
 * <p>
 *
 * @author Horace
 */
@RestController
@RequestMapping("/manager-api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private GuidGenerate guidGenerate;

    /**
     * 用户登录
     *
     * @return
     */
    @PostMapping(name = "用户登录", value = "/login")
    public MsgObject userLogin(@RequestBody LoginParams params) {
        Result<LoginResult> result = this.userService.userLogin(params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @PostMapping(name = "用户信息", value = "/getUser")
    public MsgObject getUser() {
        String token = WebContext.getContext().getToken();
        Result<UserResult> result = this.userService.getUser(token);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 获取用户信息列表
     *
     * @return
     */
    @PostMapping(name = "用户信息列表", value = "/getUserList")
    public MsgObject getUserList(@RequestBody GetUserListParams params) {
        Result<UserListResult> result = this.userService.getUserList(params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 用户登出
     *
     * @return
     */
    @PostMapping(name = "用户登出", value = "/logout")
    public MsgObject logout() {
        String token = WebContext.getContext().getToken();
        this.userService.logout(token);
        return MsgObject.success();
    }

    /**
     * 重置用户密码
     *
     * @return
     */
    @PostMapping(name = "重置用户密码", value = "/resetPassword")
    public MsgObject resetPassword(String userId) {
        long currentUserId = WebContext.getContext().getUserId();
        long targetUserId = Long.parseLong(userId);
        if (currentUserId != Constants.ADMIN_USER_ID && targetUserId == Constants.ADMIN_USER_ID) {
            return MsgObject.msgCodes(MsgCodes.ERROR_OPERATE_ADMIN_ACCOUNT);
        }
        String token = WebContext.getContext().getToken();
        Result<String> result = this.userService.resetPassword(token, userId);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 创建用户
     *
     * @param params 参数
     * @return
     */
    @PostMapping(name = "创建用户", value = "/createUser")
    public MsgObject createUser(@RequestBody CreateUserParams params) {
        Result<String> result = this.userService.createUser(params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }

    /**
     * 禁用用户
     *
     * @param userId 用户ID
     * @return
     */
    @PostMapping(name = "禁用用户", value = "/updateUserState")
    public MsgObject updateUserState(String userId, int state) {
        long uid = Long.parseLong(userId);
        UserState userState = UserState.from(state);
        if (userState == null || uid <= 0) {
            return MsgObject.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        if (uid == Constants.ADMIN_USER_ID) {
            return MsgObject.msgCodes(MsgCodes.ERROR_OPERATE_ADMIN_ACCOUNT);
        }
        Result<Void> result = this.userService.updateUserState(uid, userState);
        if (result.isSuccess()) {
            return MsgObject.success();
        } else {
            return MsgObject.msgCodes(result.getMsgCodes());
        }
    }
}