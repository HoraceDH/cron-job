/* 用户相关API */
import {Pages} from "@/typings/pages";
import {request} from '@umijs/max';
import Apis from "@/typings/apis";
import MsgCodes from "@/typings/msgcodes";
import {Md5} from "ts-md5";
import {Commons} from "@/typings/commons";

/**
 * 用户服务类
 */
class UserService {
    /**
     * 单例实例对象
     * @private
     */
    private static INSTANCE: UserService = new UserService();

    /**
     * 获取实例对象
     */
    public static getInstance(): UserService {
        return this.INSTANCE;
    }

    /**
     * 加密Token
     * @param token
     * @private
     */
    private encryptToken(token: string) {
        let encryptedToken = '';
        for (let i = 0; i < token.length; i++) {
            const charCode = token.charCodeAt(i);
            encryptedToken += String.fromCharCode(charCode ^ 0x55);
        }
        return encryptedToken;
    }

    /**
     * 解密Token
     * @param token
     * @private
     */
    private decryptToken(token: string) {
        return this.encryptToken(token);
    }

    /**
     * 获取用户Token
     */
    public getToken(): string | undefined {
        const token = localStorage.getItem("_cache_");
        if (token === null || token === undefined || token.trim() === "" || token.startsWith("H")) {
            return undefined
        }
        return this.decryptToken(token);
    }

    /**
     * 设置用户Token
     * @param token Token
     */
    public setUserToken(token: string) {
        token = this.encryptToken(token);
        localStorage.setItem("_cache_", token);
    }

    /**
     * 清理用户Token
     * @private
     */
    public clearToken() {
        localStorage.setItem("_cache_", "H" + this.encryptToken("392f078e11a44719790de52a02996cb"));
    }

    /**
     * 用户登录
     * @param params
     */
    public async login(params: UserBeans.LoginParams): Promise<UserBeans.LoginResult> {
        let loginResult: UserBeans.LoginResult = {
            code: MsgCodes.ERROR_SYSTEM,
            msg: MsgCodes.ERROR_SYSTEM_MSG,
            data: {token: ""},
        }
        params.password = Md5.hashStr(params.password === undefined ? "" : params.password);
        loginResult = await request<UserBeans.LoginResult>(Apis.URI_USER_LOGIN, {
            method: 'POST',
            data: params,
        });
        if (loginResult.code === MsgCodes.SUCCESS) {
            // 存储到LocalStore中
            this.setUserToken(loginResult.data.token)
        }
        return loginResult;
    }

    /**
     * 登出，退出登录
     */
    public async logout() {
        // 发送网络请求退出登录
        const token = this.getToken();
        if (token !== undefined) {
            await request<Commons.MsgObject>(Apis.URI_USER_LOGOUT, {method: "POST",});
        }
        // 清理本地token
        this.clearToken();
        // 跳转到登录页面
        window.location.href = Pages.PAGE_URL_USER_LOGIN;
    }

    /**
     * 获取用户信息
     */
    public async getUser(): Promise<UserBeans.User | undefined> {
        try {
            const token = this.getToken();
            // 未登录
            if (token === undefined) {
                return undefined;
            }
            const userResult = await request<UserBeans.GetUserResult>(Apis.URI_USER_GET_USER, {method: 'POST',});
            if (userResult.code === MsgCodes.SUCCESS) {
                return userResult.data;
            }
            return undefined;
        } catch (e) {
            console.error("get user error,", e);
        }
        return undefined;
    }

    /**
     * 获取用户列表
     * @param params 请求参数
     * @param options 查询参数
     */
    public async getUserList(params: { current?: number; pageSize?: number; }, options?: {
        [key: string]: any
    }): Promise<UserBeans.User[]> {
        try {
            const userListResult = await request<UserBeans.GetUserListResult>(Apis.URI_USER_GET_USER_LIST, {
                    method: 'POST',
                    data: {
                        ...params,
                    },
                    ...(options || {}),
                }
            );
            if (userListResult.code === MsgCodes.SUCCESS) {
                return userListResult.data;
            }
            return [];
        } catch (e) {
            console.error("get user list error,", e);
        }
        return []
    }

    /**
     * 重置密码
     * @param userId 用户ID
     */
    public async resetPassword(userId: string): Promise<UserBeans.ResetPasswordResult> {
        return await request<UserBeans.ResetPasswordResult>(Apis.URI_USER_RESET_PASSWORD, {
            method: "POST",
            params: {"userId": userId}
        });
    }

    /**
     * 创建用户
     * @param user
     */
    public async createUser(user: UserBeans.User): Promise<UserBeans.CreateUserResult> {
        return await request<UserBeans.CreateUserResult>(Apis.URI_USER_CREATE_USER, {
            method: "POST",
            data: user
        });
    }

    /**
     * 删除用户
     * @param userId 用户ID
     */
    public async updateUserState(userId: string, state: number): Promise<UserBeans.ResetPasswordResult> {
        return await request<UserBeans.ResetPasswordResult>(Apis.URI_USER_UPDATE_USER_STATE, {
            method: "POST",
            params: {"userId": userId, state: state}
        });
    }

    /**
     * 获取状态列表
     */
    public getUserStateList(): Commons.SearchItem[] {
        return [
            {value: "-1", label: "全部"},
            {value: "0", label: "正常"},
            {value: "1", label: "禁用"},
        ];
    }
}

export default UserService;
