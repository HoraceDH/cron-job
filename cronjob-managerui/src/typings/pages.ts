// 页面路径
export enum Pages {
    /**
     * 登录页面
     */
    PAGE_URL_USER_LOGIN = "/user/login",
    /**
     * 后台首页
     */
    PAGE_ROOT = "/",
    /**
     * 用户管理页面
     */
    PAGE_USER_INDEX = "/system/user/index",
    /**
     * 设置用户权限
     */
    PAGE_USER_SET_PERMISSION = "/system/user/set_permission",
    /**
     * 设置用户租户
     */
    PAGE_USER_SET_TENANT = "/system/user/set_tenant",
    /**
     * 任务首页
     */
    PAGE_TASK_INDEX = "/schedulers/task/index",
    /**
     * 任务详情页
     */
    PAGE_TASK_DETAILS = "/schedulers/task/detail",
    /**
     * 任务日志详情页
     */
    PAGE_TASKLOG_DETAILS = "/schedulers/tasklog/detail",
    /**
     * 调度器主页
     */
    PAGE_SCHEDULER_INDEX = "/system/schedulers/index",
    /**
     * 租户主页
     */
    PAGE_TENANT_INDEX = "/system/tenant/index",
    /**
     * 应用主页
     */
    PAGE_APP_INDEX = "/schedulers/app/index",
    /**
     * 执行器主页
     */
    PAGE_EXECUTOR_INDEX = "/schedulers/executor/index",
}