/* 告警相关API */
import {Commons} from "@/typings/commons";
import {request} from "@@/exports";
import Apis from "@/typings/apis";
import MsgCodes from "@/typings/msgcodes";
import {message} from "antd";

/**
 * 租户服务类
 */
class AlarmService {
    /**
     * 单例实例对象
     * @private
     */
    private static INSTANCE: AlarmService = new AlarmService();
    private alarmItems: Commons.SearchItem[] = [];

    /**
     * 获取实例对象
     */
    public static getInstance(): AlarmService {
        return this.INSTANCE;
    }

    /**
     * 获取告警渠道列表，提供给搜索框用
     */
    public async getSearchList(): Promise<Commons.SearchItem[]> {
        if (this.alarmItems === null || this.alarmItems === undefined || this.alarmItems.length === 0) {
            const result = await request<Commons.SearchResult>(Apis.URI_ALARM_GET_SEARCH_LIST, {
                method: "POST",
            });
            if (result.code !== MsgCodes.SUCCESS) {
                console.error("get search alarm channel failed, ", result);
                this.alarmItems = [];
                message.error("获取告警渠道列表失败，请重试。");
            }
            this.alarmItems = result.data;
        }
        return this.alarmItems;
    }

    /**
     * 获取群组列表
     *
     * @param type 渠道值
     */
    public async getGroupList(type: string): Promise<Commons.SearchItem[]> {
        const result = await request<Commons.SearchResult>(Apis.URI_ALARM_GET_GROUP_LIST, {
            method: "POST",
            data: {
                type: type
            }
        });
        if (result.code !== MsgCodes.SUCCESS) {
            console.error("get alarm group list failed, ", result);
            message.error("获取告警群列表失败，请重试。");
            return [];
        }
        return result.data;
    }

    /**
     * 发送告警信息
     *
     * @param config 告警配置
     */
    public async sendAlarm(config: AlarmBeans.AlarmConfig): Promise<Commons.CommonResult> {
        const result = await request<Commons.CommonResult>(Apis.URI_ALARM_POST_SEND_ALARM, {
            method: "POST",
            data: {
                type: config.type,
                chatId: config.chatId,
            }
        });
        if (result.code !== MsgCodes.SUCCESS) {
            console.error("send alarm failed, ", result);
            message.error("发送告警信息失败：" + result.msg);
        } else {
            message.success("发送告警信息成功！");
        }
        return result;
    }
}

export default AlarmService;
