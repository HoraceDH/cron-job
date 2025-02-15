package cn.horace.cronjob.executor.service;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.TaskResult;
import cn.horace.cronjob.commons.httpclient.HttpClient;
import cn.horace.cronjob.executor.bean.ExecutorHeartbeatParams;
import cn.horace.cronjob.executor.bean.ExecutorRegisterParams;
import cn.horace.cronjob.executor.constants.OpenApis;
import cn.horace.cronjob.executor.httpserver.HttpServer;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这里收拢了所有与调度器相关的接口
 * <p>
 * Created in 2024-12-07 12:17.
 *
 * @author Horace
 */
public class OpenApiService {
    private static final Logger logger = LoggerFactory.getLogger(OpenApiService.class);
    private static OpenApiService INSTANCE = new OpenApiService();
    private HttpClient httpClient = HttpClient.getInstance();

    private OpenApiService() {
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static OpenApiService getInstance() {
        return INSTANCE;
    }

    /**
     * 注册执行器
     *
     * @param params 注册参数
     * @return 是否注册成功
     */
    public boolean registerExecutor(ExecutorRegisterParams params) {
        boolean success = false;
        String jsonParams = JSONObject.toJSONString(params);
        try {
            MsgObject msgObject = this.httpClient.postMsgObject(OpenApis.getRequestURL(OpenApis.EXECUTOR_REGISTER), null, null, jsonParams);
            if (msgObject.isSuccess()) {
                success = true;
                logger.info("cron job executor register success, serverAddress:{}, params:{}, result:{}", OpenApis.HOST, params, msgObject);
            } else {
                logger.error("cron job executor register failed, serverAddress:{}, result:{}, params:{}", OpenApis.HOST, msgObject, params);
            }
        } catch (Exception e) {
            logger.error("cron job executor register error, serverAddress:{}, params:{}, msg:{}", OpenApis.HOST, params, e.getMessage(), e);
        }
        return success;
    }

    /**
     * 注册任务
     *
     * @param address 执行器地址
     * @param params  注册参数
     */
    public boolean registerTasks(String address, String params) {
        boolean success = false;
        try {
            MsgObject msgObject = this.httpClient.postMsgObject(OpenApis.getRequestURL(OpenApis.TASK_REGISTER), null, null, params);
            if (msgObject.isSuccess()) {
                success = true;
                logger.info("cron job task register success, address:{}, result:{}", address, msgObject);
            } else {
                logger.error("cron job task register failed, address:{}, result:{}", address, msgObject);
            }
        } catch (Exception e) {
            logger.error("cron job task register error, address:{}, msg:{}", address, e.getMessage(), e);
        }
        return success;
    }

    /**
     * 注销执行器，将该执行器下线
     *
     * @param params 执行器地址
     */
    public boolean unregisterExecutor(String params) {
        boolean success = false;
        try {
            MsgObject msgObject = this.httpClient.postMsgObject(OpenApis.getRequestURL(OpenApis.EXECUTOR_UNREGISTER), null, null, params);
            if (msgObject.isSuccess()) {
                success = true;
                logger.info("cron job task unregister success, result:{}, params:{}", msgObject, params);
            } else {
                logger.error("cron job task unregister failed, result:{}, params:{}", msgObject, params);
            }
        } catch (Exception e) {
            logger.error("cron job task unregister error, params:{}, msg:{}", params, e.getMessage(), e);
        }
        return success;
    }

    /**
     * 心跳
     *
     * @param address 执行器地址
     */
    public void heartbeat(String address) {
        try {
            ExecutorHeartbeatParams heartbeatParams = new ExecutorHeartbeatParams();
            heartbeatParams.setAddress(address);
            String params = JSONObject.toJSONString(heartbeatParams);
            MsgObject msgObject = this.httpClient.postMsgObject(OpenApis.getRequestURL(OpenApis.EXECUTOR_HEARTBEAT), null, null, params);
            if (!msgObject.isSuccess()) {
                logger.error("cron job heartbeat failed, params:{}, result:{}", params, msgObject);
            } else {
                logger.debug("cron job heartbeat success, params:{}, result:{}", params, msgObject);
            }
        } catch (Exception e) {
            logger.error("cron job heartbeat error, address:{}, msg:{}", address, e.getMessage(), e);
        }
    }

    /**
     * 发送结果给调度器
     *
     * @param result 执行结果
     */
    public boolean sendResult(TaskResult result) {
        result.setAddress(HttpServer.getInstance().getAddress());
        String data = JSONObject.toJSONString(result);
        boolean success = false;
        try {
            MsgObject msgObject = httpClient.postMsgObject(OpenApis.getRequestURL(OpenApis.TASK_EXECUTE_COMPLETE), null, null, data);
            if (msgObject.isSuccess()) {
                success = true;
                logger.debug("cron job send result success, result:{}, data:{}", msgObject, data);
            } else {
                logger.error("cron job send result failed, try again in 1 second, result:{}, params:{}", msgObject, data);
            }
        } catch (Throwable e) {
            logger.error("cron job send result exception, try again in 1 second, result:{}, msg:{}", data, e.getMessage(), e);
        }
        return success;
    }
}