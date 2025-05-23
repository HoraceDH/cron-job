import {PageContainer, ProColumns, ProTable} from '@ant-design/pro-components';
import React, {useEffect, useState} from 'react';
import {Button, Card, Col, DatePicker, Divider, Row, Select, Tag, Tooltip} from "antd";
import {InfoCircleOutlined, ReloadOutlined} from "@ant-design/icons";
import "./Welcome.less"
import {StatisticsBeans} from "@/typings/statisticss";
import StatisticsService from "@/services/StatisticsService";
import {Line} from "@ant-design/plots";
import defaultSettings from '../../config/defaultSettings';
import dayjs, {Dayjs} from "dayjs";
import {Pages} from "@/typings/pages";
import {Link} from "@@/exports";
import TaskService from "@/services/TaskService";
import {Commons} from "@/typings/commons";
import {TenantBeans} from "@/typings/tenant";
import AlarmService from "@/services/AlarmService";

const {RangePicker} = DatePicker;

const Welcome: React.FC = () => {
    const [summaryData, updateSummaryData] = useState<StatisticsBeans.SummaryData>();
    const [lineData, updateLineData] = useState<StatisticsBeans.LineData[]>();
    const [taskSelectList, updateTaskSelectList] = useState<Commons.SearchItem[]>();
    const [loading, updateLoading] = useState<boolean>(true)
    const [rangePickerValue, updateRangePickerValue] = useState<[Dayjs | null | undefined, Dayjs | null | undefined]>([dayjs().add(-2, 'hour'), dayjs()]);
    const [currentTaskId, updateCurrentTaskId] = useState<string>();

    /**
     * 加载概要数据
     */
    async function fetchData() {
        const data = await StatisticsService.getInstance().getSummaryData();
        updateSummaryData(data);
        // 获取任务列表
        const taskSelectList = await TaskService.getInstance().getSearchList("", "");
        updateTaskSelectList(taskSelectList);
        fetchLineData("", "", "");
    }

    async function fetchLineData(taskId: string | undefined, startDate: string, endDate: string) {
        updateLoading(true);
        if (startDate === "" || endDate === "") {
            startDate = dayjs().add(-2, 'hour').format("YYYY-MM-DD HH:mm:ss");
            endDate = dayjs().format("YYYY-MM-DD HH:mm:ss");
        }

        const lineDataSet = await StatisticsService.getInstance().getLineData(taskId, startDate, endDate);
        updateLineData(lineDataSet)
        updateLoading(false);
    }

    useEffect(() => {
        fetchData().then();
    }, []);

    const config = {
        data: lineData,
        theme: defaultSettings.navTheme === "light" ? "classic" : "classicDark",
        xField: (d: any) => new Date(d.date),
        yField: 'value',
        colorField: 'name',
        axis: {
            y: {title: '指标数'},
        },
        label: {
            text: 'name',
            selector: 'last',
            style: {
                fontSize: 10,
            },
        },
        tooltip: {
            channel: 'y',
            valueFormatter: '.2f'
        },
    };

    /**
     * 渲染操作按钮
     *
     * @param dom dom节点
     * @param entity 当前行数据实体
     */
    function renderOperationOptions(dom: React.ReactNode, entity: AlarmBeans.AlarmItem) {
        return (
            <Button type={"primary"} size={"small"}>
                <Link to={Pages.PAGE_TASKLOG_DETAILS + `?id=${entity.taskLogId}`}>查看详情</Link>
            </Button>
        );
    }

    const columns: ProColumns<AlarmBeans.AlarmItem>[] = [
        {
            title: '任务日志ID',
            dataIndex: 'taskLogId',
            width: "10%",
            hideInSearch: true,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '应用名称',
            dataIndex: 'appName',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '任务名称',
            dataIndex: 'taskName',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '告警状态',
            dataIndex: 'state',
            valueEnum: {
                0: <Tag color={"green"}>初始化</Tag>,
                1: <Tag color={"green"}>已送达</Tag>,
                2: <Tag color={"green"}>告警失败</Tag>,
            },
        },
        {
            title: '告警方式',
            dataIndex: 'alarmType',
            hideInSearch: true,
            valueEnum: {
                0: <Tag color={"volcano"}>未设置告警</Tag>,
                1: <Tag color={"volcano"}>飞书告警</Tag>,
                2: <Tag color={"volcano"}>Lark告警</Tag>,
                3: <Tag color={"volcano"}>企微告警</Tag>,
            },
        },
        {
            title: '告警群名',
            dataIndex: 'alarmGroupName',
            hideInSearch: true,
            render: (dom: React.ReactNode, entity: AlarmBeans.AlarmItem) => {
                return <Tag color={"orange"}>{entity.alarmGroupName}</Tag>;
            }
        },
        {
            title: '执行器地址',
            hideInSearch: true,
            dataIndex: 'executorAddress',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '执行器主机名',
            hideInSearch: true,
            dataIndex: 'executorHostName',
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            valueType: 'text',
            hideInSearch: true,
            width: 250,
            ellipsis: {showTitle: true}, // 超出自动缩略
        },
        {
            title: '创建时间',
            dataIndex: 'createTimeRange',
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

    // @ts-ignore
    return (
        <PageContainer>
            <Row gutter={16} align={"middle"}>
                <Col className="gutter-row" span={4}>
                    <Card className={"statistics-card"}>
                        <div>
                            <span className={"name"}>调度器总数</span>
                            <div style={{float: "right"}}>
                                <Tooltip title={"调度器支持多实例部署，多实例有助于分摊调度压力"}>
                                    <InfoCircleOutlined/>
                                </Tooltip>
                            </div>
                        </div>
                        <span className={"value"}>
                            <Link to={`${Pages.PAGE_SCHEDULER_INDEX}`}>{summaryData?.onlineSchedulerCount}</Link>
                        </span>
                        <Divider className={"divider"}/>
                        <span className={"tips"}>在线的调度器数量</span>
                    </Card>
                </Col>
                <Col className="gutter-row" span={4}>
                    <Card className={"statistics-card"}>
                        <div>
                            <span className={"name"}>租户总数</span>
                            <div style={{float: "right"}}>
                                <Tooltip title={"租户总数量"}>
                                    <InfoCircleOutlined/>
                                </Tooltip>
                            </div>
                        </div>
                        <span className={"value"}>
                            <Link to={`${Pages.PAGE_TENANT_INDEX}`}>{summaryData?.totalTenantCount}</Link>
                        </span>
                        <Divider className={"divider"}/>
                        <span className={"tips"}>租户总数量</span>
                    </Card>
                </Col>
                <Col className="gutter-row" span={4}>
                    <Card className={"statistics-card"}>
                        <div>
                            <span className={"name"}>运行中应用数</span>
                            <div style={{float: "right"}}>
                                <Tooltip title={"运行中的应用数量"}>
                                    <InfoCircleOutlined/>
                                </Tooltip>
                            </div>
                        </div>
                        <span className={"value"}>
                            <Link to={`${Pages.PAGE_APP_INDEX}`}>{summaryData?.onlineAppCount}</Link>
                        </span>
                        <Divider className={"divider"}/>
                        <span className={"tips"}>运行中的应用数量</span>
                    </Card>
                </Col>
                <Col className="gutter-row" span={4}>
                    <Card className={"statistics-card"}>
                        <div>
                            <span className={"name"}>在线执行器数</span>
                            <div style={{float: "right"}}>
                                <Tooltip title={"执行器表示执行任务的服务实例"}>
                                    <InfoCircleOutlined/>
                                </Tooltip>
                            </div>
                        </div>
                        <span className={"value"}>
                            <Link to={`${Pages.PAGE_EXECUTOR_INDEX}`}>{summaryData?.onlineExecutorCount}</Link>
                        </span>
                        <Divider className={"divider"}/>
                        <span className={"tips"}>在线的执行器数量</span>
                    </Card>
                </Col>
                <Col className="gutter-row" span={4}>
                    <Card className={"statistics-card"}>
                        <div>
                            <span className={"name"}>任务总数</span>
                            <div style={{float: "right"}}>
                                <Tooltip title={"总的任务数量"}>
                                    <InfoCircleOutlined/>
                                </Tooltip>
                            </div>
                        </div>
                        <span className={"value"}>
                            <Link to={`${Pages.PAGE_TASK_INDEX}`}>{summaryData?.totalTaskCount}</Link>
                        </span>
                        <Divider className={"divider"}/>
                        <span className={"tips"}>总的任务数量</span>
                    </Card>
                </Col>
                <Col className="gutter-row" span={4}>
                    <Card className={"statistics-card"}>
                        <div>
                            <span className={"name"}>启动任务数</span>
                            <div style={{float: "right"}}>
                                <Tooltip title={"启动的任务数量"}>
                                    <InfoCircleOutlined/>
                                </Tooltip>
                            </div>
                        </div>
                        <span className={"value"}>
                            <Link to={`${Pages.PAGE_TASK_INDEX}`}>{summaryData?.onlineTaskCount}</Link>
                        </span>
                        <Divider className={"divider"}/>
                        <span className={"tips"}>启动的任务数量</span>
                    </Card>
                </Col>
            </Row>

            {/*告警列表*/}
            <ProTable<AlarmBeans.AlarmItem, Commons.PageParams>
                defaultSize="small"
                headerTitle="告警列表"
                style={{marginTop: 30, marginBottom: 30}}
                search={{
                    labelWidth: 120,
                }}
                pagination={{pageSize: 5}}
                rowKey="key"
                request={AlarmService.getInstance().getAlarmList}
                columns={columns}
                onRow={(row) => {
                    return {
                        // 双击进入详情页
                        onDoubleClick: (event) => {
                            location.href = Pages.PAGE_TASKLOG_DETAILS + "?id=" + row.taskLogId;
                        }
                    }
                }}
            />
            {/*告警列表*/}

            {/*数据曲线*/}
            <Card loading={loading} title={
                <>
                    <span style={{marginRight: 5}}>数据指标</span>
                    <Tooltip title={
                        <span style={{fontSize: 12}}>
                            1、延迟=实际执行时间-预计执行时间，单位毫秒。
                            <br/>
                            2、耗时=执行器中方法的执行耗时，单位毫秒。
                            <br/>
                            3、提前调度=为了保证任务更加准时的执行，系统会提前将任务派发给执行器暂存，单位毫秒。
                            <br/>
                            注意：
                            <br/>
                            1. 提前调度为负数则表示没有提前调度，需要检查配置[schedulers.beforeInterval]，或调度器CPU高或LongGC。
                            <br/>
                            2. 延迟时间增高但提前调度时间为正数，则是执行器的问题，可能的原因是：CPU高、LongGC等。
                        </span>
                    }>
                        <InfoCircleOutlined/>
                    </Tooltip>

                    <span style={{marginLeft: 20}}>任务：</span>
                    <Select defaultValue={""} options={taskSelectList} showSearch={true}
                            placeholder={"请选择任务"} style={{width: '360px'}} onChange={(taskId) => {
                        updateCurrentTaskId(taskId);
                        const startDate = dayjs().add(-2, 'hour');
                        const endDate = dayjs();
                        updateRangePickerValue([startDate, endDate]);
                        fetchLineData(taskId, startDate.format("YYYY-MM-DD HH:mm:ss"), endDate.format("YYYY-MM-DD HH:mm:ss"));
                    }}/>
                </>
            } style={{marginTop: 20}} extra={
                <>
                    <Tooltip title={"最近2小时"}>
                        <Button onClick={() => {
                            const startDate = dayjs().add(-2, 'hour');
                            const endDate = dayjs();
                            updateRangePickerValue([startDate, endDate]);
                            fetchLineData(currentTaskId, startDate.format("YYYY-MM-DD HH:mm:ss"), endDate.format("YYYY-MM-DD HH:mm:ss"));
                        }} style={{marginRight: 10}} type={"link"} shape="circle" icon={<ReloadOutlined/>}/>
                    </Tooltip>
                    <RangePicker showTime
                                 format={"YYYY-MM-DD HH:mm:ss"}
                                 value={rangePickerValue}
                                 presets={[
                                     {label: "最近1小时", value: [dayjs().add(-1, 'hour'), dayjs()]},
                                     {label: "最近2小时", value: [dayjs().add(-2, 'hour'), dayjs()]},
                                     {label: "最近3小时", value: [dayjs().add(-3, 'hour'), dayjs()]},
                                     {label: "最近1天", value: [dayjs().add(-1, 'day'), dayjs()]},
                                     {label: "最近2天", value: [dayjs().add(-2, 'day'), dayjs()]},
                                     {label: "最近3天", value: [dayjs().add(-3, 'day'), dayjs()]},
                                     {label: "最近7天", value: [dayjs().add(-7, 'day'), dayjs()]},
                                 ]}
                                 onChange={data => {
                                     // @ts-ignore
                                     updateRangePickerValue([data[0], data[1]]);
                                     // @ts-ignore
                                     const startDate = data[0].format('YYYY-MM-DD HH:mm:ss');
                                     // @ts-ignore
                                     const endDate = data[1].format('YYYY-MM-DD HH:mm:ss');
                                     fetchLineData(currentTaskId, startDate, endDate);
                                 }}
                    />
                </>
            }>
                <Line {...config}/>
            </Card>
            {/*数据曲线*/}
        </PageContainer>
    );
};

export default Welcome;
