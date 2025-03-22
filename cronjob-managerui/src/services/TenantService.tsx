/* 租户相关API */
import {Commons} from "@/typings/commons";
import {request} from "@@/exports";
import Apis from "@/typings/apis";
import MsgCodes from "@/typings/msgcodes";
import {TenantBeans} from "@/typings/tenant";
import UserService from "@/services/UserService";
import {message} from "antd";

/**
 * 租户服务类
 */
class TenantService {
    /**
     * 单例实例对象
     * @private
     */
    private static INSTANCE: TenantService = new TenantService();
    private tenantItems: Commons.SearchItem[] = [];

    /**
     * 获取实例对象
     */
    public static getInstance(): TenantService {
        return this.INSTANCE;
    }

    /**
     * 获取当前用户的租户ID
     * @param userId
     */
    public async getTenantIds(userId: string): Promise<string[]> {
        const result = await request<Menu.MyMenuIdResult>(Apis.URI_TENANT_GET_TENANT_IDS, {
            method: "POST", params: {userId: userId}
        });
        if (result.code !== MsgCodes.SUCCESS) {
            return [];
        }
        return result.data;
    }

    /**
     * 获取租户信息
     *
     * @param id 租户ID
     */
    public async getTenantDetail(id: string): Promise<TenantBeans.TenantItem | undefined> {
        const result = await request<TenantBeans.TenantResult>(Apis.URI_TENANT_GET_TENANT, {
            method: "POST", data: {id: id}
        });
        if (result.code !== MsgCodes.SUCCESS) {
            return;
        }
        return result.data;
    }

    /**
     * 获取租户列表
     */
    public async getAllTenantList(params: { current?: number; pageSize?: number; }, options?: {
        [key: string]: any
    }): Promise<TenantBeans.TenantItem[]> {
        let token = UserService.getInstance().getToken();
        if (token === undefined) {
            return [];
        }
        const tenantResult = await request<TenantBeans.AllTenantResult>(Apis.URI_TENANT_GET_ALL_LIST, {
            method: "POST",
            data: {
                ...params,
            },
            ...(options || {}),
        });
        if (tenantResult.code !== MsgCodes.SUCCESS) {
            console.error("get all tenant list failed, ", tenantResult);
            return [];
        }
        return tenantResult.data ? tenantResult.data : [];
    }

    /**
     * 授权租户
     *
     * @param param 参数
     */
    public async grantTenant(param: { tenantIds: string | undefined | null; userId: string | undefined }) {
        return await request<Commons.MsgObject>(Apis.URI_TENANT_GRANT, {
            method: "POST",
            data: param
        });
    }

    /**
     * 获取租户列表，提供给搜索框用
     */
    public async getSearchList(): Promise<Commons.SearchItem[]> {
        if (this.tenantItems === null || this.tenantItems === undefined || this.tenantItems.length === 0) {
            const result = await request<Commons.SearchResult>(Apis.URI_TENANT_GET_SEARCH_LIST, {
                method: "POST",
            });
            if (result.code !== MsgCodes.SUCCESS) {
                console.error("get search tenant list failed, ", result);
                this.tenantItems = [];
            }
            this.tenantItems = result.data;
        }
        return this.tenantItems;
    }

    /**
     * 更新租户信息
     *
     * @param tenant 租户信息
     */
    public async updateById(tenant: TenantBeans.TenantItem): Promise<Commons.CommonResult> {
        const result = await request<Commons.CommonResult>(Apis.URI_TENANT_POST_UPDATE_BY_ID, {
            method: "POST",
            data: tenant,
        });
        if (result.code !== MsgCodes.SUCCESS) {
            console.error("update tenant failed, ", result);
            message.error("更新租户信息失败！" + result.msg);
            return result;
        }
        return result;
    }
}

export default TenantService;
