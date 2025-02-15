import React, {useEffect, useState} from "react";
import {PageContainer, ProCard, ProDescriptions} from "@ant-design/pro-components";
import ParamsUtils from "@/utils/ParamsUtils";
import {Button, Divider, Spin, Tag} from "antd";
import {Link} from "umi";
import TaskLogService from "@/services/TaskLogService";
import {TaskLogBeans} from "@/typings/tasklog";

/**
 * 任务详情页
 * @constructor
 */
const TaskDetail: React.FC = () => {
    let [data, setData] = useState<TaskLogBeans.TaskLogItem | null>();
    let [loading, setLoading] = useState<boolean>(true);

    /**
     * 查询任务详情
     */
    async function fetchTaskDetails() {
        setLoading(true);
        let params = ParamsUtils.getQueryParams();
        let taskItem = await TaskLogService.getInstance().getTaskLog(params.id);
        setData(taskItem);
        setLoading(false);
    }

    /**
     * 页面渲染时调用
     */
    useEffect(() => {
        fetchTaskDetails().then(r => {
        });
    }, [0]);

    return (
        <PageContainer
            header={{title: "任务日志详情"}}
            footer={[
                <Link to={"/schedulers/tasklog/index"}><Button key="1" type={"primary"}>返回</Button></Link>,
                <Button key="2" type={"primary"} onClick={fetchTaskDetails}>刷新</Button>,
            ]}
        >
            <Spin spinning={loading} fullscreen/>

            <ProCard>
                <ProDescriptions title="基础详情" style={{marginBottom: 32}}>
                    <ProDescriptions.Item label="任务日志ID">{data?.id}</ProDescriptions.Item>
                    <ProDescriptions.Item label="任务ID">{data?.taskId}</ProDescriptions.Item>
                    <ProDescriptions.Item label="租户编码">{data?.tenant}</ProDescriptions.Item>
                    <ProDescriptions.Item label="应用名">{data?.appName}</ProDescriptions.Item>
                    <ProDescriptions.Item label="任务名">{data?.taskName}</ProDescriptions.Item>
                    <ProDescriptions.Item label="Cron表达式">{data?.cron}</ProDescriptions.Item>
                    <ProDescriptions.Item label="标签">{data?.tag}</ProDescriptions.Item>
                    <ProDescriptions.Item label="创建时间">{data?.createTime}</ProDescriptions.Item>
                    <ProDescriptions.Item label="修改时间">{data?.modifyTime}</ProDescriptions.Item>
                    <ProDescriptions.Item label="负责人">{data?.owner}</ProDescriptions.Item>
                    <ProDescriptions.Item label="任务描述">{data?.remark}</ProDescriptions.Item>
                </ProDescriptions>

                <Divider style={{marginBottom: 32}}/>
                <ProDescriptions title="方法详情" style={{marginBottom: 32}}>
                    <ProDescriptions.Item label="任务参数">{data?.params}</ProDescriptions.Item>
                    <ProDescriptions.Item label="方法名">{data?.method}</ProDescriptions.Item>
                </ProDescriptions>

                <Divider style={{marginBottom: 32}}/>
                <ProDescriptions title="策略详情" style={{marginBottom: 32}}>
                    <ProDescriptions.Item label="路由策略">{
                        data?.routerStrategy === 1 ? <Tag color={"cyan"}>随机策略</Tag> :
                            data?.routerStrategy === 2 ? <Tag color={"cyan"}>分片策略</Tag> :
                                <Tag color={"red"}>未知策略</Tag>
                    }</ProDescriptions.Item>
                    <ProDescriptions.Item label="过期策略">{
                        data?.expiredStrategy === 1 ? <Tag color={"cyan"}>过期丢弃</Tag> :
                            data?.expiredStrategy === 2 ? <Tag color={"cyan"}>过期执行</Tag> :
                                <Tag color={"red"}>未知策略</Tag>
                    }</ProDescriptions.Item>
                    <ProDescriptions.Item label="过期时间（毫秒）">{data?.expiredTime}</ProDescriptions.Item>
                    <ProDescriptions.Item label="失败策略">{
                        data?.failureStrategy === 1 ? <Tag color={"cyan"}>失败重试</Tag> :
                            data?.failureStrategy === 2 ? <Tag color={"cyan"}>失败丢弃</Tag> :
                                <Tag color={"red"}>未知策略</Tag>
                    }</ProDescriptions.Item>
                    <ProDescriptions.Item label="最大重试次数">{data?.maxRetryCount}</ProDescriptions.Item>
                    <ProDescriptions.Item label="实际重试次数">
                        <Tag color={"red"}>{data?.retryCount}</Tag>
                    </ProDescriptions.Item>
                    <ProDescriptions.Item label="失败重试间隔（毫秒）">{data?.failureRetryInterval}</ProDescriptions.Item>
                </ProDescriptions>

                <Divider style={{marginBottom: 32}}/>
                <ProDescriptions title="执行详情" style={{marginBottom: 32}}>
                    <ProDescriptions.Item label="任务日志状态"
                                          tooltip={"1、取消执行（预生成日志后，任务取消等情况）。2、任务过期（超过执行时间而未被调度）"}>{
                        data?.state === 1 ? <Tag color={"seagreen"}>初始化</Tag> :
                            data?.state === 2 ? <Tag color={"coral"}>队列中</Tag> :
                                data?.state === 3 ? <Tag color={"darkolivegreen"}>调度中</Tag> :
                                    data?.state === 4 ? <Tag color={"geekblue-inverse"}>执行成功</Tag> :
                                        data?.state === 5 ? <Tag color={"maroon"}>执行失败</Tag> :
                                            data?.state === 6 ?
                                                <Tag color={"deeppink"}>取消执行</Tag> :
                                                data?.state === 7 ?
                                                    <Tag color={"steelblue"}>任务过期</Tag> :
                                                    data?.state === 8 ? <Tag color={"chocolate"}>执行失败，已丢弃</Tag> :
                                                        data?.state === 9 ?
                                                            <Tag color={"gold-inverse"}>执行失败，重试中</Tag> :
                                                            data?.state === 10 ?
                                                                <Tag color={"red-inverse"}>未找到执行方法</Tag> :
                                                                <Tag color={"red-inverse"}>未知状态</Tag>
                    }</ProDescriptions.Item>
                    <ProDescriptions.Item tooltip={"任务方法的执行耗时"}
                                          label="执行耗时">{data?.elapsedTime === null ? "-" : data?.elapsedTime + " ms"}</ProDescriptions.Item>
                    <ProDescriptions.Item label="调度时间"
                                          tooltip={"调度器将任务分发给执行器的时间"}>{data?.dispatcherTime}</ProDescriptions.Item>
                    <ProDescriptions.Item label="调度器地址">{data?.schedulersAddress}</ProDescriptions.Item>
                    <ProDescriptions.Item label="执行器地址">{data?.executorAddress}</ProDescriptions.Item>
                    <ProDescriptions.Item label="预计执行时间">{data?.executionTime}</ProDescriptions.Item>
                    <ProDescriptions.Item label="首次执行时间"
                                          tooltip={"如果任务进行过失败重试，那么该时间表示重试前应该在什么时间执行"}>
                        {data?.firstExecutionTime}
                    </ProDescriptions.Item>
                    <ProDescriptions.Item label="执行延迟时间"
                                          tooltip={"延迟时间 = 实际执行时间- 预计执行时间。如果是【执行失败，重试中】的状态，则该值会为负值，因为【预计执行时间】会重新计算，加上了重试间隔时间。"}>
                        {data?.delayTime === null ? "-" : data?.delayTime + " ms"}
                    </ProDescriptions.Item>
                    <ProDescriptions.Item label="实际执行时间">{data?.realExecutionTime}</ProDescriptions.Item>
                    <ProDescriptions.Item label="分片参数"
                                          tooltip={"只有在分片策略下才有参考意义"}>
                        页码：{data?.page}，页数：{data?.total}
                    </ProDescriptions.Item>
                </ProDescriptions>

                <Divider style={{marginBottom: 32}}/>
                <ProDescriptions title="失败原因" style={{marginBottom: 32}}>
                    <ProDescriptions.Item label="">
                        <pre>
                            {data?.failedReason}
                        </pre>
                    </ProDescriptions.Item>
                </ProDescriptions>
            </ProCard>
        </PageContainer>
    );
}
export default TaskDetail;
