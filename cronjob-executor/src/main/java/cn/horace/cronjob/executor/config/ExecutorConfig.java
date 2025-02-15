package cn.horace.cronjob.executor.config;

import java.util.ArrayList;

/**
 *
 * @author Horace
 */
public class ExecutorConfig {
    /**
     * 调度器地址，例如 http://localhost:1577
     */
    private String address;
    /**
     * 租户编码
     */
    private String tenant;
    /**
     * 应用名，一般英文代号
     */
    private String appName;
    /**
     * 应用描述，应用中文名称
     */
    private String appDesc;
    /**
     * 标签
     */
    private String tag;
    /**
     * 签名Key
     */
    private String signKey;
    /**
     * 任务对象集合
     */
    private ArrayList<Object> taskObjects;

    public String getAddress() {
        return address;
    }

    public String getTenant() {
        return tenant;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public String getTag() {
        return tag;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public ArrayList<Object> getTaskObjects() {
        return taskObjects;
    }

    @Override
    public String toString() {
        return "ExecutorConfig{" +
                "address='" + address + '\'' +
                ", tenant='" + tenant + '\'' +
                ", appName='" + appName + '\'' +
                ", appDesc='" + appDesc + '\'' +
                ", tag='" + tag + '\'' +
                ", signKey='" + signKey + '\'' +
                ", taskObjects=" + taskObjects +
                '}';
    }

    public static class Builder {
        private String address;
        private String tenant;
        private String appName;
        private String appDesc;
        private String tag = "common";
        private String signKey = "7d890a079948b196756rtf5452d2245t";
        private ArrayList<Object> taskObjects;

        private Builder(ArrayList<Object> taskObjects) {
            this.taskObjects = taskObjects;
        }

        public static Builder newBuilder(ArrayList<Object> taskObjects) {
            return new Builder(taskObjects);
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder tenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public Builder appName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder appDesc(String appDesc) {
            this.appDesc = appDesc;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder signKey(String signKey) {
            this.signKey = signKey;
            return this;
        }

        public ExecutorConfig build() {
            ExecutorConfig executorConfig = new ExecutorConfig();
            executorConfig.address = this.address;
            executorConfig.tag = this.tag;
            executorConfig.signKey = this.signKey;
            executorConfig.appName = this.appName;
            executorConfig.tenant = this.tenant;
            executorConfig.appDesc = this.appDesc;
            executorConfig.taskObjects = this.taskObjects;
            return executorConfig;
        }
    }
}