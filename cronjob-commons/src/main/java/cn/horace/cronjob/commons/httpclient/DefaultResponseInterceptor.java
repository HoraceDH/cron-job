package cn.horace.cronjob.commons.httpclient;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 * @author Horace
 */
public class DefaultResponseInterceptor implements HttpResponseInterceptor {
    private static Logger logger = LoggerFactory.getLogger(DefaultResponseInterceptor.class);

    @Override
    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
        LogHelper.printLog(context, null);
    }
}