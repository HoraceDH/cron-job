import {ActionType, PageContainer, ProColumns, ProForm, ProFormText, ProTable,} from '@ant-design/pro-components';
import React, {useRef, useState} from 'react';
import {Button, Dropdown, message, Space, Tag} from "antd";
import UserService from "@/services/UserService";
import MsgCodes from "@/typings/msgcodes";
import {DownOutlined, PlusOutlined} from "@ant-design/icons";
import {Commons} from '@/typings/commons';
// @ts-ignore
import type {ItemType} from "antd/es/menu/hooks/useItems";
import {Link} from "umi";
import {Pages} from "@/typings/pages";
import {ModalForm, ProFormInstance} from "@ant-design/pro-form/lib";
import {Md5} from "ts-md5";

/**
 * 用户管理列表
 *
 * @constructor
 */
const Index: React.FC = () => {
    const createFormRef = useRef<ProFormInstance<UserBeans.User>>();
    const actionRef = useRef<ActionType>();
    const [currentRow, updateCurrentRow] = useState<UserBeans.User>();
    const [createFormVisibility, setCreateFormVisibility] = useState<boolean>(false);

    /**
     * 重置用户密码
     */
    async function resetPassword() {
        if (currentRow?.id) {
            const result = await UserService.getInstance().resetPassword(currentRow.id);
            if (result.code === MsgCodes.SUCCESS) {
                message.success("密码重置成功：" + result.data, 3);
            }
        }
    }

    /**
     * 更新用户状态
     * @param state 用户状态，0：启用，1：禁用
     */
    async function updateUserState(state: number) {
        if (currentRow?.id) {
            const result = await UserService.getInstance().updateUserState(currentRow.id, state);
            if (result.code === MsgCodes.SUCCESS) {
                let msg = "用户启用成功！";
                if (state === 1) {
                    msg = "用户禁用成功！";
                }
                message.success(msg);
                actionRef.current?.reload();
            }
        }
    }

    // 基础操作按钮
    const baseOperateItems: ItemType[] = [
        {
            key: '1',
            label: <Link to={`${Pages.PAGE_USER_SET_PERMISSION}?userId=${currentRow?.id}`}><Button size="small"
                                                                                                   type={"primary"}>授权菜单</Button></Link>,
        },
        {
            key: '2',
            label: <Link to={`${Pages.PAGE_USER_SET_TENANT}?userId=${currentRow?.id}`}><Button size="small"
                                                                                               type={"primary"}>授权租户</Button></Link>,
        },
        {
            key: '3',
            label: <Button size="small" type={"primary"} danger onClick={resetPassword}>重置密码</Button>,
        },
    ];

    /**
     * 渲染操作按钮
     * @param dom dom节点
     * @param entity 实体
     */
    function renderOperationOptions(dom: React.ReactNode, entity: UserBeans.User) {
        let items: ItemType[] = [];
        items.push(...baseOperateItems);
        if (entity.state === 0) {
            items.push({
                key: '5',
                label: <Button size="small" type={"primary"} danger onClick={async () => {
                    updateUserState(1).then(r => {
                    });
                }}>禁用用户</Button>,
            },);
        } else if (entity.state === 1) {
            items.push({
                key: '4',
                label: <Button size="small" type={"primary"} onClick={() => {
                    updateUserState(0).then(r => {
                    });
                }}>启用用户</Button>,
            },);
        }
        return (
            <Dropdown menu={{items}} trigger={["click"]}>
                <a onClick={(e) => {
                    e.preventDefault();
                    updateCurrentRow(entity);
                }}>
                    <Space>
                        操作
                        <DownOutlined/>
                    </Space>
                </a>
            </Dropdown>
        );
    }

    /**
     * 表格列
     */
    const columns: ProColumns<UserBeans.User>[] = [
        {
            title: '头像',
            dataIndex: 'avatar',
            valueType: 'text',
            hideInSearch: true,
            render: (dom, entity) => {
                return (
                    <img src={entity.avatar} width={35} alt={"加载失败"}/>
                );
            },
        },
        {
            title: '用户ID',
            hideInSearch: true,
            hideInTable: true,
            width: "10%",
            dataIndex: 'id',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '账号',
            dataIndex: 'username',
            valueType: 'text',
        },
        {
            title: '昵称',
            dataIndex: 'nickname',
            valueType: 'text',
        },
        {
            title: '状态',
            width: "5%",
            dataIndex: 'state',
            fieldProps: {showSearch: true},
            initialValue: "-1",
            valueType: 'select',
            request: async () => {
                return UserService.getInstance().getUserStateList();
            },
            render: (dom, entity) => {
                return (
                    entity.state === 1 ? <Tag color={"red"}>禁用</Tag> : <Tag color={"green"}>正常</Tag>
                );
            },
        },
        {
            title: '邮箱',
            dataIndex: 'email',
            width: '20%',
            valueType: 'text',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '手机号',
            dataIndex: 'phone',
            valueType: 'text',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '地址',
            dataIndex: 'address',
            valueType: 'text',
            hideInSearch: true,
            hideInTable: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '签名',
            dataIndex: 'signature',
            valueType: 'text',
            hideInSearch: true,
            hideInTable: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '注册时间',
            dataIndex: 'createTime',
            valueType: 'text',
            width: "20%",
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: "操作",
            width: "9%",
            hideInSearch: true,
            render: renderOperationOptions,
        }
    ];

    /**
     * 随机生成密码
     */
    const randomPassword = () => {
        let password = Math.random().toString(36).slice(-10);
        createFormRef.current?.setFieldValue("password", password);
        message.success("生成随机密码成功！").then(() => {
        });
    }

    /**
     * 创建用户
     * @param user 用户对象
     */
    const createUser = async (user: UserBeans.User) => {
        user.password = Md5.hashStr(user.password === undefined ? "" : user.password);
        let createUserResult = await UserService.getInstance().createUser(user);
        if (createUserResult.code === MsgCodes.SUCCESS) {
            message.success("用户【" + user.username + "】创建成功");
            actionRef.current?.reload();
            setCreateFormVisibility(false);
            return true;
        }
        return false;
    }

    return (
        <PageContainer>
            {/*创建用户的表单*/}
            <ModalForm<UserBeans.User>
                title="创建用户"
                open={createFormVisibility}
                formRef={createFormRef}
                autoFocusFirstInput
                modalProps={{
                    destroyOnClose: true,
                    onCancel: () => setCreateFormVisibility(false),
                }}
                submitTimeout={5000}
                onFinish={createUser}
            >
                <ProForm.Group>
                    <ProFormText
                        name="username"
                        label="用户名"
                        width="md"
                        tooltip="登录账号，全局唯一"
                        placeholder="用户名"
                        rules={[{required: true}]}
                    />
                    <ProFormText
                        name="nickname"
                        label="昵称"
                        width="md"
                        initialValue={"Horace"}
                        tooltip="昵称，全局唯一"
                        placeholder="昵称"
                        rules={[{required: true}]}
                    />
                </ProForm.Group>
                <ProForm.Group>
                    <Space.Compact>
                        <div style={{maxWidth: 263}}>
                            <ProFormText.Password
                                name="password"
                                label="密码"
                                width={"md"}
                                placeholder="密码"
                                rules={[{required: true}]}
                            />
                        </div>

                        <Button type={"primary"} style={{marginTop: 30}} onClick={randomPassword}>随机</Button>
                    </Space.Compact>
                    <ProFormText
                        name="phone"
                        label="手机号"
                        width="md"
                        placeholder="手机号"
                        initialValue={"13588888888"}
                        rules={[{required: true}, {pattern: /^1\d{10}$/, message: "请输入正确的手机号"}]}
                    />
                </ProForm.Group>
                <ProForm.Group>
                    <ProFormText
                        name="email"
                        label="邮箱"
                        width="md"
                        placeholder="邮箱"
                        initialValue={"xxxxxx@163.com"}
                        rules={[{required: true, type: "email"}]}
                    />
                    <ProFormText
                        name="address"
                        label="地址"
                        width="md"
                        placeholder="地址"
                        initialValue={"广州市天河区"}
                    />
                </ProForm.Group>
                <ProFormText
                    name="signature"
                    label="个性签名"
                    placeholder="个性签名"
                    initialValue={"路虽远，行则将至；事虽难，做则必成！"}
                />
            </ModalForm>
            {/*创建用户的表单*/}

            <ProTable<UserBeans.User, Commons.PageParams>
                defaultSize="small"
                headerTitle="查询表格"
                actionRef={actionRef}
                rowKey="id"
                search={{
                    labelWidth: 120,
                }}
                pagination={{pageSize: 15}}
                toolBarRender={() => [
                    <Button
                        type="primary"
                        key="primary"
                        onClick={() => {
                            setCreateFormVisibility(true)
                        }}
                    >
                        <PlusOutlined/> 创建用户
                    </Button>,
                ]}
                // toolBarRender={() => [
                //   <Link to={Pages.PAGE_USER_CREATE}>
                //     <Button
                //         type="primary"
                //         key="primary"
                //         onClick={() => {
                //           setCreateFormVisibility(true)
                //         }}
                //     >
                //       <PlusOutlined/> 创建用户
                //     </Button>,
                //   </Link>
                // ]}
                request={UserService.getInstance().getUserList}
                columns={columns}
            />
        </PageContainer>
    );
};

export default Index;

