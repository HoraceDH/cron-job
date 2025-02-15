package cn.horace.cronjob.commons.bean;

import cn.horace.cronjob.commons.constants.MsgCodes;

/**
 * 消息对象
 * <p>
 *
 * @author Horace
 */
public class MsgObject {
    private int code;
    private String msg;
    private Object data;

    public MsgObject(MsgCodes msgCodes) {
        this.code = msgCodes.getCode();
        this.msg = msgCodes.getMsg();
    }

    /**
     * 成功消息
     *
     * @return
     */
    public static MsgObject success() {
        return new MsgObject(MsgCodes.SUCCESS.getCode(), MsgCodes.SUCCESS.getMsg());
    }

    /**
     * 成功消息
     *
     * @return
     */
    public static MsgObject success(Object data) {
        MsgObject msgObject = new MsgObject(MsgCodes.SUCCESS.getCode(), MsgCodes.SUCCESS.getMsg());
        msgObject.setData(data);
        return msgObject;
    }

    /**
     * 状态码
     *
     * @param msgCodes 状态码
     * @return
     */
    public static MsgObject msgCodes(MsgCodes msgCodes) {
        return new MsgObject(msgCodes);
    }


    public MsgObject(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static MsgObject msgCodes(Result<Void> result) {
        MsgCodes msgCodes = result.getMsgCodes();
        return new MsgObject(msgCodes.getCode(), result.getMsg());
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MsgObject{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return this.code == MsgCodes.SUCCESS.getCode();
    }
}