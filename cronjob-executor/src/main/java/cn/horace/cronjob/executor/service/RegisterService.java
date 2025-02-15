package cn.horace.cronjob.executor.service;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.commons.utils.HostUtils;
import cn.horace.cronjob.executor.CronJobExecutorClient;
import cn.horace.cronjob.executor.bean.ExecutorRegisterParams;
import cn.horace.cronjob.executor.config.ExecutorConfig;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Horace
 */
public class RegisterService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);
    private static RegisterService INSTANCE = new RegisterService();
    private OpenApiService openApiService = OpenApiService.getInstance();
    private volatile boolean registerSuccess = false;

    private RegisterService() {
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static RegisterService getInstance() {
        return INSTANCE;
    }

    /**
     * 是否已经注册成功
     *
     * @return
     */
    public boolean isRegisterSuccess() {
        return registerSuccess;
    }

    /**
     * 注册执行器
     *
     * @param config  执行器配置
     * @param address 执行器地址，host:port
     */
    public void register(ExecutorConfig config, String address) {
        ExecutorRegisterParams params = this.buildExecutorParams(config);
        params.setAddress(address);
        params.setHostName(HostUtils.getHostName());
        this.registerSuccess = this.openApiService.registerExecutor(params);

        // 如果已经停止，则不再重试
        if (CronJobExecutorClient.getInstance().isShutdown()) {
            logger.warn("cronjob executor is shutdown, don't retry register executor, address:{}, config:{}", address, config);
            return;
        }

        if (!this.registerSuccess) {
            // 如果注册不成功，则重试一次
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
            this.register(config, address);
        }
    }

    /**
     * 构建参数
     *
     * @param config
     * @return
     */
    private ExecutorRegisterParams buildExecutorParams(ExecutorConfig config) {
        ExecutorRegisterParams params = new ExecutorRegisterParams();
        params.setTenant(config.getTenant());
        params.setAppName(config.getAppName());
        params.setAppDesc(config.getAppDesc());
        params.setTag(config.getTag());
        params.setVersion("Java-" + Constants.VERSION);
        return params;
    }

    /**
     * 注销执行器，也就是将该执行器置为下线状态
     *
     * @param address 执行器地址
     */
    public void unregister(String address) {
        this.registerSuccess = false;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address", address);
        this.openApiService.unregisterExecutor(jsonObject.toJSONString());
    }
}