package cn.horace.cronjob.commons.httpclient;

import cn.horace.cronjob.commons.utils.SignUtils;
import org.apache.http.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Horace
 */
public class DefaultRequestInterceptor implements HttpRequestInterceptor {
    private static Logger logger = LoggerFactory.getLogger(DefaultRequestInterceptor.class);
    private String signKey;

    public DefaultRequestInterceptor(String signKey) {
        this.signKey = signKey;
    }

    /**
     * Processes a request.
     * On the client side, this step is performed before the request is
     * sent to the server. On the server side, this step is performed
     * on incoming messages before the message body is evaluated.
     *
     * @param request the request to preprocess
     * @param context the context for the request
     * @throws HttpException in case of an HTTP protocol violation
     * @throws IOException   in case of an I/O error
     */
    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        HttpClientContext clientContext = (HttpClientContext) context;
        long currentTimeMillis = System.currentTimeMillis();
        clientContext.setAttribute("interceptor.current_time_millis", currentTimeMillis);

        // 获取Body
        String body = "";
        Map<String, String> params = new HashMap<>();
        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntityEnclosingRequest entityRequest = (HttpEntityEnclosingRequest) request;
            HttpEntity entity = entityRequest.getEntity();
            if (entity != null) {
                body = EntityUtils.toString(entity, "UTF-8");
            }
            String[] split = request.getRequestLine().getUri().split("\\?");
            if (split.length > 1) {
                params = QueryStringUtils.toParams(split[1]);
            }
            // 参数签名，并设置Header头
            String token = "not-need-token";
            String sign = SignUtils.sign(this.signKey, token, String.valueOf(currentTimeMillis), body, params);
            entityRequest.addHeader("sign", sign);
            entityRequest.addHeader("times", currentTimeMillis + "");
            entityRequest.addHeader("token", token);
        } else {
            logger.error("httpclient interceptor error, requestClass:{}, request:{}", request.getClass(), request);
        }
    }
}