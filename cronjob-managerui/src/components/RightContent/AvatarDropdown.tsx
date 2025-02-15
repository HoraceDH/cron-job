import {LogoutOutlined, SettingOutlined, UserOutlined} from '@ant-design/icons';
import {useEmotionCss} from '@ant-design/use-emotion-css';
import {history, useModel} from '@umijs/max';
import {Spin} from 'antd';
import type {MenuInfo} from 'rc-menu/lib/interface';
import React, {useCallback} from 'react';
import HeaderDropdown from '../HeaderDropdown';
import UserService from "@/services/UserService";
import {Pages} from "@/typings/pages";

export type GlobalHeaderRightProps = {
    menu?: boolean;
    children?: React.ReactNode;
};

export const AvatarName = () => {
    const {initialState} = useModel('@@initialState');
    const {currentUser} = initialState || {};
    return <span className="anticon" style={{fontWeight: "bold"}}>{currentUser?.username}</span>;
};

export const AvatarDropdown: React.FC<GlobalHeaderRightProps> = ({menu, children}) => {
    const actionClassName = useEmotionCss(({token}) => {
        return {
            display: 'flex',
            height: '48px',
            marginLeft: 'auto',
            overflow: 'hidden',
            alignItems: 'center',
            padding: '0 8px',
            cursor: 'pointer',
            borderRadius: token.borderRadius,
            '&:hover': {
                backgroundColor: token.colorBgTextHover,
            },
        };
    });
    const {initialState, setInitialState} = useModel('@@initialState');

    const onMenuClick = useCallback(
        (event: MenuInfo) => {
            const {key} = event;
            if (key === "logout") {
                UserService.getInstance().logout().then(() => {
                });
                setInitialState((s) => ({...s, currentUser: undefined}));
                return;
            }
            history.push(Pages.PAGE_URL_USER_LOGIN);
        },
        [setInitialState],
    );

    const loading = (
        <span className={actionClassName}>
      <Spin
          size="small"
          style={{
              marginLeft: 8,
              marginRight: 8,
          }}
      />
    </span>
    );

    if (!initialState) {
        return loading;
    }

    const {currentUser} = initialState;

    if (!currentUser || !currentUser.nickname) {
        return loading;
    }

    const menuItems = [
        ...(menu
            ? [
                {
                    key: 'center',
                    icon: <UserOutlined/>,
                    label: '个人中心',
                },
                {
                    key: 'settings',
                    icon: <SettingOutlined/>,
                    label: '个人设置',
                },
                {
                    type: 'divider' as const,
                },
            ]
            : []),
        {
            key: 'logout',
            icon: <LogoutOutlined/>,
            label: '退出登录',
        },
    ];

    return (
        <HeaderDropdown
            trigger={["click"]}
            menu={{
                selectedKeys: [],
                onClick: onMenuClick,
                items: menuItems,
            }}
        >
            {children}
        </HeaderDropdown>
    );
};
