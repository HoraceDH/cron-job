import {ActionType, PageContainer, ProColumns, ProTable} from '@ant-design/pro-components';
import React, {useEffect, useRef, useState} from 'react';
import {Commons} from "@/typings/commons";
import {Button, Tag} from "antd";
import TenantService from "@/services/TenantService";
import {Link} from "umi";
// @ts-ignore
import type {ItemType} from "antd/es/menu/hooks/useItems";
import {TaskLogBeans} from "@/typings/tasklog";
import TaskLogService from "@/services/TaskLogService";
import {Pages} from "@/typings/pages";
import AppService from "@/services/AppService";
import TaskService from "@/services/TaskService";
import {ProFormInstance} from "@ant-design/pro-form/lib";
import dayjs from "dayjs";
import ParamsUtils from "@/utils/ParamsUtils";


const Index: React.FC = () => {
    const actionRef = useRef<ActionType>();
    const tableFormRef = useRef<ProFormInstance>();
    const [item, updateItem] = useState<TaskLogBeans.TaskLogItem>();
    const [taskItems, updateTaskItems] = useState<Map<string, string>>();
    const [appItems, updateAppItems] = useState<Map<string, string>>();
    const [firstQuery, updateFirstQuery] = useState<boolean>(true);

    /**
     * 加载应用列表
     */
    const fetchAppItems = async (tenantId: string) => {
        const appItems = await AppService.getInstance().getSearchListEnum(tenantId)
        updateAppItems(appItems);
        tableFormRef.current?.setFieldValue("appName", "");
        tableFormRef.current?.setFieldValue("taskId", "");
    }

    const fetchTaskItems = async (tenantId: any, appName: any) => {
        let taskService = TaskService.getInstance();
        // @ts-ignore
        const itemMap = await taskService.getSearchListValueEnum(tenantId, appName);
        updateTaskItems(itemMap);
        tableFormRef.current?.setFieldValue("taskId", "");
    }

    useEffect(() => {
        fetchAppItems("").then(() => {
            fetchTaskItems("", "").then(() => {
                let params = ParamsUtils.getQueryParams();
                if (params.taskId !== undefined && params.state !== undefined) {
                    tableFormRef.current?.setFieldValue("appName", params.appName);
                    tableFormRef.current?.setFieldValue("taskId", params.taskId);
                    tableFormRef.current?.setFieldValue("state", params.state);
                    //tableFormRef.current?.submit();
                }
            });
        });
    }, []);

    // 操作按钮
    const baseOperateItems: ItemType[] = [
        {
            label: <Button size="small" type={"primary"}><Link
                to={`${Pages.PAGE_TASKLOG_DETAILS}?id=${item?.id}`}>查看详情</Link></Button>,
            key: '1',
        },
    ];

    /**
     * 渲染操作按钮
     *
     * @param dom dom节点
     * @param entity 当前行数据实体
     */
    function renderOperationOptions(dom: React.ReactNode, entity: TaskLogBeans.TaskLogItem) {
        return (
            <Button size="small" type={"primary"}>
                <Link to={`${Pages.PAGE_TASKLOG_DETAILS}?id=${entity.id}`}>查看详情</Link>
            </Button>
        );
    }

    // @ts-ignore
    /**
     * 表格列
     */
    const columns: ProColumns<TaskLogBeans.TaskLogItem>[] = [
        {
            title: 'ID',
            dataIndex: 'id',
            // hideInSearch: true,
            // hideInTable: true,
            width: "185px",
            render: (dom, entity) => {
                return <Link to={`${Pages.PAGE_TASKLOG_DETAILS}?id=${entity.id}`}>{entity.id}</Link>
            }
        },
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
                    fetchTaskItems(tenantId, "")
                }
            },
            request: async () => {
                return TenantService.getInstance().getSearchList();
            }
        },
        {
            title: '应用名',
            dataIndex: 'appName',
            hideInTable: true,
            initialValue: '',
            valueType: 'select',
            valueEnum: appItems,
            fieldProps: {
                showSearch: true,
                onChange: async (appName) => {
                    const tenantId = tableFormRef.current?.getFieldValue("tenantId")
                    fetchTaskItems(tenantId, appName);
                }
            }
        },
        {
            title: '任务名',
            dataIndex: 'taskId',
            hideInTable: true,
            initialValue: '',
            valueType: 'select',
            fieldProps: {showSearch: true},
            valueEnum: taskItems,
        },
        {
            title: '执行状态',
            dataIndex: 'state',
            initialValue: '-1',
            valueType: 'select',
            fieldProps: {
                showSearch: true
            },
            request: async () => {
                return TaskLogService.getInstance().getTaskLogStateList();
            },
            hideInTable: true,
        },
        {
            title: '租户编码',
            dataIndex: 'tenant',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '应用名',
            dataIndex: 'appName',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '任务名',
            dataIndex: 'taskName',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '负责人',
            dataIndex: 'owner',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '执行器地址',
            dataIndex: 'executorAddress',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '执行器主机名',
            dataIndex: 'executorHostName',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '延迟时间',
            dataIndex: 'delayTime',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
            render: (dom, entity) => {
                if (entity.delayTime !== null) {
                    return entity.delayTime + " ms";
                } else {
                    return "-";
                }
            }
        },
        {
            title: '执行耗时',
            dataIndex: 'elapsedTime',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
            render: (dom, entity) => {
                if (entity.elapsedTime !== null) {
                    return entity.elapsedTime + " ms";
                } else {
                    return "-";
                }
            }
        },
        {
            title: 'Cron表达式',
            dataIndex: 'cron',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '执行状态',
            dataIndex: 'state',
            hideInSearch: true,
            width: "80px",
            render: (dom, entity) => {
                switch (entity.state) {
                    case 1:
                        return <Tag color={"seagreen"}>初始化</Tag>;
                    case 2:
                        return <Tag color={"coral"}>队列中</Tag>;
                    case 3:
                        return <Tag color={"darkolivegreen"}>调度中</Tag>;
                    case 4:
                        return <Tag color={"geekblue-inverse"}>执行成功</Tag>;
                    case 5:
                        return <Tag color={"maroon"}>执行失败</Tag>;
                    case 6:
                        return <Tag color={"deeppink"}>取消执行</Tag>;
                    case 7:
                        return <Tag color={"steelblue"}>任务过期</Tag>;
                    case 8:
                        return <Tag color={"chocolate"}>失败已丢弃</Tag>;
                    case 9:
                        return <Tag color={"gold-inverse"}>执行失败，重试中</Tag>;
                    case 10:
                        return <Tag color={"gold-inverse"}>执行失败</Tag>;
                    default:
                        return <Tag color={"red-inverse"}>未知状态</Tag>;

                }
            }
        },
        {
            title: '执行器标签',
            dataIndex: 'tag',
            hideInSearch: true,
            width: "90px",
            render: (tag) => {
                if (tag === "" || tag === undefined || tag === null || tag === "-") {
                    return tag;
                }
                return <Tag color={"blue"}>{tag}</Tag>
            }
        },
        {
            title: '执行器地址',
            dataIndex: 'executorAddress',
            hideInSearch: true,
            hideInTable: true,
        },
        {
            title: '调度器地址',
            dataIndex: 'schedulersAddress',
            hideInSearch: true,
            hideInTable: true,
        },
        {
            title: '预计执行时间',
            dataIndex: 'executionTime',
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
            render: (dom, entity) => {
                if (entity.executionTime !== null) {
                    return dayjs(entity.executionTime).format("YYYY-MM-DD HH:mm:ss");
                } else {
                    return "-";
                }
            }
        },
        {
            title: '执行方法',
            dataIndex: 'method',
            hideInTable: true,
        },
        {
            title: '执行时间',
            dataIndex: 'executionTimeRange',
            valueType: "dateTimeRange",
            initialValue: [dayjs().add(-30, 'minute'), dayjs().add(10, 'minute')],
            fieldProps: {
                presets: [
                    {label: "最近半小时", value: [dayjs().add(-30, 'minute'), dayjs().add(10, 'minute')]},
                    {label: "最近1小时", value: [dayjs().add(-1, 'hour'), dayjs().add(10, 'minute')]},
                    {label: "最近2小时", value: [dayjs().add(-2, 'hour'), dayjs().add(10, 'minute')]},
                    {label: "最近3小时", value: [dayjs().add(-170, 'minute'), dayjs().add(10, 'minute')]},
                ]
            },
            colSize: 2,
            hideInTable: true,
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
            <ProTable<TaskLogBeans.TaskLogItem, Commons.PageParams>
                defaultSize="small"
                headerTitle="查询表格"
                tooltip={"双击某一条记录，可直接进入详情页"}
                actionRef={actionRef}
                search={{
                    labelWidth: 'auto',
                    defaultCollapsed: false,
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
                formRef={tableFormRef}
                rowKey="id"
                request={
                    async (params: any) => {
                        // 如果是首次通过路径的查询参数进来
                        let queryParams = ParamsUtils.getQueryParams();
                        if (firstQuery && queryParams.taskId !== undefined && queryParams.state !== undefined) {
                            updateFirstQuery(false);
                            params.taskId = queryParams.taskId;
                            params.state = queryParams.state;
                            params.appName = queryParams.appName;
                            return TaskLogService.getInstance().getTaskLogList(params);
                        }
                        return TaskLogService.getInstance().getTaskLogList(params);
                    }
                }
                columns={columns}
                onRow={(row) => {
                    return {
                        // 双击进入详情页
                        onDoubleClick: (event) => {
                            location.href = Pages.PAGE_TASKLOG_DETAILS + "?id=" + row.id;
                        }
                    }
                }}
            />
        </PageContainer>
    );
};

export default Index;
