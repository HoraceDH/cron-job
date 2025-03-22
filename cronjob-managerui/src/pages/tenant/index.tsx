import {ActionType, PageContainer, ProColumns, ProTable} from '@ant-design/pro-components';
import React, {useRef} from 'react';
import {TenantBeans} from "@/typings/tenant";
import {Commons} from "@/typings/commons";
import TenantService from "@/services/TenantService";
import {Button, Tag} from "antd";
import {Link} from "umi";

const Index: React.FC = () => {
    const actionRef = useRef<ActionType>();

    /**
     * 渲染操作按钮
     *
     * @param dom dom节点
     * @param entity 当前行数据实体
     */
    function renderOperationOptions(dom: React.ReactNode, entity: TenantBeans.TenantItem) {
        return (
            <Button type={"primary"} size={"small"}>
                <Link to={`/system/tenant/edit?id=${entity.key}`}>编辑</Link>
            </Button>
        );
    }

    const columns: ProColumns<TenantBeans.TenantItem>[] = [
        {
            title: '租户ID',
            dataIndex: 'key',
            width: "10%",
            hideInSearch: true
        },
        {
            title: '租户编码',
            dataIndex: 'tenant',
        },
        {
            title: '租户名称',
            dataIndex: 'name',
        },
        {
            title: '租户描述',
            hideInSearch: true,
            dataIndex: 'remark',
        },
        {
            title: '应用数',
            dataIndex: 'appCount',
            hideInSearch: true,
            render: (dom, entity) => {
                return (
                    <Link to={"/app/manager"}>
                        <Tag color={"green"}>{entity.appCount}</Tag>
                    </Link>
                );
            },
        },
        {
            title: '告警类型',
            hideInSearch: true,
            dataIndex: 'type',
            valueEnum: {
                0: <Tag color={"volcano"}>未设置告警</Tag>,
                1: <Tag color={"volcano"}>飞书告警</Tag>,
                2: <Tag color={"volcano"}>Lark告警</Tag>,
                3: <Tag color={"volcano"}>企微告警</Tag>,
            },
        },
        {
            title: '告警群名称',
            hideInSearch: true,
            dataIndex: 'groupName',
            render: (dom: React.ReactNode, entity: TenantBeans.TenantItem) => {
                return <Tag color={"orange"}>{entity.groupName}</Tag>;
            }
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            valueType: 'text',
            hideInSearch: true,
            width: 250,
        },
        {
            title: "操作",
            width: "9%",
            hideInSearch: true,
            render: renderOperationOptions,
        }
    ];

    return (
        <PageContainer>
            <ProTable<TenantBeans.TenantItem, Commons.PageParams>
                defaultSize="small"
                headerTitle="查询表格"
                actionRef={actionRef}
                search={{
                    labelWidth: 120,
                }}
                pagination={{pageSize: 15}}
                rowKey="key"
                request={TenantService.getInstance().getAllTenantList}
                columns={columns}
            />
        </PageContainer>
    );
};

export default Index;
