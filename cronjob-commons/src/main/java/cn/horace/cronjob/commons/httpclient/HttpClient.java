package cn.horace.cronjob.commons.httpclient;

import cn.horace.cronjob.commons.bean.MsgObject;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Horace
 */
public class HttpClient implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private static volatile HttpClient INSTANCE;
    private CloseableHttpClient httpClient;

    private HttpClient(HttpClientBuilder httpClientBuilder) {
        this.httpClient = httpClientBuilder.build();
    }

    /**
     * 获取客户端构建器
     *
     * @param connectionManager    连接管理对象
     * @param requestConfigBuilder 请求配置对象
     * @param signKey              签名key
     * @return
     */
    public static HttpClientBuilder getHttpClientBuilder(PoolingHttpClientConnectionManager connectionManager, RequestConfig.Builder requestConfigBuilder, String signKey) {
        if (connectionManager == null) {
            connectionManager = getConnectionManager();
        }
        if (requestConfigBuilder == null) {
            requestConfigBuilder = getRequestConfigBuilder();
        }
        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                // 设置连接池
                .setConnectionManager(connectionManager)
                // 设置连接配置
                .setDefaultRequestConfig(requestConfigBuilder.build())
                // 设置连接的最长存活时间
                .setConnectionTimeToLive(5, TimeUnit.MINUTES).setRetryHandler(new DefaultHttpRequestRetryHandlerX(0, true)).setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

        // 设置请求拦截器
        httpClientBuilder.addInterceptorFirst(new DefaultRequestInterceptor(signKey));
        httpClientBuilder.addInterceptorFirst(new DefaultResponseInterceptor());
        return httpClientBuilder;
    }

    /**
     * 获取客户端构建器
     *
     * @return
     */
    public static HttpClientBuilder getHttpClientBuilder(String signKey) {
        return getHttpClientBuilder(null, null, signKey);
    }

    /**
     * 获取默认的连接池
     *
     * @return
     */
    public static PoolingHttpClientConnectionManager getConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // 最大总连接数
        connectionManager.setMaxTotal(1000);
        // 设置路由的最大连接数
        connectionManager.setDefaultMaxPerRoute(300);
        // 设置某个路由的最大连接数
        //connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("", 80)), 10);
        // 定义多长时间不活跃的连接，需要验证有效性
        connectionManager.setValidateAfterInactivity((int) TimeUnit.SECONDS.toMillis(3));

        new Timer("httpclient-idle-check", true).schedule(new TimerTask() {
            @Override
            public void run() {
                connectionManager.closeExpiredConnections();
                connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
                PoolStats stats = connectionManager.getTotalStats();
                logger.debug("http client connection stats:{}", stats);
            }
        }, 0, 5000);
        return connectionManager;
    }

    /**
     * 获取默认的请求配置
     *
     * @return
     */
    public static RequestConfig.Builder getRequestConfigBuilder() {
        // 请求配置
        return RequestConfig.custom()
                // 从连接池获取连接的超时时间
                .setConnectionRequestTimeout(3000)
                // 连接超时时间
                .setConnectTimeout(3000)
                // 等待回应超时时间
                .setSocketTimeout(5000)
                // 设置最大重定向次数
                .setMaxRedirects(10)
                //.setLocalAddress() // 本机存在多个网卡的情况下，用哪个本地网卡连接目标地址
                //.setProxy() // 设置代理服务器
                ;
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static HttpClient getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = init(getHttpClientBuilder(""));
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param httpClientBuilder 客户端构建器
     * @return
     */
    public static HttpClient init(HttpClientBuilder httpClientBuilder) {
        if (INSTANCE == null) {
            synchronized (HttpClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpClient(httpClientBuilder);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void close() {
        try {
            this.httpClient.close();
            logger.info("close http client");
        } catch (Exception e) {
            logger.error("close http client error, msg:{}", e.getMessage(), e);
        }
    }

    /**
     * 发送Http请求并返回一个结果
     *
     * @param request http请求
     * @return
     * @throws IOException
     */
    public CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
        return this.httpClient.execute(request);
    }

    /**
     * 发送POST请求
     *
     * @param uri     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @param entity  实体内容
     * @return
     */
    public byte[] post(String uri, Map<String, Object> params, Map<String, String> headers, byte[] entity) throws IOException {
        String queryString = QueryStringUtils.toQueryString(params);
        uri += queryString;
        HttpPost post = new HttpPost(uri);

        // 添加公共头
        Map<String, String> commonHeaders = HeaderUtils.getCommonHeaders();
        for (Map.Entry<String, String> entry : commonHeaders.entrySet()) {
            post.addHeader(entry.getKey(), entry.getValue());
        }

        // 设置头信息
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 设置实体数据
        if (entity != null) {
            ByteArrayEntity arrayEntity = new ByteArrayEntity(entity);
            arrayEntity.setContentType("UTF-8");
            post.setEntity(arrayEntity);
        }

        // 发送请求，结束后确保在任何时候都能归还连接
        try (CloseableHttpResponse response = this.execute(post)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("http post error, status:" + response.getStatusLine() + ", uri:" + uri + ", params:" + params);
            }
            return EntityUtils.toByteArray(response.getEntity());
        }
    }

    /**
     * 发送POST请求
     *
     * @param uri     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @param entity  实体内容
     * @return
     */
    public String postString(String uri, Map<String, Object> params, Map<String, String> headers, String entity) throws IOException {
        byte[] result = this.post(uri, params, headers, entity.getBytes());
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * 发送POST请求
     *
     * @param uri     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @param entity  实体内容
     * @return
     */
    public MsgObject postMsgObject(String uri, Map<String, Object> params, Map<String, String> headers, String entity) throws IOException {
        String result = this.postString(uri, params, headers, entity);
        if (StringUtils.isBlank(result)) {
            String msg = "post msg object error, result is blank, uri:" + uri + ", params:" + params + ", headers:" + headers + ", entity:" + entity;
            logger.error(msg);
            throw new RuntimeException(msg);
        }
        return JSONObject.parseObject(result, MsgObject.class);
    }
}