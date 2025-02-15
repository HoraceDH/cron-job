package cn.horace.cronjob.commons.bean;

import cn.horace.cronjob.commons.constants.MsgCodes;
import org.apache.commons.lang3.StringUtils;

/**
 * 结果对象
 * <p>
 *
 * @author Horace
 */
public class Result<T> {
    /**
     * 状态码
     */
    private MsgCodes msgCodes;
    /**
     * 错误消息
     */
    private String msg;
    /**
     * 实际处理结果
     */
    private T data;

    /**
     * 成功
     *
     * @param data 实际数据
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setMsgCodes(MsgCodes.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setMsgCodes(MsgCodes.SUCCESS);
        result.setData(null);
        return result;
    }

    /**
     * 状态码
     *
     * @param msgCodes 状态码
     * @param <T>
     * @return
     */
    public static <T> Result<T> msgCodes(MsgCodes msgCodes) {
        Result<T> result = new Result<>();
        result.setMsgCodes(msgCodes);
        result.setData(null);
        return result;
    }

    public Result() {
    }

    public MsgCodes getMsgCodes() {
        return msgCodes;
    }

    public void setMsgCodes(MsgCodes msgCodes) {
        this.msgCodes = msgCodes;
    }

    public String getMsg() {
        if (StringUtils.isNotBlank(this.msg)) {
            return msg;
        }
        return this.msgCodes.getMsg();
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msgCodes=" + msgCodes +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return this.msgCodes == MsgCodes.SUCCESS;
    }
}