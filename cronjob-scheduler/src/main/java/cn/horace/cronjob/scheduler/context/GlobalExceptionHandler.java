package cn.horace.cronjob.scheduler.context;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.constants.MsgCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 *
 * @author Horace
 */
@Controller
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 全局异常处理
     *
     * @param e
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public MsgObject handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        WebContext.Context context = WebContext.getContext();
        MsgObject msgObject = null;
        if (e instanceof HttpRequestMethodNotSupportedException) {
            msgObject = MsgObject.msgCodes(MsgCodes.ERROR_METHOD_NOT_SUPPORTED);
        }

        // ....

        if (msgObject == null) {
            msgObject = MsgObject.msgCodes(MsgCodes.ERROR_SYSTEM);
        }
        logger.error("request exception, uri:{}, params:{}, body:{}, result:{}, msg:{}", request.getRequestURI(), context.getParams(), context.getBody(), msgObject, e.getMessage(), e);
        return msgObject;
    }
}