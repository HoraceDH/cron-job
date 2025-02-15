import {ActionType, PageContainer, ProColumns, ProTable} from '@ant-design/pro-components';
import React, {useRef} from 'react';
import {Commons} from "@/typings/commons";
import {Tag} from "antd";
import {SchedulersBeans} from "@/typings/schedulers";
import SchedulersService from "@/services/SchedulersService";

const Index: React.FC = () => {
    const actionRef = useRef<ActionType>();

    const columns: ProColumns<SchedulersBeans.SchedulersItem>[] = [
        {
            title: '实例ID',
            dataIndex: 'id',
            width: "8%",
        },
        {
            title: '地址',
            dataIndex: 'address',
            width: "16%",
        },
        {
            title: '主机名',
            dataIndex: 'hostName',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '状态',
            dataIndex: 'state',
            initialValue: '-1',
            valueType: 'select',
            request: async () => {
                return SchedulersService.getInstance().getStateList();
            },
            render: (dom, entity) => {
                if (entity.state === 1) {
                    return <Tag color={"green"}>在线</Tag>
                } else {
                    return <Tag color={"red"}>离线</Tag>
                }
            }
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            valueType: 'text',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
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
            <ProTable<SchedulersBeans.SchedulersItem, Commons.PageParams>
                defaultSize="small"
                headerTitle="查询表格"
                actionRef={actionRef}
                search={{
                    labelWidth: 120,
                }}
                pagination={{pageSize: 15}}
                rowKey="id"
                request={SchedulersService.getInstance().getSchedulersList}
                columns={columns}

            />
        </PageContainer>
    );
};

export default Index;
