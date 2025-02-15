package cn.horace.cronjob.commons.httpclient;

import cn.horace.cronjob.commons.utils.IOUtils;
import org.apache.http.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 * @author Horace
 */
public class LogHelper {
    private static Logger logger = LoggerFactory.getLogger(LogHelper.class);

    /**
     * 打印日志
     *
     * @param context
     * @param e
     */
    public static void printLog(HttpContext context, Exception e) {
        HttpClientContext clientContext = (HttpClientContext) context;
        HttpRequest request = clientContext.getRequest();
        long startTime = (long) clientContext.getAttribute("interceptor.current_time_millis");

        HttpHost targetHost = clientContext.getTargetHost();
        String uri = targetHost.getSchemeName() + "://" + targetHost.getHostName();
        if (targetHost.getPort() > 0) {
            uri += ":" + targetHost.getPort();
        }
        uri += request.getRequestLine().getUri();

        HttpResponse response = clientContext.getResponse();
        int statusCode = -1;
        if (response != null) {
            statusCode = response.getStatusLine().getStatusCode();
        }

        Header[] allHeaders = request.getAllHeaders();
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;

        String entity = null;
        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntityEnclosingRequest enclosingRequest = (HttpEntityEnclosingRequest) request;
            HttpEntity httpEntity = enclosingRequest.getEntity();
            if (httpEntity != null) {
                try {
                    byte[] requestData = IOUtils.copy(httpEntity.getContent());
                    entity = new String(requestData);
                } catch (IOException ex) {
                    logger.warn("httpclient get request entity error, uri:{}, msg:{}", uri, ex.getMessage(), ex);
                }
            }
        }

        boolean showLog = statusCode != HttpStatus.SC_OK || useTime > 1000;

        if (showLog || e != null) {
            logger.info("httpclient request, code:{}, use {}ms, uri:{}, headers:{}, entity:{}, requestConfig:{}", statusCode, useTime, uri, allHeaders, entity, clientContext.getRequestConfig(), e);
        }
    }
}