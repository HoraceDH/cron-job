package cn.horace.cronjob.examples;

import cn.horace.cronjob.examples.tasks.DemoCronTask;
import cn.horace.cronjob.executor.CronJobExecutorClient;
import cn.horace.cronjob.executor.config.ExecutorConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * 示例执行器
 * <p>
 *
 * @author Horace
 */
public class ExampleExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ExampleExecutor.class);

    public static void main(String[] args) {
        String cronServerAddress = System.getProperty("cronjob.server.address");
        if (StringUtils.isBlank(cronServerAddress)) {
            cronServerAddress = "http://127.0.0.1:9527";
        }
        String tenant = System.getProperty("cronjob.tenant.name");
        if (StringUtils.isBlank(tenant)) {
            tenant = "horace";
        }
        String appName = System.getProperty("cronjob.app.name");
        if (StringUtils.isBlank(appName)) {
            appName = "example-executor";
        }

        ArrayList<Object> taskObjects = new ArrayList<>();
        taskObjects.add(new DemoCronTask());
        ExecutorConfig config = ExecutorConfig.Builder.newBuilder(taskObjects)
                .address(cronServerAddress)
                .tenant(tenant)
                .appName(appName)
                .appDesc("普通示例执行器")
                .tag("common")
                .signKey("7d890a079948b196756rtf5452d2245t")
                .build();
        CronJobExecutorClient.init(config).start();
    }
}