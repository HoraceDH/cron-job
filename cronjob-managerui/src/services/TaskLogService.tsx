/* 任务日志相关API */
import {Commons} from "@/typings/commons";
import {request} from "@@/exports";
import Apis from "@/typings/apis";
import MsgCodes from "@/typings/msgcodes";
import {TaskLogBeans} from "@/typings/tasklog";

/**
 * 执行器服务类
 */
class TaskLogService {
    /**
     * 单例实例对象
     * @private
     */
    private static INSTANCE: TaskLogService = new TaskLogService();

    /**
     * 获取实例对象
     */
    public static getInstance(): TaskLogService {
        return this.INSTANCE;
    }

    /**
     * 获取任务日志列表
     */
    public async getTaskLogList(params: { current?: number; pageSize?: number; }, options?: {
        [key: string]: any
    }): Promise<TaskLogBeans.TaskLogItem[]> {
        const taskListLogResult = await request<TaskLogBeans.TaskLogListResult>(Apis.URI_TASK_LOG_GET_LIST, {
            method: "POST",
            data: {
                ...params,
            },
            ...(options || {}),
        });
        if (taskListLogResult.code !== MsgCodes.SUCCESS) {
            console.error("get task log list failed, ", taskListLogResult);
            return [];
        }
        return taskListLogResult.data ? taskListLogResult.data : [];
    }

    /**
     * 根据ID查询任务日志详情
     *
     * @param id 任务日志ID
     */
    public async getTaskLog(id: string): Promise<TaskLogBeans.TaskLogItem | null> {
        const taskLogResult = await request<TaskLogBeans.TaskLogResult>(Apis.URI_TASK_LOG_GET, {
            method: "POST",
            params: {id: id}
        });
        if (taskLogResult.code !== MsgCodes.SUCCESS) {
            console.error("get task log failed, ", taskLogResult);
            return null;
        }
        return taskLogResult.data ? taskLogResult.data : null;
    }

    /**
     * 获取状态列表
     */
    public getTaskLogStateList(): Commons.SearchItem[] {
        return [
            {value: "-1", label: "全部"},
            {value: "1", label: "初始化"},
            {value: "2", label: "队列中"},
            {value: "3", label: "调度中"},
            {value: "4", label: "执行成功"},
            {value: "5", label: "执行失败"},
            {value: "6", label: "取消执行"},
            {value: "7", label: "任务过期"},
            {value: "8", label: "执行失败，已丢弃"},
            {value: "9", label: "执行失败，重试中"},
        ];
    }
}

export default TaskLogService;
