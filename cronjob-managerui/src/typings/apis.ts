/* 配置API路径 */
export default {
    /*********** 用户模块 **************************************/
    /**
     * 用户登录
     */
    URI_USER_LOGIN: "/manager-api/user/login",
    /**
     * 获取用户信息
     */
    URI_USER_GET_USER: "/manager-api/user/getUser",
    /**
     * 获取用户信息列表
     */
    URI_USER_GET_USER_LIST: "/manager-api/user/getUserList",
    /**
     * 用户登出
     */
    URI_USER_LOGOUT: "/manager-api/user/logout",
    /**
     * 重置密码
     */
    URI_USER_RESET_PASSWORD: "/manager-api/user/resetPassword",
    /**
     * 设置用户状态
     */
    URI_USER_UPDATE_USER_STATE: "/manager-api/user/updateUserState",
    /**
     * 创建用户
     */
    URI_USER_CREATE_USER: "/manager-api/user/createUser",
    /*********** 用户模块 **************************************/

    /*********** 菜单模块 **************************************/
    /**
     * 授权菜单给用户
     */
    URI_MENU_GRANT: "/manager-api/menu/grant",
    /**
     * 获取用户菜单列表
     */
    URI_MENU_GET_USER_MENU_LIST: "/manager-api/menu/getUserMenuList",
    /**
     * 获取所有菜单
     */
    URI_MENU_GET_ALL_LIST: "/manager-api/menu/getAllList",
    /**
     * 获取有权限的菜单ID列表
     */
    URI_MENU_GET_USER_MENU_IDS: "/manager-api/menu/getUserMenuIds",
    /**
     * 获取当前用户所属的租户ID列表
     */
    URI_TENANT_GET_TENANT_IDS: "/manager-api/tenant/getTenantIds",
    /*********** 菜单模块 **************************************/

    /*********** 租户模块 **************************************/
    /**
     * 获取所有的租户
     */
    URI_TENANT_GET_ALL_LIST: "/manager-api/tenant/getAllList",
    /**
     * 授权租户给用户
     */
    URI_TENANT_GRANT: "/manager-api/tenant/grant",
    /**
     * 获取租户列表，提供给搜索框用
     */
    URI_TENANT_GET_SEARCH_LIST: "/manager-api/tenant/getSearchList",
    /*********** 租户模块 **************************************/

    /*********** 应用模块 **************************************/
    /**
     * 获取应用列表
     */
    URI_APP_GET_LIST: "/manager-api/app/getAppList",
    /**
     * 获取应用列表，提供给搜索框用
     */
    URI_APP_GET_SEARCH_LIST: "/manager-api/app/getSearchList",
    /**
     * 停止应用
     */
    URI_APP_STOP: "/manager-api/app/stop",
    /**
     * 启动应用
     */
    URI_APP_START: "/manager-api/app/start",
    /*********** 应用模块 **************************************/

    /*********** 调度器模块 **************************************/
    /**
     * 获取调度器列表
     */
    URI_SCHEDULERS_GET_LIST: "/manager-api/schedulers/getSchedulersList",
    /*********** 调度器模块 **************************************/

    /*********** 执行器模块 **************************************/
    /**
     * 获取执行器列表
     */
    URI_EXECUTOR_GET_LIST: "/manager-api/executor/getExecutorList",
    /*********** 执行器模块 **************************************/

    /*********** 任务模块 **************************************/
    /**
     * 获取任务详情
     */
    URI_TASK_GET: "/manager-api/task/getTask",
    /**
     * 更新任务运行状态
     */
    URI_TASK_UPDATE_RUN_STATE: "/manager-api/task/updateRunState",
    /**
     * 获取任务列表
     */
    URI_TASK_GET_LIST: "/manager-api/task/getTaskList",
    /**
     * 立即执行一次任务
     */
    URI_TASK_EXECUTE: "/manager-api/task/executeTaskNow",
    /**
     * 获取任务列表，提供给搜索框用
     */
    URI_TASK_GET_SEARCH_LIST: "/manager-api/task/getSearchList",
    /**
     * 根据ID更新任务信息
     */
    URI_TASK_UPDATE_BY_ID: "/manager-api/task/updateById",
    /**
     * 获取最近几次的执行时间
     */
    URI_TASK_GET_RECENT_EXECUTIONTIME: "/manager-api/task/getRecentExecutionTime",
    /*********** 任务模块 **************************************/

    /*********** 任务日志模块 **************************************/
    /**
     * 获取任务详情
     */
    URI_TASK_LOG_GET: "/manager-api/tasklog/getTaskLog",
    /**
     * 获取任务列表
     */
    URI_TASK_LOG_GET_LIST: "/manager-api/tasklog/getTaskLogList",
    /*********** 任务日志模块 **************************************/

    /*********** 统计模块 **************************************/
    /**
     * 获取概要数据
     */
    URI_STATISTICS_GET_SUMMARY_DATA: "/manager-api/statistics/getSummaryData",
    /**
     * 获取曲线图数据
     */
    URI_STATISTICS_GET_LINE_DATA: "/manager-api/statistics/getLineData",
    /*********** 统计模块 **************************************/
}
