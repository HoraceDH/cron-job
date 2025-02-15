package cn.horace.cronjob.executor.bean;

/**
 *
 * @author Horace
 */
public class ExecutorHeartbeatParams {
    /**
     * 执行器地址，host:port
     */
    private String address;

    public ExecutorHeartbeatParams() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}