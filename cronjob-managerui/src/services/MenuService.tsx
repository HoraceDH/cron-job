/**
 * 菜单服务
 *
 * @author Horace
 */
import UserService from "@/services/UserService";
import {request} from '@umijs/max';
import Apis from "@/typings/apis";
import MsgCodes from "@/typings/msgcodes";
import React from "react";
import * as Icons from '@ant-design/icons';
import {Commons} from "@/typings/commons";

class MenuService {
    /**
     * 单例实例对象
     * @private
     */
    private static INSTANCE: MenuService = new MenuService();

    /**
     * 获取实例对象
     */
    public static getInstance(): MenuService {
        return this.INSTANCE;
    }

    /**
     * 处理菜单ICON图标
     *
     * @param menus 菜单列表
     * @private
     */
    private handlerMenuIcon(menus: any[]): any[] {
        return menus.map(({icon, subMenus, ...item}) => ({
            ...item,
            // @ts-ignore
            icon: icon && React.createElement(Icons[icon]),
            children: subMenus && this.handlerMenuIcon(subMenus),
        }));
    }

    /**
     * 获取菜单列表
     */
    public async getMenuList(): Promise<Menu.MenuItem[]> {
        let token = UserService.getInstance().getToken();
        if (token === undefined) {
            return [];
        }
        const menuResult = await request<Menu.MenuResult>(Apis.URI_MENU_GET_USER_MENU_LIST, {method: "POST"});
        if (menuResult.code !== MsgCodes.SUCCESS) {
            return [];
        }
        return this.handlerMenuIcon(menuResult.data);
    }

    /**
     * 获取所有菜单列表
     */
    public async getAllMenuList(params: { current?: number; pageSize?: number; }, options?: {
        [key: string]: any
    }): Promise<Menu.MenuListItem[]> {
        let token = UserService.getInstance().getToken();
        if (token === undefined) {
            return [];
        }
        const menuResult = await request<Menu.AllMenuResult>(Apis.URI_MENU_GET_ALL_LIST, {
            method: "POST",
            data: {
                ...params,
            },
            ...(options || {}),
        });
        if (menuResult.code !== MsgCodes.SUCCESS) {
            return [];
        }
        return menuResult.data;
    }

    /**
     * 获取有权限的菜单ID
     */
    public async getMenuIds(userId: string): Promise<string[]> {
        const result = await request<Menu.MyMenuIdResult>(Apis.URI_MENU_GET_USER_MENU_IDS, {
            method: "POST",
            params: {userId: userId}
        });
        if (result.code !== MsgCodes.SUCCESS) {
            return [];
        }
        return result.data;
    }

    /**
     * 授权权限
     * @param param 参数
     */
    public async grantUser(param: { menuIds: string | undefined | null; userId: string | undefined }) {
        return await request<Commons.MsgObject>(Apis.URI_MENU_GRANT, {
            method: "POST",
            data: param
        });
    }
}

export default MenuService;
