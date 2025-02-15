package cn.horace.cronjob.executor.starter.config;

/**
 * Created in 2024-11-24 17:47.
 *
 * @author Horace
 */
public class BaseConfig {
    /**
     * 调度器地址，例如：http://127.0.0.1:9527
     */
    protected String address;
    /**
     * 租户名称
     */
    protected String tenant;
    /**
     * 应用名称
     */
    protected String appName;
    /**
     * 应用描述
     */
    protected String appDesc;
    /**
     * 标签
     */
    protected String tag;
    /**
     * 签名Key
     */
    protected String signKey;

    public BaseConfig() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }
}