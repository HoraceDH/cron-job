/**
 * 菜单
 * @author Horace
 */
declare namespace Menu {
    // 获取菜单的结果
    type MenuResult = Commons.MsgObject & {
        data: MenuItem[]
    }

    // 获取所有菜单的结果
    type AllMenuResult = Commons.MsgObject & {
        data?: MenuListItem[],
        total: number,
        success: true,
        pageSize: number,
        current: number
    }

    // 获取拥有权限菜单的结果
    type MyMenuIdResult = Commons.MsgObject & {
        data?: string[],
    }

    type MenuItem = {
        key: string,
        name: string,
        icon: string,
        path: string,
        component: string,
        subMenus: MenuItem[],
    };

    type MenuListItem = {
        id: string,
        parentId: string,
        menu: boolean,
        name: string,
        icon: string,
        path: string,
        createTime: string,
        component: string,
        children: MenuListItem[],
    };
}
