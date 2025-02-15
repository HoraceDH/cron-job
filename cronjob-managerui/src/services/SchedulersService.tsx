/* 调度器相关API */
import {request} from "@@/exports";
import Apis from "@/typings/apis";
import MsgCodes from "@/typings/msgcodes";
import UserService from "@/services/UserService";
import {SchedulersBeans} from "@/typings/schedulers";
import {Commons} from "@/typings/commons";

/**
 * 调度器服务类
 */
class SchedulersService {
    /**
     * 单例实例对象
     * @private
     */
    private static INSTANCE: SchedulersService = new SchedulersService();

    /**
     * 获取实例对象
     */
    public static getInstance(): SchedulersService {
        return this.INSTANCE;
    }

    /**
     * 获取调度器列表
     */
    public async getSchedulersList(params: { current?: number; pageSize?: number; }, options?: {
        [key: string]: any
    }): Promise<SchedulersBeans.SchedulersItem[]> {
        let token = UserService.getInstance().getToken();
        if (token === undefined) {
            return [];
        }
        const schedulersListResult = await request<SchedulersBeans.SchedulersListResult>(Apis.URI_SCHEDULERS_GET_LIST, {
            method: "POST",
            data: {
                ...params,
            },
            ...(options || {}),
        });
        if (schedulersListResult.code !== MsgCodes.SUCCESS) {
            console.error("get schedulers list failed, ", schedulersListResult);
            return [];
        }
        return schedulersListResult.data ? schedulersListResult.data : [];
    }

    /**
     * 获取状态列表
     */
    public getStateList(): Commons.SearchItem[] {
        return [
            {value: "-1", label: "全部"},
            {value: "1", label: "在线"},
            {value: "2", label: "离线"},
        ];
    }
}

export default SchedulersService;
