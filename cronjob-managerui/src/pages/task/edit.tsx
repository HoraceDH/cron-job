import React, {useRef, useState} from "react";
import {PageContainer, ProForm, ProFormText} from "@ant-design/pro-components";
import {Link} from "@@/exports";
import {Button, Card, Col, message, notification, Row, Space, Spin} from "antd";
import TaskService from "@/services/TaskService";
import ParamsUtils from "@/utils/ParamsUtils";
import {ModalForm, ProFormInstance, ProFormSelect} from "@ant-design/pro-form/lib";
import MsgCodes from "@/typings/msgcodes";
import {Pages} from "@/typings/pages";
import CronComponent from "@/components/Customs/CronComponent";
import ExecutorService from "@/services/ExecutorService";
import TenantService from "@/services/TenantService";

/**
 * 任务编辑
 *
 * @constructor
 */
const TaskEdit: React.FC = () => {
    const formRef = useRef<ProFormInstance>();
    const [loading, setLoading] = useState<boolean>(true);
    const [cronFormVisibility, setCronFormVisibility] = useState<boolean>(false);
    const [cronData, setCronData] = useState<String>();
    const [api, contextHolder] = notification.useNotification();


    /**
     * 查询任务详情
     */
    async function submitEditTask(data: Record<string, any>) {
        const success = await TaskService.getInstance().updateById(data);
        if (success) {
            message.success("任务更新成功，未在队列中、调度中的将取消执行，已在队列中、调度中的将继续执行！", 1, function () {
                document.location.href = Pages.PAGE_TASK_INDEX;
            });
            return true;
        }
        return false;
    }

    /**
     * 检查Cron表达式
     */
    async function checkExpression() {
        const cron = formRef.current?.getFieldValue("cron");
        const result = await TaskService.getInstance().getRecentExecutionTime(cron);
        if (result.code !== MsgCodes.SUCCESS) {
            message.error(result.msg + ", Cron表达式校验失败，请检查语法是否正确：" + cron);
        } else {
            const data: React.ReactNode[] = [];
            result.data?.forEach((item, index) => {
                data.push(<p style={{marginBottom: 0, color: "coral"}}>{(index + 1) + "：" + item}</p>);
            })
            // message.success(data, 10);
            api.success({
                duration: 10,
                message: "最近5次的执行时间：",
                description: data,
            });
        }
    }

    /**
     * 显示编辑cron表达式的弹窗
     */
    function showCronForm() {
        setCronFormVisibility(true);
    }

    /**
     * 结束编辑cron表达式
     */
    const finishCronForm = async () => {
        formRef.current?.setFieldValue("cron", cronData);
        setCronFormVisibility(false);
        return true;
    }

    return (
        <>
            {contextHolder}
            <Spin spinning={loading} fullscreen/>
            {/*cron表达式的弹窗*/}
            <ModalForm<String>
                title="Cron表达式编辑器"
                open={cronFormVisibility}
                autoFocusFirstInput
                modalProps={{
                    destroyOnClose: true,
                    onCancel: () => setCronFormVisibility(false),
                }}
                submitTimeout={5000}
                onFinish={finishCronForm}
            >
                <CronComponent initValue={formRef.current?.getFieldValue("cron")} onDataChange={(cron) => {
                    setCronData(cron)
                }}/>
                <div style={{float: "left", fontSize: 12, marginTop: 20, color: "gray"}}>
                    -- 因依赖的开源Cron组件无法在暗黑模式下正常展示，建议编辑时切换到默认的亮色风格 --
                </div>
            </ModalForm>
            {/*cron表达式的弹窗*/}
            <PageContainer
                footer={[
                    <Link to={Pages.PAGE_TASK_INDEX}><Button key="1" type={"primary"}>返回</Button></Link>,
                    <Button key="2" type={"primary"} onClick={async () => {
                        // 提交前，进行错误检测，避免底部提示不到用户
                        let fieldsError = formRef.current?.getFieldsError();
                        if (fieldsError) {
                            for (const error of fieldsError) {
                                if (error.errors && error.errors.length > 0) {
                                    message.warning(error.errors);
                                }
                            }
                        }
                        formRef.current?.submit()
                    }}>提交</Button>,
                ]}
            >
                <ProForm
                    request={async () => {
                        const taskId = ParamsUtils.getQueryParams().id;
                        const task: any = await TaskService.getInstance().getTask(taskId)
                        task.onlineState = task.onlineState + "";
                        task.runState = task.runState + "";
                        task.routerStrategy = task.routerStrategy + "";
                        task.expiredStrategy = task.expiredStrategy + "";
                        task.failureStrategy = task.failureStrategy + "";
                        setLoading(false);
                        return task;
                    }}
                    layout="vertical"
                    autoFocusFirstInput
                    formRef={formRef}
                    onFinish={submitEditTask}
                    submitter={{
                        // 自定义整个区域
                        render: (props, doms) => {
                            return [];
                        },
                    }}
                >
                    <Card title="基础编辑" bordered={false} style={{marginBottom: 5}}>
                        <Row gutter={16}>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormText
                                    label={"任务ID"}
                                    name="id"
                                    disabled={true}
                                    placeholder={"x"}
                                />
                            </Col>
                            <Col xl={{span: 6, offset: 2}} lg={{span: 8}} md={{span: 12}} sm={24}>
                                <ProFormText
                                    label={"租户编码"}
                                    name="tenant"
                                    disabled={true}
                                    placeholder={""}
                                />
                            </Col>
                            <Col xl={{span: 8, offset: 2}} lg={{span: 10}} md={{span: 24}} sm={24}>
                                <ProFormText
                                    label={"任务名"}
                                    name="name"
                                    disabled={true}
                                    rules={[{required: true, message: '请输入任务名'}]}
                                    placeholder={"请输入任务名"}
                                />
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormText
                                    label={"应用名"}
                                    name="appName"
                                    disabled={true}
                                    placeholder={""}
                                />
                            </Col>
                            <Col xl={{span: 6, offset: 2}} lg={{span: 8}} md={{span: 12}} sm={24}>
                                <ProFormText
                                    style={{maxWidth: 330}}
                                    label={"应用描述"}
                                    name="appDesc"
                                    disabled={true}
                                    placeholder={""}
                                />
                            </Col>
                            <Col xl={{span: 8, offset: 2}} lg={{span: 10}} md={{span: 24}} sm={24}>
                                <Space.Compact style={{width: '100%'}}>
                                    <ProFormText
                                        style={{maxWidth: 330}}
                                        label={"Cron表达式"}
                                        name="cron"
                                        tooltip={"点击【执行时间】可检查CRON表达式的正确性并展示最近5次的执行时间"}
                                        rules={[{required: true, message: '请输入Cron表达式'}]}
                                        placeholder={"请输入Cron表达式"}
                                    />
                                    <Button type={"primary"} onClick={showCronForm}
                                            style={{marginTop: "30px", marginLeft: "-1px"}}>Cron编辑器</Button>
                                    <Button type={"primary"} danger onClick={checkExpression}
                                            style={{marginTop: "30px", marginLeft: "-1px"}}>执行时间</Button>
                                </Space.Compact>
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormSelect
                                    label="运行状态"
                                    name="runState"
                                    rules={[{required: true, message: '请选择运行状态'}]}
                                    valueEnum={{
                                        1: '启动',
                                        2: '停止',
                                    }}
                                />
                            </Col>
                            <Col xl={{span: 6, offset: 2}} lg={{span: 8}} md={{span: 12}} sm={24}>
                                <ProFormSelect
                                    tooltip={"可选择多个标签，随机策略下：任务会在选择的标签的执行器集合中随机选择一个执行；分片策略下：会在选择的标签的执行器集合中分片执行；如果在任务调度时，所选的标签下没有任何在线的执行器，则会寻找common标签下的执行器进行。"}
                                    mode={"multiple"}
                                    label="标签"
                                    name="tag"
                                    rules={[{required: true, message: '请选择标签'}]}
                                    request={async () => {
                                        const taskId = ParamsUtils.getQueryParams().id;
                                        return ExecutorService.getInstance().getSearchList(taskId);
                                    }}
                                />
                            </Col>
                            <Col xl={{span: 8, offset: 2}} lg={{span: 10}} md={{span: 24}} sm={24}>
                                <ProFormText
                                    label={"负责人"}
                                    name="owner"
                                    rules={[{required: true, message: '请输入负责人'}]}
                                    placeholder={"请输入负责人"}
                                />
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormText
                                    label={"创建时间"}
                                    name="createTime"
                                    disabled={true}
                                    placeholder={"请输入创建时间"}
                                />
                            </Col>
                            <Col xl={{span: 6, offset: 2}} lg={{span: 8}} md={{span: 12}} sm={24}>
                                <ProFormText
                                    label={"修改时间"}
                                    name="modifyTime"
                                    disabled={true}
                                    placeholder={"请输入修改时间"}
                                />
                            </Col>
                            <Col xl={{span: 8, offset: 2}} lg={{span: 10}} md={{span: 24}} sm={24}>
                                <ProFormText
                                    label={"任务描述"}
                                    name="remark"
                                    placeholder={"请输入任务描述"}
                                />
                            </Col>
                        </Row>
                    </Card>

                    <Card title="方法编辑" bordered={false} style={{marginBottom: 5}}>
                        <Row gutter={16}>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormText
                                    label={"任务参数"}
                                    name="taskParams"
                                    placeholder={"请输入任务参数"}
                                />
                            </Col>
                            <Col xl={{span: 16, offset: 2}} lg={{span: 8}} md={{span: 12}} sm={24}>
                                <ProFormText
                                    label={"方法名"}
                                    name="method"
                                    disabled={true}
                                />
                            </Col>
                        </Row>
                    </Card>

                    <Card title="策略编辑" bordered={false} style={{marginBottom: 5}}>
                        <Row gutter={16}>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormSelect
                                    label="路由策略"
                                    name="routerStrategy"
                                    rules={[{required: true, message: '请选择路由策略'}]}
                                    valueEnum={{
                                        1: '随机策略',
                                        2: '分片策略',
                                    }}
                                />
                            </Col>
                            <Col xl={{span: 6, offset: 2}} lg={{span: 8}} md={{span: 12}} sm={24}>
                                <ProFormSelect
                                    label="过期策略"
                                    name="expiredStrategy"
                                    rules={[{required: true, message: '请选择过期策略'}]}
                                    valueEnum={{
                                        1: '过期丢弃',
                                        2: '过期执行',
                                    }}
                                />
                            </Col>
                            <Col xl={{span: 8, offset: 2}} lg={{span: 10}} md={{span: 24}} sm={24}>
                                <ProFormText
                                    label={"过期时间（毫秒）"}
                                    name="expiredTime"
                                    rules={[{required: true, message: '请输入过期时间'}]}
                                    placeholder={"请输入过期时间"}
                                />
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormSelect
                                    label="失败策略"
                                    name="failureStrategy"
                                    rules={[{required: true, message: '请选择失败策略'}]}
                                    valueEnum={{
                                        1: '失败重试',
                                        2: '失败丢弃',
                                    }}
                                />
                            </Col>
                            <Col xl={{span: 6, offset: 2}} lg={{span: 8}} md={{span: 12}} sm={24}>
                                <ProFormText
                                    label={"失败重试次数"}
                                    name="maxRetryCount"
                                    rules={[{required: true, message: '请输入失败重试次数'}]}
                                    placeholder={"请输入失败重试次数"}
                                />
                            </Col>
                            <Col xl={{span: 8, offset: 2}} lg={{span: 10}} md={{span: 24}} sm={24}>
                                <ProFormText
                                    label={"失败重试间隔（毫秒）"}
                                    name="failureRetryInterval"
                                    rules={[{required: true, message: '请输入失败重试间隔（毫秒）'}]}
                                    placeholder={"请输入失败重试间隔（毫秒）"}
                                />
                            </Col>
                        </Row>
                    </Card>
                </ProForm>
            </PageContainer>
        </>
    );
}

export default TaskEdit;