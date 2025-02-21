package cn.horace.cronjob.scheduler.context;

import cn.horace.cronjob.scheduler.utils.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 上下文对象
 * <p>
 *
 * @author Horace
 */
public class WebContext {
    private static final Logger logger = LoggerFactory.getLogger(WebContext.class);
    private static ThreadLocal<Context> CONTEXTS = new ThreadLocal<>();

    /**
     * 初始化上下文对象
     * <p>
     * 同一包下可以访问该方法，其他包不能访问，因此需要将Filter放到同一个包下
     *
     * @param servletRequest Servlet请求对象
     */
    static Context init(ServletRequest servletRequest) {
        if (!(servletRequest instanceof HttpServletRequest)) {
            throw new IllegalArgumentException("The servletRequest parameter is null or illegal");
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 解析请求参数
        Map<String, String> params = new ConcurrentHashMap<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            if (name != null && value != null) {
                params.put(name, value);
            }
        }

        // 解析Header
        Map<String, String> headers = new ConcurrentHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            if (name != null && value != null) {
                headers.put(name, value);
            }
        }

        // 获取请求体
        String body = null;
        try {
            // 获取请求体
            ServletInputStream inputStream = request.getInputStream();
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            if (bytes.length > 0) {
                body = new String(bytes, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            logger.error("Failed to read the request body, msg:{}", e.getMessage(), e);
        }

        // 组装好Context对象后，放入ThreadLocal中，方便当前线程获取
        Context context = new Context(params, headers, body, request);
        CONTEXTS.set(context);
        logger.debug("Initializes the Web security component context, context:{}", context);
        return context;
    }

    /**
     * 获取上下文对象，注意只能在当前线程上下文中获取，子线程无法获得，注意判空
     *
     * @return
     */
    public static Context getContext() {
        return CONTEXTS.get();
    }

    /**
     * 上下文对象
     */
    public static class Context {
        private Map<String, String> params;
        private Map<String, String> headers;
        private String uri;
        private String body;
        private String token;
        private String realIp;
        private long userId = 0;
        private HttpServletRequest request;

        protected Context(Map<String, String> params, Map<String, String> headers, String body, HttpServletRequest request) {
            this.params = params;
            this.headers = headers;
            this.body = body;
            this.request = request;
            this.uri = request.getRequestURI();
            this.token = headers.get("token");
            // 获取IP地址
            this.realIp = IPUtils.getRealIp(request);
        }

        public Map<String, String> getParams() {
            return params;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public String getUri() {
            return uri;
        }

        public String getBody() {
            return body;
        }

        public String getToken() {
            return token;
        }

        public String getRealIp() {
            return realIp;
        }

        public long getUserId() {
            return userId;
        }

        protected void setUserId(long userId) {
            this.userId = userId;
        }

        public HttpServletRequest getRequest() {
            return request;
        }

        @Override
        public String toString() {
            return "Context{" +
                    "params=" + params +
                    ", headers=" + headers +
                    ", uri='" + uri + '\'' +
                    ", body='" + body + '\'' +
                    ", realIp='" + realIp + '\'' +
                    ", userId=" + userId +
                    '}';
        }
    }
}