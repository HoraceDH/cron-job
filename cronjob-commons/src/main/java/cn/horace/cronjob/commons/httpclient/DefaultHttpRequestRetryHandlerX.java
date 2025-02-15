package cn.horace.cronjob.commons.httpclient;

import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Horace
 */
public class DefaultHttpRequestRetryHandlerX extends DefaultHttpRequestRetryHandler {
    protected DefaultHttpRequestRetryHandlerX(int retryCount, boolean requestSentRetryEnabled, Collection<Class<? extends IOException>> clazzes) {
        super(retryCount, requestSentRetryEnabled, clazzes);
    }

    public DefaultHttpRequestRetryHandlerX(int retryCount, boolean requestSentRetryEnabled) {
        super(retryCount, requestSentRetryEnabled);
    }

    public DefaultHttpRequestRetryHandlerX() {
        super();
    }

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        LogHelper.printLog(context, exception);
        return super.retryRequest(exception, executionCount, context);
    }
}