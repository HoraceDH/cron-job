/* 任务相关API */
import {Commons} from "@/typings/commons";
import {request} from "@@/exports";
import Apis from "@/typings/apis";
import MsgCodes from "@/typings/msgcodes";
import {TaskBeans} from "@/typings/task";

/**
 * 执行器服务类
 */
class TaskService {
    /**
     * 单例实例对象
     * @private
     */
    private static INSTANCE: TaskService = new TaskService();

    /**
     * 获取实例对象
     */
    public static getInstance(): TaskService {
        return this.INSTANCE;
    }

    /**
     * 获取任务列表
     */
    public async getTaskList(params: { current?: number; pageSize?: number; }, options?: {
        [key: string]: any
    }): Promise<TaskBeans.TaskItem[]> {
        const taskListResult = await request<TaskBeans.TaskListResult>(Apis.URI_TASK_GET_LIST, {
            method: "POST",
            data: {
                ...params,
            },
            ...(options || {}),
        });
        if (taskListResult.code !== MsgCodes.SUCCESS) {
            return [];
        }
        return taskListResult.data ? taskListResult.data : [];
    }

    /**
     * 根据ID查询任务详情
     *
     * @param id 任务ID
     */
    public async getTask(id: string): Promise<TaskBeans.TaskItem | null> {
        const taskResult = await request<TaskBeans.TaskResult>(Apis.URI_TASK_GET, {
            method: "POST",
            params: {id: id}
        });
        if (taskResult.code !== MsgCodes.SUCCESS) {
            return null;
        }
        return taskResult.data ? taskResult.data : null;
    }

    /**
     * 更新任务运行状态
     *
     * @param id 任务ID
     * @param runState 任务状态，1：启动，2：停止
     */
    public async updateRunState(id: string | undefined, runState: number): Promise<boolean> {
        const result = await request<Commons.MsgObject>(Apis.URI_TASK_UPDATE_RUN_STATE, {
            method: "POST",
            params: {id: id, runState: runState}
        });
        if (result.code !== MsgCodes.SUCCESS) {
            return false;
        }
        return true;
    }

    /**
     * 获取状态列表
     */
    public getOnlineStateList(): Commons.SearchItem[] {
        return [
            {value: "-1", label: "全部"},
            {value: "1", label: "在线"},
            {value: "2", label: "离线"},
        ];
    }

    /**
     * 获取状态列表
     */
    public getRunStateList(): Commons.SearchItem[] {
        return [
            {value: "-1", label: "全部"},
            {value: "1", label: "启动"},
            {value: "2", label: "停止"},
        ];
    }

    /**
     * 立即执行一次任务
     *
     * @param taskId 任务ID
     * @param taskParams 任务参数
     */
    public async executeTask(taskId: string | undefined, taskParams: string | undefined): Promise<string> {
        const result = await request<Commons.CommonResult>(Apis.URI_TASK_EXECUTE, {
            method: "POST",
            data: {
                taskId: taskId,
                taskParams: taskParams,
            }
        });
        if (result.code !== MsgCodes.SUCCESS) {
            return result.data;
        }
        return result.data;
    }

    /**
     * 获取任务列表，提供给搜索框用
     *
     * @param tenantId 租户ID
     * @param appName 应用名
     */
    public async getSearchList(tenantId: string, appName: string): Promise<Commons.SearchItem[]> {
        if (tenantId === undefined || tenantId === "") {
            tenantId = "-1";
        }
        const result = await request<Commons.SearchResult>(Apis.URI_TASK_GET_SEARCH_LIST, {
            method: "POST",
            params: {tenantId: tenantId, appName: appName},
        });
        if (result.code !== MsgCodes.SUCCESS) {
            return [];
        }
        return result.data;
    }

    /**
     * 转换为map格式
     *
     * @param tenantId 租户ID
     * @param appName 应用名
     */
    public async getSearchListValueEnum(tenantId: string, appName: string): Promise<Map<string, string>> {
        const items = await this.getSearchList(tenantId, appName);
        const map = new Map<string, string>();
        for (let i = 0; i < items.length; i++) {
            const temp = items[i];
            map.set(temp.value, temp.label);
        }
        return map;
    }

    /**
     * 根据ID更新任务信息
     *
     * @param data 更新的字段集合
     */
    public async updateById(data: Record<string, any>): Promise<boolean> {
        const result = await request<Commons.MsgObject>(Apis.URI_TASK_UPDATE_BY_ID, {
            method: "POST",
            data: data,
        });
        if (result.code !== MsgCodes.SUCCESS) {
            return false;
        }
        return true;
    }

    /**
     * 检查Cron表达式语法是否正确
     *
     * @param cron 表达式
     */
    public async getRecentExecutionTime(cron: string): Promise<TaskBeans.CheckResult> {
        const taskResult = await request<TaskBeans.CheckResult>(Apis.URI_TASK_GET_RECENT_EXECUTIONTIME, {
            method: "POST",
            params: {cron: cron}
        });
        if (taskResult.code !== MsgCodes.SUCCESS) {
            return taskResult;
        }
        return taskResult;
    }
}

export default TaskService;
