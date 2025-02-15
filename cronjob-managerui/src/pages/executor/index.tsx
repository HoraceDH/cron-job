import {ActionType, PageContainer, ProColumns, ProTable} from '@ant-design/pro-components';
import React, {useEffect, useRef, useState} from 'react';
import {Commons} from "@/typings/commons";
import {Button, Tag} from "antd";
import TenantService from "@/services/TenantService";
import {ExecutorBeans} from "@/typings/executor";
import ExecutorService from "@/services/ExecutorService";
import AppService from "@/services/AppService";
import {ProFormInstance} from "@ant-design/pro-form/lib";

const Index: React.FC = () => {
    const actionRef = useRef<ActionType>();
    const [appItems, updateAppItems] = useState<Map<string, string>>();
    const tableFormRef = useRef<ProFormInstance>();

    /**
     * 加载应用列表
     */
    const fetchAppItems = async (tenantId: string) => {
        tableFormRef.current?.setFieldValue("appName", "");
        const appItems = await AppService.getInstance().getSearchListEnum(tenantId)
        updateAppItems(appItems);
    }

    useEffect(() => {
        fetchAppItems("");
    }, []);

    const stateSearchItems = {
        "-1": "全部",
        "1": "在线",
        "2": "离线",
    }

    const columns: ProColumns<ExecutorBeans.ExecutorItem>[] = [
        {
            title: '选择租户',
            dataIndex: 'tenantId',
            hideInTable: true,
            initialValue: '-1',
            valueType: 'select',
            fieldProps: {
                showSearch: true,
                onChange: async (tenantId) => {
                    // @ts-ignore
                    fetchAppItems(tenantId);
                }
            },
            request: async () => {
                return TenantService.getInstance().getSearchList();
            }
        },
        {
            title: '执行器地址',
            dataIndex: 'address',
            width: "16%",
            hideInSearch: true,
        },
        {
            title: '主机名',
            dataIndex: 'hostName',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '租户编码',
            dataIndex: 'tenant',
            hideInSearch: true,
        },
        {
            title: '应用名',
            dataIndex: 'appName',
            hideInTable: true,
            initialValue: '',
            valueType: 'select',
            fieldProps: {showSearch: true},
            valueEnum: appItems,
        },
        {
            title: '应用名',
            dataIndex: 'appName',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '应用描述',
            dataIndex: 'appDesc',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '状态',
            dataIndex: 'state',
            initialValue: '1',
            valueType: 'select',
            valueEnum: stateSearchItems,
            hideInTable: true,
        },
        {
            title: '标签',
            dataIndex: 'tag',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '状态',
            dataIndex: 'state',
            hideInSearch: true,
            render: (dom, entity) => {
                if (entity.state === 1) {
                    return <Tag color={"green"}>在线</Tag>
                } else {
                    return <Tag color={"red"}>离线</Tag>
                }
            }
        },
        {
            title: 'SDK版本号',
            dataIndex: 'version',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            valueType: 'text',
            hideInSearch: true,
            hideInTable: true,
        },
        {
            title: '修改时间',
            dataIndex: 'modifyTime',
            valueType: 'text',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
    ];

    return (
        <PageContainer>
            <ProTable<ExecutorBeans.ExecutorItem, Commons.PageParams>
                defaultSize="small"
                headerTitle="查询表格"
                actionRef={actionRef}
                formRef={tableFormRef}
                search={{
                    labelWidth: 120,
                    defaultCollapsed: true,
                    optionRender: ({searchText, resetText}, {form}) => [
                        <Button
                            key="reset"
                            onClick={() => {
                                form?.resetFields(); // 重置表单
                                fetchAppItems("")
                            }}
                        >
                            {resetText}
                        </Button>,
                        <Button
                            type="primary"
                            key="search"
                            onClick={() => {
                                form?.submit();
                            }}
                        >
                            {searchText}
                        </Button>,
                    ],
                }}
                pagination={{pageSize: 15}}
                rowKey="id"
                request={ExecutorService.getInstance().getExecutorList}
                columns={columns}

            />
        </PageContainer>
    );
};

export default Index;
