package cn.horace.cronjob.scheduler.context;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.constants.MsgCodes;
import cn.horace.cronjob.scheduler.constants.Constants;
import cn.horace.cronjob.scheduler.bean.result.MenuItem;
import cn.horace.cronjob.scheduler.service.MenuService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户权限过滤器过滤器
 * <p>
 *
 * @author Horace
 */
@Component
@Order(Integer.MIN_VALUE + 1)
@WebFilter(filterName = "UserPermissionsFilter", urlPatterns = "/*", asyncSupported = true)
public class UserPermissionsFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserPermissionsFilter.class);
    private List<String> defaultPaths = new ArrayList<>();
    @Resource
    private MenuService menuService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        defaultPaths.add("/manager-api/user/login");
        defaultPaths.add("/manager-api/user/logout");
        defaultPaths.add("/manager-api/user/getUser");
        defaultPaths.add("/manager-api/menu/getUserMenuList");
        defaultPaths.add("/manager-api/menu/getUserMenuIds");
        defaultPaths.add("/manager-api/tenant/getSearchList");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        WebContext.Context context = WebContext.getContext();
        String token = context.getToken();
        String uri = context.getUri();
        if (!uri.startsWith("/manager-api/")) {
            chain.doFilter(request, response);
            return;
        }

        boolean contains = this.defaultPaths.contains(uri);
        if (contains || context.getUserId() == Constants.ADMIN_USER_ID) {
            chain.doFilter(request, response);
            return;
        }
        List<MenuItem> menuList = this.menuService.getMenuList(token, false, true);
        boolean isHaveAccess = this.isHaveAccess(uri, menuList);
        if (!isHaveAccess) {
            MsgObject msgObject = MsgObject.msgCodes(MsgCodes.ERROR_PERMISSION);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(msgObject));
            // 没有权限访问
            logger.warn("permission verification failed, userId:{}, uri:{}, context:{}", context.getUserId(), context.getUri(), context);
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * 是否有权限访问
     *
     * @param uri      请求地址
     * @param menuList 菜单地址
     * @return
     */
    private boolean isHaveAccess(String uri, List<MenuItem> menuList) {
        for (MenuItem menuItem : menuList) {
            if (menuItem.getPath().equals(uri)) {
                return true;
            }
        }
        return false;
    }
}