/* 执行器相关API */
import {Commons} from "@/typings/commons";
import {request} from "@@/exports";
import Apis from "@/typings/apis";
import MsgCodes from "@/typings/msgcodes";
import UserService from "@/services/UserService";
import {ExecutorBeans} from "@/typings/executor";

/**
 * 执行器服务类
 */
class ExecutorService {
    /**
     * 单例实例对象
     * @private
     */
    private static INSTANCE: ExecutorService = new ExecutorService();

    /**
     * 获取实例对象
     */
    public static getInstance(): ExecutorService {
        return this.INSTANCE;
    }

    /**
     * 获取执行器列表
     */
    public async getExecutorList(params: { current?: number; pageSize?: number; }, options?: {
        [key: string]: any
    }): Promise<ExecutorBeans.ExecutorItem[]> {
        let token = UserService.getInstance().getToken();
        if (token === undefined) {
            return [];
        }
        const executorListResult = await request<ExecutorBeans.ExecutorListResult>(Apis.URI_EXECUTOR_GET_LIST, {
            method: "POST",
            data: {
                ...params,
            },
            ...(options || {}),
        });
        if (executorListResult.code !== MsgCodes.SUCCESS) {
            console.error("get executor list failed, ", executorListResult);
            return [];
        }
        return executorListResult.data ? executorListResult.data : [];
    }
}

export default ExecutorService;
