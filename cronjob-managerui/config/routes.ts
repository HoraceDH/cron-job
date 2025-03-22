/**
 * @name umi 的路由配置
 * @description 只支持 path,component,routes,redirect,wrappers,name,icon 的配置
 * @param path  path 只支持两种占位符配置，第一种是动态参数 :id 的形式，第二种是 * 通配符，通配符只能出现路由字符串的最后。
 * @param component 配置 location 和 path 匹配后用于渲染的 React 组件路径。可以是绝对路径，也可以是相对路径，如果是相对路径，会从 src/pages 开始找起。
 * @param routes 配置子路由，通常在需要为多个路径增加 layout 组件时使用。
 * @param redirect 配置路由跳转
 * @param wrappers 配置路由组件的包装组件，通过包装组件可以为当前的路由组件组合进更多的功能。 比如，可以用于路由级别的权限校验
 * @param name 配置路由的标题，默认读取国际化文件 menu.ts 中 menu.xxxx 的值，如配置 name 为 login，则读取 menu.ts 中 menu.login 的取值作为标题
 * @param icon 配置路由的图标，取值参考 https://ant.design/components/icon-cn， 注意去除风格后缀和大小写，如想要配置图标为 <StepBackwardOutlined /> 则取值应为 stepBackward 或 StepBackward，如想要配置图标为 <UserOutlined /> 则取值应为 user 或者 User
 * @doc https://umijs.org/docs/guides/routes
 */
export default [
    {
        path: '/',
        redirect: '/welcome',
    },
    {
        component: './404',
    },
    {
        path: '*',
        layout: false,
        component: './404',
    },
    {
        path: '/welcome',
        name: 'welcome',
        icon: 'smile',
        component: './Welcome',
    },
    {
        path: '/user/login',
        name: '用户登录',
        layout: false,
        locale: false,
        icon: 'smile',
        component: './user/login',
    },
    {
        path: '/system/user/index',
        name: '用户管理',
        locale: false,
        icon: 'smile',
        component: './user/index',
    },
    {
        path: '/system/user/set_permission',
        name: '设置菜单权限',
        locale: false,
        icon: 'smile',
        component: './user/set_permission',
    },
    {
        path: '/system/user/set_tenant',
        name: '授权租户',
        locale: false,
        icon: 'smile',
        component: './user/set_tenant',
    },
    {
        path: '/system/tenant/index',
        name: '租户管理',
        locale: false,
        icon: 'smile',
        component: './tenant/index',
    },
    {
        path: '/system/tenant/edit',
        name: '租户编辑',
        locale: false,
        icon: 'smile',
        component: './tenant/edit',
    },
    {
        path: '/system/schedulers/index',
        name: '调度器管理',
        locale: false,
        icon: 'smile',
        component: './schedulers/index',
    },
    {
        path: '/schedulers/app/index',
        name: '应用管理',
        locale: false,
        icon: 'smile',
        component: './app/index',
    },
    {
        path: '/schedulers/executor/index',
        name: '执行器管理',
        locale: false,
        icon: 'smile',
        component: './executor/index',
    },
    {
        path: '/schedulers/task/index',
        name: '任务管理',
        locale: false,
        icon: 'smile',
        component: './task/index',
    },
    {
        path: '/schedulers/task/detail',
        name: '任务详情',
        locale: false,
        icon: 'smile',
        component: './task/detail',
    },
    {
        path: '/schedulers/task/edit',
        name: '编辑任务',
        locale: false,
        icon: 'smile',
        component: './task/edit',
    },
    {
        path: '/schedulers/tasklog/index',
        name: '任务日志',
        locale: false,
        icon: 'smile',
        component: './tasklog/index',
    },
    {
        path: '/schedulers/tasklog/detail',
        name: '任务日志详情',
        locale: false,
        icon: 'smile',
        component: './tasklog/detail',
    },
    // 这里累加
];
