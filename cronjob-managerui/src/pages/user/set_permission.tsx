/**
 * 设置菜单权限页面
 *
 * @author Horace
 */
import React, {useEffect, useState} from "react";
import {Commons} from "@/typings/commons";
import {Button, message} from "antd";
import MenuService from "@/services/MenuService";
import MsgCodes from "@/typings/msgcodes";
import {Link} from "umi";
import {Pages} from "@/typings/pages";
import {PageContainer, ProColumns, ProTable} from "@ant-design/pro-components";
import ParamsUtils from "@/utils/ParamsUtils";
import {TableRowSelection} from "antd/es/table/interface";
import * as Icons from "@ant-design/icons";

export interface Params {
    userId?: string;
}

const SetPermission: React.FC<Params> = (props: Params) => {
    const [state, setState] = useState<{
        selectedKeys?: React.Key[],
    }>();

    useEffect(() => {
        fetchPermissions().then(r => {
        });
    }, [])

    /**
     * 获取用户当前权限
     */
    async function fetchPermissions() {
        let menuIds = await MenuService.getInstance().getMenuIds(ParamsUtils.getQueryParams().userId);
        let keys: React.Key[] = [...menuIds];
        localStorage.setItem("menuIds", menuIds.toString());
        setState({selectedKeys: keys})
    }

    /**
     * 设置菜单权限
     */
    async function setPermission() {
        const menuIds = state?.selectedKeys?.toString();
        console.info(menuIds);
        const result = await MenuService.getInstance().grantUser({
            menuIds: menuIds,
            userId: ParamsUtils.getQueryParams().userId
        });
        if (result.code == MsgCodes.SUCCESS) {
            message.success("权限设置成功！", 0.5, () => {
                location.href = Pages.PAGE_USER_INDEX;
            });
        }
    }

    const rowSelection: TableRowSelection<Menu.MenuListItem> = {
        onChange: (selectedRowKeys, selectedRows) => {
            setState({
                ...state,
                selectedKeys: selectedRowKeys
            });
        },
    };

    /**
     * 表格列
     */
    const columns: ProColumns<Menu.MenuListItem>[] = [
        {
            title: '菜单ID',
            dataIndex: 'key',
            hideInSearch: true,
            hideInTable: true,
        },
        {
            title: '菜单名',
            dataIndex: 'name',
            valueType: 'text',
            width: 250,
            render: (dom, entity) => {
                return (
                    <>
            <span style={{marginRight: 10}}>{
                // @ts-ignore
                Icons && React.createElement(Icons[entity.icon])
            }</span>
                        <span>{entity.name}</span>
                    </>
                );
            }
        },
        {
            title: '是否菜单',
            dataIndex: 'menu',
            valueType: 'text',
            width: 200,
            hideInSearch: true,
            render: (dom, entity) => {
                if (entity.menu) {
                    return "是";
                }
                return "否";
            }
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            valueType: 'text',
            width: 250,
        },
    ];

    return (
        <PageContainer title={"菜单权限"} footer={[
            <Link to={Pages.PAGE_USER_INDEX}>
                <Button key="backup">返回</Button>
            </Link>,
            <Button key="submit" type="primary" onClick={setPermission}>
                提交
            </Button>,
        ]}>
            <ProTable<Menu.MenuListItem, Commons.PageParams>
                rowSelection={{
                    checkStrictly: false,
                    selectedRowKeys: state?.selectedKeys,
                    ...rowSelection,
                }}
                tableClassName={"permissionTable"}
                defaultSize="small"
                headerTitle="菜单列表"
                form={{loading: true}}
                rowKey="key"
                search={false}
                request={MenuService.getInstance().getAllMenuList}
                columns={columns}
            />
        </PageContainer>
    );
}

export default SetPermission;
