package cn.horace.cronjob.executor.constants;

/**
 * @author Horace
 */
public class OpenApis {
    /**
     * 主机
     */
    public static volatile String HOST = "";
    /**
     * 注册执行器
     */
    public static String EXECUTOR_REGISTER = "/openapi/executor/register";
    /**
     * 注销下线执行器
     */
    public static String EXECUTOR_UNREGISTER = "/openapi/executor/unregister";
    /**
     * 执行器心跳
     */
    public static String EXECUTOR_HEARTBEAT = "/openapi/executor/heartbeat";
    /**
     * 注册任务
     */
    public static String TASK_REGISTER = "/openapi/task/register";
    /**
     * 任务执行完毕
     */
    public static String TASK_EXECUTE_COMPLETE = "/openapi/task/complete";

    /**
     * 获取请求地址
     *
     * @param uri 请求路径
     * @return
     */
    public static String getRequestURL(String uri) {
        return HOST + uri;
    }
}