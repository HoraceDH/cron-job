package cn.horace.cronjob.executor.bean;

import cn.horace.cronjob.executor.constants.ResultCode;

/**
 * Created in 2025-01-10 12:01.
 *
 * @author Horace
 */
public class HandlerResult {
    private int code;
    private String msg;

    public HandlerResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public HandlerResult(ResultCode resultCode) {
        this.code = resultCode.getValue();
        this.msg = resultCode.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 成功的结果
     *
     * @return
     */
    public static HandlerResult success() {
        return new HandlerResult(ResultCode.SUCCESS);
    }

    /**
     * 失败结果
     *
     * @param msg 错误信息可以在调度平台查看
     * @return
     */
    public static HandlerResult failed(String msg) {
        HandlerResult result = new HandlerResult(ResultCode.FAILED);
        result.setMsg(msg);
        return result;
    }

    /**
     * 失败结果，未指定错误信息，则使用默认的错误信息
     *
     * @return
     */
    public static HandlerResult failed() {
        return new HandlerResult(ResultCode.FAILED);
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return this.code == ResultCode.SUCCESS.getValue();
    }
}