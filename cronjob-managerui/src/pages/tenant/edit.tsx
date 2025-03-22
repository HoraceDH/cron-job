import React, {useRef, useState} from "react";
import {PageContainer, ProForm, ProFormText} from "@ant-design/pro-components";
import {Link} from "@@/exports";
import {Button, Card, Col, message, Row, Spin} from "antd";
import ParamsUtils from "@/utils/ParamsUtils";
import {ProFormInstance, ProFormSelect} from "@ant-design/pro-form/lib";
import {Pages} from "@/typings/pages";
import TenantService from "@/services/TenantService";
import AlarmService from "@/services/AlarmService";
import {TenantBeans} from "@/typings/tenant";
import {Commons} from "@/typings/commons";
import MsgCodes from "@/typings/msgcodes";

/**
 * 租户编辑
 *
 * @constructor
 */
const TenantEdit: React.FC = () => {
    const formRef = useRef<ProFormInstance>();
    const [loading, setLoading] = useState<boolean>(true);
    const [tenant, updateTenant] = useState<TenantBeans.TenantItem>();
    const [alarmTypeList, updateAlarmTypeList] = useState<Commons.SearchItem[]>();
    const [alarmGroupList, updateAlarmGroupList] = useState<Commons.SearchItem[]>();

    /**
     * 初始化
     */
    async function init(type: string) {
        // 获取告警类型列表
        const alarmTypeList: Commons.SearchItem[] = await AlarmService.getInstance().getSearchList();
        updateAlarmTypeList(alarmTypeList);

        // 获取告警群列表
        if (type != "0") {
            const alarmGroupList: Commons.SearchItem[] = await AlarmService.getInstance().getGroupList(type);
            updateAlarmGroupList(alarmGroupList);
        }
    }

    /**
     * 更新租户信息
     */
    async function submitEditTenant(tenant: TenantBeans.TenantItem) {
        const success = await TenantService.getInstance().updateById(tenant);
        if (success) {
            message.success("租户更新成功！");
            document.location.href = Pages.PAGE_TENANT_INDEX;
            return true;
        }
        return false;
    }

    /**
     * 触发一次告警
     */
    async function sendAlarm() {
        if (tenant?.type === 0) {
            message.warning("请先设置告警方式！");
            return;
        }

        let result = await AlarmService.getInstance().sendAlarm({
            type: tenant?.type,
            chatId: tenant?.chatId,
        });
        if (result.code === MsgCodes.SUCCESS) {
            message.success("发送告警信息成功！");
        }
    }

    /**
     * 修改告警方式
     *
     * @param value 选项值
     * @param options 参数
     */
    async function changeAlarmType(value: string, options: any) {
        console.info("value = ", value, " options = ", options);
        // 清空告警群列表
        updateAlarmGroupList(undefined);
        formRef.current?.setFieldValue("chatId", "不设置告警");
        formRef.current?.setFieldValue("groupName", undefined);

        // 重新加载告警群列表
        if (value != "0") {
            formRef.current?.setFieldValue("chatId", undefined);
            const alarmGroupList: Commons.SearchItem[] = await AlarmService.getInstance().getGroupList(value);
            updateAlarmGroupList(alarmGroupList);
        }
        let type = Number(value);
        updateTenant({
            ...tenant,
            type: type,
        });
    }

    return (
        <>
            <Spin spinning={loading} fullscreen/>
            <PageContainer
                title={"租户编辑"}
                footer={[
                    <Link to={Pages.PAGE_TENANT_INDEX}><Button key="1" type={"primary"}>返回</Button></Link>,
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
                <ProForm<TenantBeans.TenantItem>
                    request={async () => {
                        const id = ParamsUtils.getQueryParams().id;
                        const tenant: any = await TenantService.getInstance().getTenantDetail(id)
                        updateTenant(tenant);
                        await init(tenant.type);
                        setLoading(false);
                        return tenant;
                    }}
                    layout="vertical"
                    autoFocusFirstInput
                    formRef={formRef}
                    onFinish={submitEditTenant}
                    submitter={{
                        // 自定义整个区域
                        render: (props, doms) => {
                            return [];
                        },
                    }}
                >
                    <Card title="基础编辑" variant={"borderless"} style={{marginBottom: 5}}>
                        <Row gutter={16}>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormText
                                    label={"租户ID"}
                                    name="key"
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
                                    label={"租户名称"}
                                    name="name"
                                    rules={[{required: true, message: '请输入租户名称'}]}
                                    placeholder={"请输入租户名称"}
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
                                    label={"租户描述"}
                                    name="remark"
                                    placeholder={"请输入租户描述"}
                                />
                            </Col>
                        </Row>
                    </Card>

                    <Card title="告警配置" variant={"borderless"} style={{marginBottom: 5}}>
                        <Row gutter={16}>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormSelect
                                    tooltip={"支持的告警方式，如：飞书/Lark、企业微信、邮件等，但需要在application.properties中配置相关信息才可选"}
                                    label={"告警方式"}
                                    name="type"
                                    rules={[{required: true, message: '请选择告警方式'}]}
                                    options={alarmTypeList}
                                    onChange={changeAlarmType}
                                />
                            </Col>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormSelect
                                    tooltip={"告警群组需要拉机器人进群，才能获取到群组和发告警信息"}
                                    label={"告警群组"}
                                    name="chatId"
                                    rules={[{required: true, message: '请选择告警群组'}]}
                                    options={alarmGroupList}
                                    onChange={async (value, option) => {
                                        // @ts-ignore
                                        let label = option?.label as string;
                                        updateTenant({
                                            ...tenant,
                                            chatId: value as string,
                                            groupName: label,
                                        })
                                        formRef.current?.setFieldValue("chatId", value)
                                        formRef.current?.setFieldValue("groupName", label)
                                    }}
                                />
                            </Col>
                            <Col lg={6} md={12} sm={24}>
                                <ProFormText
                                    label={"告警群名"}
                                    name="groupName"
                                    disabled={true}
                                    placeholder={""}
                                />
                            </Col>
                            <Col lg={6} md={12} sm={24}>
                                <Button danger={true} type={"primary"} style={{marginTop: 30}}
                                        onClick={sendAlarm}>触发一下</Button>
                            </Col>
                        </Row>
                    </Card>
                </ProForm>
            </PageContainer>
        </>
    );
}

export default TenantEdit;