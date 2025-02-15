import {Settings as LayoutSettings} from "@ant-design/pro-layout";

// 全局共享状态
export default interface GlobalState {
    settings?: Partial<LayoutSettings>;
    currentUser?: UserBeans.User;
    menus?: any[];
    searchMenus?: any[];
    loading?: boolean;
    pageState?: Map;
}

declare namespace Commons {
    /**
     * 消息结构
     */
    type MsgObject = {
        code?: number;
        msg?: string;
    }

    type PageParams = {
        current?: number;
        pageSize?: number;
    };

    // 搜索项
    type SearchItem = {
        label: string, // 标签
        value: string, // 值
    }

    // 提供给搜索框用
    type SearchResult = Commons.MsgObject & {
        data: SearchItem[],
    }

    // 一个较为通用的响应结构
    type CommonResult = Commons.MsgObject & {
        data: string,
    }
}
