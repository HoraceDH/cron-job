import {ActionType, PageContainer, ProColumns, ProTable} from '@ant-design/pro-components';
import React, {useRef} from 'react';
import {TenantBeans} from "@/typings/tenant";
import {Commons} from "@/typings/commons";
import TenantService from "@/services/TenantService";
import {Tag} from "antd";
import {Link} from "umi";

const Index: React.FC = () => {
    const actionRef = useRef<ActionType>();

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
            title: '创建时间',
            dataIndex: 'createTime',
            valueType: 'text',
            hideInSearch: true,
            width: 250,
        },
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
