/* 应用相关API */
import {request} from "@@/exports";
import Apis from "@/typings/apis";
import MsgCodes from "@/typings/msgcodes";
import UserService from "@/services/UserService";
import {AppBeans} from "@/typings/app";
import {Commons} from "@/typings/commons";

/**
 * 应用服务类
 */
class AppService {
    /**
     * 单例实例对象
     * @private
     */
    private static INSTANCE: AppService = new AppService();

    /**
     * 获取实例对象
     */
    public static getInstance(): AppService {
        return this.INSTANCE;
    }

    /**
     * 获取应用列表
     */
    public async getAppList(params: { current?: number; pageSize?: number; }, options?: {
        [key: string]: any
    }): Promise<AppBeans.AppItem[]> {
        let token = UserService.getInstance().getToken();
        if (token === undefined) {
            return [];
        }
        const appListResult = await request<AppBeans.AppListResult>(Apis.URI_APP_GET_LIST, {
            method: "POST",
            data: {
                ...params,
            },
            ...(options || {}),
        });
        if (appListResult.code !== MsgCodes.SUCCESS) {
            console.error("get app list failed, ", appListResult);
            return [];
        }
        return appListResult.data ? appListResult.data : [];
    }

    /**
     * 获取应用列表，提供给搜索框用
     */
    public async getSearchList(tenantId: string | undefined): Promise<Commons.SearchItem[]> {
        if (tenantId === undefined || tenantId === "") {
            tenantId = "-1";
        }
        const result = await request<Commons.SearchResult>(Apis.URI_APP_GET_SEARCH_LIST, {
            method: "POST",
            params: {tenantId: tenantId}
        });
        if (result.code !== MsgCodes.SUCCESS) {
            console.error("get search app list failed, ", result);
            return [];
        }
        return result.data;
    }

    /**
     * 获取搜索列表
     *
     * @param tenant 租户
     */
    public async getSearchListEnum(tenantId: string | undefined): Promise<Map<string, string>> {
        const items = await this.getSearchList(tenantId);
        const map = new Map<string, string>();
        for (let i = 0; i < items.length; i++) {
            const temp = items[i];
            map.set(temp.value, temp.label);
        }
        return map;
    }

    /**
     * 获取状态列表
     */
    public getStateList(): Commons.SearchItem[] {
        return [
            {value: "-1", label: "全部"},
            {value: "1", label: "运行"},
            {value: "2", label: "停止"},
        ];
    }

    /**
     * 停止App
     * @param id 应用ID
     */
    public async stopApp(id: string): Promise<void> {
        const result = await request<Commons.CommonResult>(Apis.URI_APP_STOP, {
            method: "POST",
            data: {id: id}
        });
        if (result.code !== MsgCodes.SUCCESS) {
            console.error("stop app error, id:", id, ", result:", result);
        }
    }

    /**
     * 启动App
     * @param id 应用ID
     */
    public async startApp(id: string): Promise<void> {
        const result = await request<Commons.CommonResult>(Apis.URI_APP_START, {
            method: "POST",
            data: {id: id}
        });
        if (result.code !== MsgCodes.SUCCESS) {
            console.error("start app error, id:", id, ", result:", result);
        }
    }
}

export default AppService;
