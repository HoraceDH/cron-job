package cn.horace.cronjob.executor.bean;

/**
 *
 * @author Horace
 */
public class ExecutorRegisterParams {
    /**
     * 租户编码
     */
    private String tenant;
    /**
     * 应用名，一般英文代号
     */
    private String appName;
    /**
     * 应用描述，一般是应用中文名称
     */
    private String appDesc;
    /**
     * 主机名
     */
    private String hostName;
    /**
     * 标签
     */
    private String tag;
    /**
     * 执行器SDK版本
     */
    private String version;
    /**
     * 执行器地址，host:port
     */
    private String address;

    public ExecutorRegisterParams() {
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ExecutorRegisterParams{" +
                "tenant='" + tenant + '\'' +
                ", appName='" + appName + '\'' +
                ", appDesc='" + appDesc + '\'' +
                ", hostName='" + hostName + '\'' +
                ", tag='" + tag + '\'' +
                ", version='" + version + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}