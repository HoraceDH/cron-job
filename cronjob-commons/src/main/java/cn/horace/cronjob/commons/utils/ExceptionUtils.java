package cn.horace.cronjob.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Horace
 */
public class ExceptionUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

    /**
     * 获取异常堆栈信息
     *
     * @param throwable 异常对象
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        StringBuilder failedReasonBuilder = new StringBuilder();
        Throwable temp = throwable;
        while (temp != null) {
            failedReasonBuilder.append(temp).append(": ").append(temp.getMessage());
            failedReasonBuilder.append("\n");
            for (StackTraceElement stackTraceElement : temp.getStackTrace()) {
                failedReasonBuilder.append("\tat ");
                failedReasonBuilder.append(stackTraceElement);
                failedReasonBuilder.append("\n");
            }
            temp = temp.getCause();
        }
        return failedReasonBuilder.toString();
    }
}