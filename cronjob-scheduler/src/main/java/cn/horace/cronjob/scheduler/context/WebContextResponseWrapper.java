package cn.horace.cronjob.scheduler.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 *
 * @author Horace
 */
public class WebContextResponseWrapper extends HttpServletResponseWrapper {
    private static final Logger logger = LoggerFactory.getLogger(WebContextResponseWrapper.class);

    public WebContextResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    /**
     * The default behavior of this method is to return setHeader(String name,
     * String value) on the wrapped response object.
     *
     * @param name
     * @param value
     */
    @Override
    public void setHeader(String name, String value) {
        super.setHeader(name, value);
    }

    /**
     * The default behavior of this method is to return addHeader(String name,
     * String value) on the wrapped response object.
     *
     * @param name
     * @param value
     */
    @Override
    public void addHeader(String name, String value) {
        super.addHeader(name, value);
    }
}