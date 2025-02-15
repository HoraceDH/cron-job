/**
 * 授权租户页面
 *
 * @author Horace
 */
import React, {useEffect, useState} from "react";
import {Commons} from "@/typings/commons";
import {Button, message} from "antd";
import MsgCodes from "@/typings/msgcodes";
import TenantService from "@/services/TenantService";
import ParamsUtils from "@/utils/ParamsUtils";
import {Pages} from "@/typings/pages";
import {Link} from "umi";
import {PageContainer, ProColumns, ProTable} from "@ant-design/pro-components";
import {TenantBeans} from "@/typings/tenant";
import {TableRowSelection} from "antd/es/table/interface";

export interface Params {
    userId?: string;
}

const SetTenant: React.FC<Params> = (props: Params) => {
    const [state, setState] = useState<{
        selectedKeys?: React.Key[],
    }>();

    useEffect(() => {
        getTenants().then(r => {
        });
    }, [])

    /**
     * 获取当前所属租户
     */
    async function getTenants() {
        let tenantIds = await TenantService.getInstance().getTenantIds(ParamsUtils.getQueryParams().userId);
        let keys: React.Key[] = [...tenantIds];
        setState({selectedKeys: keys})
    }

    /**
     * 授权租户
     */
    async function setTenant() {
        const tenantIds = state?.selectedKeys?.toString();
        const result = await TenantService.getInstance()
            .grantTenant({tenantIds: tenantIds, userId: ParamsUtils.getQueryParams().userId});
        if (result.code == MsgCodes.SUCCESS) {
            message.success("租户授权成功！", 0.5, () => {
                location.href = Pages.PAGE_USER_INDEX
            });
        }
    }

    /**
     * 表格列
     */
    const columns: ProColumns<TenantBeans.TenantItem>[] = [
        {
            title: '租户ID',
            dataIndex: 'key',
        },
        {
            title: '租户名称',
            dataIndex: 'name',
            width: 200,
        },
        {
            title: '租户编码',
            dataIndex: 'tenant',
            width: 100,
        },
        {
            title: '租户描述',
            dataIndex: 'remark',
            width: 200,
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            valueType: 'text',
            width: 250,
        },
    ];

    const rowSelection: TableRowSelection<TenantBeans.TenantItem> = {
        onChange: (selectedRowKeys, selectedRows) => {
            setState({
                ...state,
                selectedKeys: selectedRowKeys
            });
        },
    };

    return (
        <PageContainer title={"授权租户"} footer={[
            <Link to={Pages.PAGE_USER_INDEX}>
                <Button key="backup">返回</Button>
            </Link>,
            <Button key="submit" type="primary" onClick={setTenant}>
                提交
            </Button>,
        ]}>
            <ProTable<TenantBeans.TenantItem, Commons.PageParams>
                rowSelection={{
                    checkStrictly: false,
                    selectedRowKeys: state?.selectedKeys,
                    ...rowSelection,
                }}
                tableClassName={"permissionTable"}
                defaultSize="small"
                headerTitle="查询表格"
                form={{loading: true}}
                rowKey="key"
                search={false}
                request={TenantService.getInstance().getAllTenantList}
                columns={columns}
            />
        </PageContainer>
    );
}

export default SetTenant;
