/**
 * 用户相关
 * @author Horace
 */
declare namespace UserBeans {
    /**
     * 用户对象
     */
    type User = {
        id?: string;
        username?: string;
        nickname?: string;
        password?: string;
        state?: int;
        email?: string;
        phone?: string;
        avatar?: string;
        address?: string;
        signature?: string;
    };

    /**
     * 登录参数
     */
    type LoginParams = {
        username?: string;
        password?: string;
        autoLogin?: boolean;
        type?: string;
    };

    /**
     * 登录结果
     */
    type LoginResult = Commons.MsgObject & {
        // 用户Token
        data: {
            token: string;
        };
        status?: string;
        type?: string;
        currentAuthority?: string;
    };

    /**
     * 获取用户的结果
     */
    type GetUserResult = Commons.MsgObject & {
        data?: User
    };

    /**
     * 获取用户列表的结果
     */
    type GetUserListResult = Commons.MsgObject & {
        data?: User[],
        total: number,
        success: true,
        pageSize: number,
        current: number
    };

    /**
     * 重置密码的结果
     */
    type ResetPasswordResult = Commons.MsgObject & {
        data?: string,
    };

    /**
     * 创建用户的结果
     */
    type CreateUserResult = Commons.MsgObject & {
        data?: string,
    };

}
