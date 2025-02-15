import React, {useEffect, useState} from "react";
import {PageContainer, ProCard, ProDescriptions} from "@ant-design/pro-components";
import {TaskBeans} from "@/typings/task";
import ParamsUtils from "@/utils/ParamsUtils";
import TaskService from "@/services/TaskService";
import {Button, Divider, Spin, Tag} from "antd";
import {Link} from "umi";

/**
 * 任务详情页
 * @constructor
 */
const TaskDetail: React.FC = () => {
    let [data, setData] = useState<TaskBeans.TaskItem | null>();
    let [loading, setLoading] = useState<boolean>(true);

    /**
     * 查询任务详情
     */
    async function fetchTaskDetails() {
        setLoading(true);
        let params = ParamsUtils.getQueryParams();
        let taskItem = await TaskService.getInstance().getTask(params.id);
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
            header={{title: "任务详情"}}
            footer={[
                <Link to={"/schedulers/task/index"}><Button key="1" type={"primary"}>返回</Button></Link>,
                <Button key="2" type={"primary"} onClick={fetchTaskDetails}>刷新</Button>,
            ]}
        >
            <Spin spinning={loading} fullscreen/>
            <ProCard>
                <ProDescriptions title="基础详情" style={{marginBottom: 32}}>
                    <ProDescriptions.Item label="任务ID">{data?.id}</ProDescriptions.Item>
                    <ProDescriptions.Item label="租户编码">{data?.tenant}</ProDescriptions.Item>
                    <ProDescriptions.Item label="任务名">{data?.name}</ProDescriptions.Item>
                    <ProDescriptions.Item label="应用名">{data?.appName}</ProDescriptions.Item>
                    <ProDescriptions.Item label="应用描述">{data?.appDesc}</ProDescriptions.Item>
                    <ProDescriptions.Item label="Cron表达式">{data?.cron}</ProDescriptions.Item>
                    <ProDescriptions.Item label="运行状态">{
                        data?.runState === 1 ? <Tag color={"green"}>启动</Tag> :
                            data?.runState === 2 ? <Tag color={"red"}>停止</Tag> :
                                <Tag color={"red"}>未知状态</Tag>
                    }</ProDescriptions.Item>
                    <ProDescriptions.Item label="标签">{data?.tag}</ProDescriptions.Item>
                    <ProDescriptions.Item label="负责人">{data?.owner}</ProDescriptions.Item>
                    <ProDescriptions.Item label="任务描述">{data?.remark}</ProDescriptions.Item>
                    <ProDescriptions.Item label="创建时间">{data?.createTime}</ProDescriptions.Item>
                    <ProDescriptions.Item label="修改时间">{data?.modifyTime}</ProDescriptions.Item>
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
                    <ProDescriptions.Item label="失败重试间隔（毫秒）">{data?.failureRetryInterval}</ProDescriptions.Item>
                </ProDescriptions>
            </ProCard>
        </PageContainer>
    );
}
export default TaskDetail;
