package cn.horace.cronjob.scheduler.context;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.commons.utils.SignUtils;
import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.entities.TokenEntity;
import cn.horace.cronjob.scheduler.service.TokenService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 上下文过滤器
 * <p>
 *
 * @author Horace
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "WebContextFilter", urlPatterns = "/*", asyncSupported = true)
public class WebContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(WebContextFilter.class);
    @Resource
    private AppConfig appConfig;
    @Resource
    private TokenService tokenService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 替换Request对象，使用包装器
        WebContextRequestWrapper requestWrapper = new WebContextRequestWrapper((HttpServletRequest) request);
        WebContextResponseWrapper responseWrapper = new WebContextResponseWrapper((HttpServletResponse) response);
        // 初始化上下文对象
        WebContext.Context context = WebContext.init(requestWrapper);
        if (!context.getUri().contains("/heartbeat")) {
            logger.debug("web context received request, uri:{}, context:{}", context.getUri(), context);
        }

        /************** OpenApi接口拦截 ********************/
        String uri = ((HttpServletRequest) request).getRequestURI();
        if (uri.startsWith("/openapi/")) {
            // 签名验证不通过，则直接返回
            boolean success = this.verifySign(response, this.appConfig.getExecutorSignKey());
            if (!success) {
                return;
            }
            chain.doFilter(requestWrapper, responseWrapper);
            return;
        }

        /************** 管理后台接口拦截 ********************/

        //设置跨域头信息
        this.setCrossHeader(response);
        // 如果是跨域预检请求Options，则放行
        if ("OPTIONS".equalsIgnoreCase(requestWrapper.getMethod())) {
            chain.doFilter(requestWrapper, responseWrapper);
            return;
        }

        // 签名验证不通过，则直接返回
        boolean success = this.verifySign(response, this.appConfig.getManagerSignKey());
        if (!success) {
            return;
        }

        // 检测登录状态
        String token = WebContext.getContext().getToken();
        TokenEntity tokenEntity = this.tokenService.getToken(token);
        boolean isValid = this.tokenService.isValid(tokenEntity);
        if (!StringUtils.equals(uri, "/manager-api/user/login")) {
            if (!isValid) {
                MsgObject msgObject = MsgObject.msgCodes(MsgCodes.ERROR_USER_INVALID_TOKEN);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JSONObject.toJSONString(msgObject));
                logger.debug("web context, token is expired, uri:{}, context:{}", context.getUri(), context);
                return;
            }
            WebContext.getContext().setUserId(tokenEntity.getUserId());
        }

        // 打印操作日志
        logger.info("web context, request log, userId:{}, ip:{}, uri:{}, params:{}, body:{}", context.getUserId(), context.getRealIp(), context.getUri(), context.getParams(), context.getBody());
        chain.doFilter(requestWrapper, responseWrapper);
    }

    /**
     * 验证签名有效性
     *
     * @param response 响应对象
     * @param signKey  签名key
     * @return
     */
    private boolean verifySign(ServletResponse response, String signKey) throws IOException {
        WebContext.Context context = WebContext.getContext();
        String times = context.getHeaders().get("times");
        String serverSign = SignUtils.sign(signKey, context.getToken(), times, context.getBody(), context.getParams());
        String sign = context.getHeaders().get("sign");
        boolean success = serverSign.equals(sign);
        if (!success) {
            MsgObject msgObject = MsgObject.msgCodes(MsgCodes.ERROR_SIGN);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(msgObject));
            logger.warn("web context, params verification failed, uri:{}, context:{}", context.getUri(), context);
        }
        return success;
    }

    /**
     * 设置跨域头信息
     *
     * @param response
     */
    private void setCrossHeader(ServletResponse response) {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //通过在响应 header 中设置 ‘*’ 来允许来自所有域的跨域请求访问。
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            //请求方式
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
            //（预检请求）的返回结果（即 Access-Control-Allow-Methods 和Access-Control-Allow-Headers 提供的信息） 可以被缓存多久
            httpServletResponse.setHeader("Access-Control-Max-Age", "86400");
            //首部字段用于预检请求的响应。其指明了实际请求中允许携带的首部字段
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        }
    }
}