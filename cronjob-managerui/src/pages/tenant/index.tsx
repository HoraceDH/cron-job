import {ActionType, PageContainer, ProColumns, ProForm, ProTable} from '@ant-design/pro-components';
import React, {useEffect, useRef, useState} from 'react';
import {TenantBeans} from "@/typings/tenant";
import {Commons} from "@/typings/commons";
import TenantService from "@/services/TenantService";
import {Button, Tag} from "antd";
import {Link} from "umi";
import {ModalForm, ProFormInstance, ProFormSelect} from "@ant-design/pro-form/lib";
import AlarmService from "@/services/AlarmService";

const Index: React.FC = () => {
    const actionRef = useRef<ActionType>();
    const [formVisibility, setFormVisibility] = useState<boolean>(false);
    const [alarmChannelSearchList, updateAlarmChannelSearchList] = useState<Commons.SearchItem[]>();
    const [groupSearchList, updateGroupSearchList] = useState<Commons.SearchItem[]>();
    const createFormRef = useRef<ProFormInstance<AlarmBeans.AlarmConfig>>();

    /**
     * 加载数据
     */
    async function fetchData() {
        // 加载告警渠道列表
        let alarmChannelList = await AlarmService.getInstance().getSearchList();
        updateAlarmChannelSearchList(alarmChannelList);
    }

    useEffect(() => {
        fetchData().then();
    }, []);

    /**
     * 渲染操作按钮
     *
     * @param dom dom节点
     * @param entity 当前行数据实体
     */
    function renderOperationOptions(dom: React.ReactNode, entity: TenantBeans.TenantItem) {
        return (
            <Button size="small" type={"primary"} onClick={() => {
                setFormVisibility(true);
            }}>
                配置告警
            </Button>
        );
    }

    /**
     * 告警渠道变更时
     *
     * @param channel 渠道值
     */
    async function onAlarmChannelChange(channel: string) {
        let groupList = await AlarmService.getInstance().getGroupList(channel);
        updateGroupSearchList(groupList);
    }

    /**
     * 告警渠道变更时
     *
     * @param channel 渠道值
     */
    async function tryAlarm() {
        createFormRef.current?.validateFields().then((values) => {
            // 发送一个告警信息
            AlarmService.getInstance().sendAlarm(values);
        });
    }

    /**
     * 更新告警配置
     * @param alarmConfig 用户对象
     */
    const updateAlarmConfig = async (alarmConfig: AlarmBeans.AlarmConfig) => {
        return false;
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
            {/*告警配置的表单*/}
            <ModalForm<AlarmBeans.AlarmConfig>
                title="告警配置"
                open={formVisibility}
                formRef={createFormRef}
                autoFocusFirstInput
                modalProps={{
                    destroyOnClose: true,
                    onCancel: () => setFormVisibility(false),
                }}
                submitTimeout={5000}
                onFinish={updateAlarmConfig}
            >
                <br/>

                <ProForm.Group>
                    <ProFormSelect name={"type"} options={alarmChannelSearchList} label={"告警渠道"} width={350}
                                   rules={[{required: true}]}
                                   tooltip={"如无可选告警渠道，请先在 application.properties 中配置。"}
                                   onChange={onAlarmChannelChange}/>
                    <ProFormSelect name={"chatId"} options={groupSearchList} label={"告警群组"} width={350}
                                   rules={[{required: true}]}
                                   tooltip={"支持查询最近创建的群，需要先邀请机器人进群，才能被检索到。"}/>
                </ProForm.Group>
                <Button type={"primary"} danger onClick={tryAlarm}>试一下</Button>
            </ModalForm>
            {/*告警配置的表单*/}

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
