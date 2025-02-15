import {LockOutlined, UserOutlined,} from '@ant-design/icons';
import {ProConfigProvider, ProFormCheckbox, ProFormText,} from '@ant-design/pro-components';
import {FormattedMessage, useModel} from '@umijs/max';
import {message, Tabs} from 'antd';
import React, {useState} from 'react';
import {LoginFormPage} from "@ant-design/pro-form/lib";
import UserService from "@/services/UserService";
import MenuService from "@/services/MenuService";
import {Pages} from "@/typings/pages";
import MsgCodes from "@/typings/msgcodes";

const Login: React.FC = () => {
    const [type, setType] = useState<string>('account');
    const {setInitialState} = useModel('@@initialState');

    /**
     * 处理用户状态
     */
    const handlerUserState = async () => {
        const user = await UserService.getInstance().getUser();
        if (user !== undefined) {
            // 获取菜单列表
            const menus = await MenuService.getInstance().getMenuList();
            // 将用户信息和菜单列表更新到全局状态中
            await setInitialState((s) => ({
                ...s,
                menus: menus,
                currentUser: user,
            }));
            document.location.href = Pages.PAGE_ROOT;
        }
    }

    /**
     * 组件每渲染一次函数就调用一次，可以在这里做一些初始化工作
     */
    // useEffect(() => {
    //     handlerUserState().then();
    // }, []);

    /**
     * 处理登录表单提交事件
     * @param params 表单参数
     */
    const handleSubmit = async (params: UserBeans.LoginParams) => {
        try {
            // 账号密码登录
            params.type = "account";
            const loginResult = await UserService.getInstance().login(params);
            if (loginResult.code === MsgCodes.SUCCESS) {
                message.success("登录成功！");
                // 处理用户状态
                await handlerUserState();
            }
        } catch (error) {
            message.error("登录失败，请重试！");
        }
    };

    // 登录tab的items
    const loginTabItems = [{label: '账户密码登录', key: 'account',},];

    return (
        <div
            style={{
                backgroundColor: 'white',
                height: '100%',
            }}
        >
            <LoginFormPage
                backgroundVideoUrl={"/background.mp4"}
                logo={<img alt="logo" src="/logo.svg"/>}
                title="cron-job 任务调度平台"
                subTitle={"简单易用、超低延迟，支持权限管理和多租户的分布式任务调度平台"}
                initialValues={{
                    autoLogin: true,
                }}
                onFinish={async (values) => {
                    await handleSubmit(values as UserBeans.LoginParams);
                }}
            >
                {/*显示登录Tab*/}
                <Tabs activeKey={type} onChange={setType} items={loginTabItems} centered={true}/>
                {type === 'account' && (
                    <>
                        <ProFormText
                            name="username"
                            fieldProps={{
                                size: 'large',
                                prefix: <UserOutlined/>,
                            }}
                            placeholder={"请输入账号"}
                            rules={[
                                {
                                    required: true,
                                    message: (
                                        <FormattedMessage
                                            id="pages.login.username.required"
                                            defaultMessage="请输入用户名!"
                                        />
                                    ),
                                },
                            ]}
                        />
                        <ProFormText.Password
                            name="password"
                            fieldProps={{
                                size: 'large',
                                prefix: <LockOutlined/>,
                            }}
                            placeholder={"请输入密码"}
                            rules={[
                                {
                                    required: true,
                                    message: (
                                        <FormattedMessage
                                            id="pages.login.password.required"
                                            defaultMessage="请输入密码！"
                                        />
                                    ),
                                },
                            ]}
                        />
                    </>
                )}

                <div
                    style={{
                        marginBottom: 24,
                    }}
                >
                    <ProFormCheckbox noStyle name="autoLogin">
                        <FormattedMessage id="pages.login.rememberMe" defaultMessage="自动登录"/>
                    </ProFormCheckbox>
                    <a
                        style={{
                            float: 'right',
                        }}
                    >
                        <FormattedMessage id="pages.login.forgotPassword" defaultMessage="忘记密码"/>
                    </a>
                </div>
            </LoginFormPage>
        </div>
    );
};

export default () => {
    return (
        <ProConfigProvider>
            <Login/>
        </ProConfigProvider>
    );
};
