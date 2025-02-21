// @ts-ignore
import type {ItemType} from "antd/es/menu/hooks/useItems";
import {ActionType, PageContainer, ProColumns, ProTable} from '@ant-design/pro-components';
import React, {useRef} from 'react';
import {Commons} from "@/typings/commons";
import {Button, Tag} from "antd";
import {AppBeans} from "@/typings/app";
import AppService from "@/services/AppService";
import TenantService from "@/services/TenantService";

const Index: React.FC = () => {
    const actionRef = useRef<ActionType>();

    function renderOperationOptions(dom: React.ReactNode, entity: AppBeans.AppItem) {
        let option = null;
        if (entity.state === 1) {
            option = <Button danger size="small" type={"primary"} onClick={() => {
                AppService.getInstance().stopApp(entity.id);
                actionRef.current?.reload()
            }}>停止</Button>
        }
        if (entity.state === 2) {
            option = <Button size="small" type={"primary"} onClick={() => {
                AppService.getInstance().startApp(entity.id);
                actionRef.current?.reload()
            }}>启动</Button>
        }
        return (
            option
        );
    }

    const columns: ProColumns<AppBeans.AppItem>[] = [
        {
            title: '选择租户',
            dataIndex: 'tenantId',
            hideInTable: true,
            initialValue: '-1',
            valueType: 'select',
            fieldProps: {showSearch: true,},
            request: async () => {
                return TenantService.getInstance().getSearchList();
            }
        },
        {
            title: '应用ID',
            dataIndex: 'id',
            hideInTable: true,
            hideInSearch: true
        },
        {
            title: '租户编码',
            dataIndex: 'tenantItem.tenant',
            hideInSearch: true,
            render: (dom, entity) => {
                return entity.tenantItem.tenant;
            }
        },
        {
            title: '租户名称',
            dataIndex: 'tenantItem.name',
            hideInSearch: true,
            render: (dom, entity) => {
                return entity.tenantItem.name;
            }
        },
        {
            title: '应用名',
            dataIndex: 'appName',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '应用描述',
            dataIndex: 'appDesc',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '执行器数',
            dataIndex: 'executorCount',
            hideInSearch: true,
            render: (dom, entity) => {
                if (entity.executorCount > 0) {
                    return (<Tag color={"green"}>{entity.executorCount}</Tag>);
                } else {
                    return (<Tag color={"red"}>{entity.executorCount}</Tag>);
                }
            },
        },
        {
            title: '任务数',
            dataIndex: 'taskCount',
            hideInSearch: true,
            render: (dom, entity) => {
                return (<Tag color={"green"}>{entity.taskCount}</Tag>);
            },
        },
        {
            title: '状态',
            dataIndex: 'state',
            initialValue: '1',
            valueType: 'select',
            request: async () => {
                return AppService.getInstance().getStateList();
            },
            render: (dom, entity) => {
                if (entity.state === 1) {
                    return <Tag color={"green"}>运行</Tag>
                } else {
                    return <Tag color={"red"}>停止</Tag>
                }
            }
        },
        {
            title: '修改时间',
            dataIndex: 'modifyTime',
            valueType: 'text',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: "操作",
            tooltip: "停止应用将会停止该应用下的所有任务，调度中的任务会被取消",
            hideInSearch: true,
            render: renderOperationOptions,
        }
    ];

    return (
        <PageContainer>
            <ProTable<AppBeans.AppItem, Commons.PageParams>
                defaultSize="small"
                headerTitle="查询表格"
                actionRef={actionRef}
                search={{
                    labelWidth: 120,
                }}
                pagination={{pageSize: 15}}
                rowKey="id"
                request={AppService.getInstance().getAppList}
                columns={columns}

            />
        </PageContainer>
    );
};

export default Index;
