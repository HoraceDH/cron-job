import Footer from '@/components/Footer';
import {Question, SelectLang} from '@/components/RightContent';
import type {Settings as LayoutSettings} from '@ant-design/pro-components';
import {SettingDrawer} from '@ant-design/pro-components';
import type {RunTimeLayoutConfig} from '@umijs/max';
import {history, RequestConfig} from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import React, {useState} from 'react';
import {AvatarDropdown, AvatarName} from './components/RightContent/AvatarDropdown';
import {requestConfig} from "@/services/HttpInterceptor";
import GlobalState from './typings/commons';
import {Pages} from "@/typings/pages";
import UserService from "@/services/UserService";
import {Button, Input, message, Space} from "antd";
import MenuService from "@/services/MenuService";
import {DeleteFilled} from '@ant-design/icons';

/**
 * @name request 配置，可以配置错误处理
 * 它基于 axios 和 ahooks 的 useRequest 提供了一套统一的网络请求和错误处理方案。
 * @doc https://umijs.org/docs/max/request#配置
 */
export const request: RequestConfig = requestConfig;

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<GlobalState> {
    // 如果不是登录页面，执行
    if (history.location.pathname !== Pages.PAGE_URL_USER_LOGIN) {
        const user = await UserService.getInstance().getUser();
        if (user === undefined) {
            message.error("登录状态失效，请重新登录");
            document.location.href = Pages.PAGE_URL_USER_LOGIN;
            return {};
        }
        // 获取菜单列表
        const menus = await MenuService.getInstance().getMenuList();
        return {
            menus: menus,
            searchMenus: menus,
            currentUser: user,
            settings: defaultSettings as Partial<LayoutSettings>,
            pageState: new Map(),
        };
    }
    return {
        settings: defaultSettings as Partial<LayoutSettings>,
        pageState: new Map()
    };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
// @ts-ignore
export const layout: RunTimeLayoutConfig = ({initialState, setInitialState}) => {
    const [keywords, setKeywords] = useState<string>("");

    /**
     * 搜索菜单
     * @param menu 菜单对象
     * @param keywords 关键词
     */
    function searchMenuItem(menu: any, keywords: string) {
        const matchingMenu = {...menu};
        if (menu.name.includes(keywords)) {
            return matchingMenu;
        } else if (menu.children && menu.children.length > 0) {
            const matchingChildren = menu.children.map((child: any) => searchMenuItem(child, keywords)).filter(Boolean);
            if (matchingChildren.length > 0) {
                matchingMenu.children = matchingChildren;
            }
            if (matchingMenu.name.includes(keywords) || matchingChildren.length > 0) {
                return matchingMenu;
            }
        }
        return null;
    }

    /**
     * 搜索菜单
     * @param keywords 事件对象
     */
    function searchMenu(keywords: string) {
        const result: any[] = [];
        const menus = initialState?.menus;

        if (menus) {
            menus.forEach(item => {
                const matchMenu = searchMenuItem(item, keywords);
                if (matchMenu) {
                    result.push(matchMenu);
                }
            })
            // 更新最新菜单列表
            setInitialState({
                ...initialState,
                searchMenus: result,
            });
        }
    }

    /**
     * 清除搜索框数据
     */
    function cleanSearchInput() {
        setKeywords("");
        searchMenu("");
    }

    return {
        menuDataRender: () => initialState?.searchMenus,
        // actionsRender: () => [<Question key="doc"/>, <SelectLang key="SelectLang"/>],
        actionsRender: () => [<Question key="doc"/>],
        avatarProps: {
            src: initialState?.currentUser?.avatar,
            title: <AvatarName/>,
            render: (_, avatarChildren) => {
                return <AvatarDropdown>{avatarChildren}</AvatarDropdown>;
            },
        },
        waterMarkProps: {
            content: initialState?.currentUser?.username,
        },
        disableContentMargin: false,
        footerRender: () => <Footer/>,
        onPageChange: () => {
            const {location} = history;
            // 如果没有登录，重定向到 login
            if (!initialState?.currentUser && location.pathname !== Pages.PAGE_URL_USER_LOGIN) {
                history.push(Pages.PAGE_URL_USER_LOGIN);
            }
        },
        menuHeaderRender: () => {
            return (
                <Space.Compact style={{width: '100%'}}>
                    <Input value={keywords} placeholder={"搜索菜单..."} onChange={(e) => {
                        const keywords = e.target.value;
                        setKeywords(keywords);
                        searchMenu(keywords)
                    }}/>
                    <Button onClick={cleanSearchInput}><DeleteFilled/></Button>
                </Space.Compact>
            );
            // return <Input placeholder={"搜索菜单"} onChange={searchMenu}/>;
        },
        // 增加一个 loading 的状态
        childrenRender: (children) => {
            // if (initialState?.loading) return <PageLoading />;
            return (
                <>
                    {children}
                    <SettingDrawer
                        disableUrlParams
                        enableDarkTheme
                        settings={initialState?.settings}
                        onSettingChange={(settings) => {
                            setInitialState((preInitialState) => ({
                                ...preInitialState,
                                settings,
                            }));
                        }}
                    />
                </>
            );
        },
        ...initialState?.settings,
    };
};
