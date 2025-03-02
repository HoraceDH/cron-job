import {ActionType, PageContainer, ProColumns, ProTable} from '@ant-design/pro-components';
import React, {useEffect, useRef, useState} from 'react';
import {Commons} from "@/typings/commons";
import {Button, message, Tag} from "antd";
import TenantService from "@/services/TenantService";
import {TaskBeans} from "@/typings/task";
import TaskService from "@/services/TaskService";
import {Link} from "umi";
// @ts-ignore
import type {ItemType} from "antd/es/menu/hooks/useItems";
import AppService from "@/services/AppService";
import {ProFormInstance} from "@ant-design/pro-form/lib";
import {Pages} from "@/typings/pages";
import {Modal} from 'antd/lib';
import TextArea from "antd/lib/input/TextArea";


const Index: React.FC = () => {
    const actionRef = useRef<ActionType>();
    const [item, updateItem] = useState<TaskBeans.TaskItem>();
    const [appItems, updateAppItems] = useState<Map<string, string>>();
    const tableFormRef = useRef<ProFormInstance>();
    const [showInputParamsDialog, updateShowInputParamsDialog] = useState<boolean>(false);
    const [currId, updateCurrId] = useState<string>();
    const currParamsRef = useRef<any>()

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

    /**
     * 启动任务
     */
    const startTask = async (id?: string) => {
        if (id == undefined) {
            id = item?.id;
        }
        const success = await TaskService.getInstance().updateRunState(id, 1);
        if (success) {
            message.success("任务启动成功！");
            actionRef.current?.reload();
        }
    }

    /**
     * 跳转到任务日志列表
     */
    const gotoTaskLogList = async (id?: string) => {
        if (id == undefined) {
            id = item?.id;
        }
        document.location.href = Pages.PAGE_TASKLOG_INDEX + "?taskId=" + id + "&state=4";
    }

    /**
     * 停止任务
     */
    const stopTask = async (id?: string) => {
        if (id == undefined) {
            id = item?.id;
        }
        const success = await TaskService.getInstance().updateRunState(id, 2);
        if (success) {
            message.success("任务停止成功！");
            actionRef.current?.reload();
        }
    }

    /**
     * 立即执行一次任务
     */
    const executeTask = async () => {
        const params = currParamsRef.current.resizableTextArea?.textArea.value
        const logId = await TaskService.getInstance().executeTask(currId, params);
        if (logId !== null && logId !== undefined && logId !== "") {
            message.success("任务调度中，请查看页面状态!", 1, () => {
                // 跳转到页面
                location.href = "/schedulers/tasklog/detail?id=" + logId;
            });
        }
        dismissInputDialog();
    }

    /**
     * 显示输入参数的对话框
     */
    const showExecuteTaskDialog = async () => {
        updateShowInputParamsDialog(true)
    }

    /**
     * 销毁输入参数的对话框
     */
    const dismissInputDialog = async () => {
        updateShowInputParamsDialog(false)
    }

    /**
     * 编辑任务
     */
    const toEditTaskPage = async (id?: string) => {
        if (id == undefined) {
            id = item?.id;
        }
        location.href = "/schedulers/task/edit?id=" + id;
    }

    /**
     * 渲染操作按钮
     *
     * @param dom dom节点
     * @param entity 当前行数据实体
     */
    function renderOperationOptions(dom: React.ReactNode, entity: TaskBeans.TaskItem) {
        let quickButtons = [];
        if (entity.runState === 1) {
            quickButtons.push(<Button danger type={"primary"} size={"small"} style={{marginRight: 4}} onClick={() => {
                stopTask(entity.id)
            }}>停止</Button>);
        }
        if (entity.runState === 2) {
            quickButtons.push(<Button type={"primary"} size={"small"} style={{marginRight: 4}} onClick={() => {
                startTask(entity.id)
            }}>启动</Button>);
        }

        quickButtons.push(<Button type={"primary"} size={"small"}
                                  style={{marginRight: 4, marginTop: 2, marginBottom: 2}} onClick={() => {
            updateCurrId(entity.id)
            showExecuteTaskDialog();
        }}>执行</Button>);

        quickButtons.push(<Button type={"primary"} size={"small"}
                                  style={{marginRight: 4, marginTop: 2, marginBottom: 2}} onClick={() => {
            toEditTaskPage(entity.id)
        }}>编辑</Button>);

        quickButtons.push(<Button type={"primary"} size={"small"}
                                  style={{marginRight: 4, marginTop: 2, marginBottom: 2}} onClick={() => {
            gotoTaskLogList(entity.id)
        }}>日志</Button>);

        quickButtons.push(<Button type={"primary"} size={"small"}
                                  style={{marginRight: 4, marginTop: 2, marginBottom: 2}}>
            <Link to={`/schedulers/task/detail?id=${entity.id}`}>详情</Link>
        </Button>);
        return (
            <>
                {quickButtons}
            </>
        );
    }

    /**
     * 表格列
     */
    const columns: ProColumns<TaskBeans.TaskItem>[] = [
        {
            title: '任务ID',
            dataIndex: 'id',
            hideInSearch: true,
            hideInTable: true,
            render: (dom, entity) => {
                return <Link to={`/schedulers/task/detail?id=${entity.id}`}>{entity.id}</Link>
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
                }
            },
            request: async () => {
                return TenantService.getInstance().getSearchList();
            }
        },
        {
            title: '租户编码',
            dataIndex: 'tenant',
            hideInSearch: true,
        },
        {
            title: '应用名',
            dataIndex: 'appName',
            initialValue: '',
            valueType: 'select',
            fieldProps: {showSearch: true},
            valueEnum: appItems,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '任务名',
            dataIndex: 'name',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '负责人',
            dataIndex: 'owner',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '应用描述',
            dataIndex: 'appDesc',
            hideInSearch: true,
            hideInTable: true,
        },
        {
            title: '路由策略',
            dataIndex: 'routerStrategy',
            hideInSearch: true,
            render: (dom, entity) => {
                if (entity.routerStrategy === 1) {
                    return <Tag color={"green"}>随机策略</Tag>
                } else if (entity.routerStrategy === 2) {
                    return <Tag color={"blue"}>分片策略</Tag>
                } else {
                    return <Tag color={"red"}>未知策略</Tag>
                }
            }
        },
        {
            title: 'Cron表达式',
            dataIndex: 'cron',
            hideInSearch: true,
        },
        {
            title: '运行状态',
            dataIndex: 'runState',
            initialValue: '-1',
            width: "8%",
            valueType: 'select',
            request: async () => {
                return TaskService.getInstance().getRunStateList();
            },
            render: (dom, entity) => {
                if (entity.runState === 1) {
                    return <Tag color={"green"}>启动</Tag>
                } else {
                    return <Tag color={"red"}>停止</Tag>
                }
            }
        },
        {
            title: '标签',
            dataIndex: 'tag',
            width: "8%",
            render: (data: any) => {
                return data.map((tag: any, index: any) => (
                    <Tag color={"blue"} key={index}>{tag}</Tag>
                ));
            }
        },
        {
            title: '执行方法',
            dataIndex: 'method',
            hideInTable: true,
        },
        {
            title: "操作",
            tooltip: "执行：只执行一次任务，但不启动任务；",
            width: "22%",
            hideInSearch: true,
            render: renderOperationOptions,
        }
    ];

    return (
        <>
            <Modal destroyOnClose={true} title="填写任务参数"
                   open={showInputParamsDialog} onOk={executeTask} onCancel={dismissInputDialog}
                   onClose={dismissInputDialog} okText={"执行任务"} cancelText={"取消"}>
                <TextArea ref={currParamsRef} style={{height: 100}}/>
            </Modal>
            <PageContainer>
                <ProTable<TaskBeans.TaskItem, Commons.PageParams>
                    defaultSize="small"
                    headerTitle="查询表格"
                    tooltip={"双击某一条记录，可直接进入详情页"}
                    actionRef={actionRef}
                    formRef={tableFormRef}
                    search={{
                        defaultCollapsed: false,
                        labelWidth: 120,
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
                    request={TaskService.getInstance().getTaskList}
                    columns={columns}
                    onRow={(row) => {
                        return {
                            // 双击进入详情页
                            onDoubleClick: (event) => {
                                location.href = Pages.PAGE_TASK_DETAILS + "?id=" + row.id;
                            }
                        }
                    }}
                />
            </PageContainer>
        </>
    );
};

export default Index;
