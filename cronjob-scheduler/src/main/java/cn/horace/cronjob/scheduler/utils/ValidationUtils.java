package cn.horace.cronjob.scheduler.utils;

import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.commons.constants.MsgCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.*;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Horace
 */
public class ValidationUtils {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtils.class);
    private static Validator VALIDATOR;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        VALIDATOR = factory.getValidator();
    }

    /**
     * 校验参数
     *
     * @param params 参数对象
     * @return
     */
    public static Result<Void> validate(Object params) {
        Result<Void> result = Result.success();
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<Object>> validate = VALIDATOR.validate(params);
        for (ConstraintViolation<Object> violation : validate) {
            String message = violation.getMessage();
            Path path = violation.getPropertyPath();
            sb.append("[").append(path).append("]").append(message).append(", ");
        }

        // 如果有错误消息
        if (sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length());
            result = Result.msgCodes(MsgCodes.ERROR_PARAMS);
            result.setMsg(sb.toString());
        }
        return result;
    }

    /**
     * 校验参数
     *
     * @param params 参数集合
     * @return
     */
    public static Result<Void> validate(List<?> params) {
        if (params == null || params.isEmpty()) {
            return Result.msgCodes(MsgCodes.ERROR_PARAMS);
        }
        Result<Void> result = Result.success();
        for (Object param : params) {
            Result<Void> temp = validate(param);
            if (!temp.isSuccess()) {
                return temp;
            }
        }
        return result;
    }
}