package cn.horace.cronjob.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态码
 * <p>
 *
 * @author Horace
 */
public enum MsgCodes {
    /**
     * 成功
     */
    SUCCESS(0, ""),

    /**
     * 参数错误
     */
    ERROR_PARAMS(1, "参数错误！"),

    /**
     * 协议错误
     */
    ERROR_OP(2, "协议错误！"),
    /**
     * 用户不存在
     */
    ERROR_USER_NOT_FOUND(3, "用户不存在！"),
    /**
     * 用户密码不正确
     */
    ERROR_USER_PASSWORD(4, "登录失败，用户名或密码错误！"),
    /**
     * 用户Token已失效
     */
    ERROR_USER_INVALID_TOKEN(5, "登录状态失效，请重新登录！"),
    /**
     * 签名错误
     */
    ERROR_SIGN(6, "非法请求！"),
    /**
     * 签名错误
     */
    ERROR_PERMISSION(7, "您的权限不够，请联系管理员！"),
    /**
     * 用户名已存在
     */
    ERROR_USER_NAME_REPEATED(8, "用户名已存在！"),
    /**
     * 用户已被禁用
     */
    ERROR_USER_DISABLED(9, "用户已被禁用！"),
    /**
     * 禁止操作管理员账户
     */
    ERROR_OPERATE_ADMIN_ACCOUNT(10, "禁止操作管理员账户！"),
    /**
     * 记录不存在
     */
    ERROR_NOT_FOUND_RECORD(11, "记录不存在！"),
    /**
     * 更新失败
     */
    ERROR_UPDATE_FAILED(12, "更新失败！"),
    /**
     * Cron表达式为空或语法错误
     */
    ERROR_CRON_EXPRESSION(13, "Cron表达式为空或语法错误！"),
    /**
     * 请求方法不支持
     */
    ERROR_METHOD_NOT_SUPPORTED(14, "请求方法不支持"),
    /**
     * 执行器已关闭
     */
    ERROR_EXECUTE_SHUTDOWN(15, "执行器已关闭"),
    /**
     * 查询范围超限
     */
    ERROR_QUERY_RANGE(16, "查询范围超限"),
    /**
     * 外部调用失败
     */
    ERROR_EXTERNAL(17, "外部调用异常"),

    /**
     * 系统错误
     */
    ERROR_SYSTEM(1000, "系统错误！"),

    ;
    private int code;
    private String msg;

    private static Map<Integer, MsgCodes> map = new HashMap<>();

    static {
        for (MsgCodes object : MsgCodes.values()) {
            map.put(object.getCode(), object);
        }
    }

    MsgCodes(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * 根据值类型找枚举
     *
     * @param value 值
     * @return
     */
    public static MsgCodes from(int value) {
        return map.get(value);
    }
}