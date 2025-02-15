package cn.horace.cronjob.scheduler.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

/**
 *
 * @author Horace
 */
public class WebContextRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger logger = LoggerFactory.getLogger(WebContextRequestWrapper.class);
    private byte[] bodyBytes;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public WebContextRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
    }

    /**
     * The default behavior of this method is to return getHeader(String name)
     * on the wrapped request object.
     *
     * @param name
     */
    @Override
    public String getHeader(String name) {
        return super.getHeader(name);
    }

    /**
     * The default behavior of this method is to return getHeaders(String name)
     * on the wrapped request object.
     *
     * @param name
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        return super.getHeaders(name);
    }

    /**
     * The default behavior of this method is to return getHeaderNames() on the
     * wrapped request object.
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return super.getHeaderNames();
    }

    /**
     * The default behavior of this method is to return getReader() on the
     * wrapped request object.
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    /**
     * The default behavior of this method is to return getInputStream() on the
     * wrapped request object.
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(this.bodyBytes);
        return new CachedInputStream(inputStream);
    }

    public static class CachedInputStream extends ServletInputStream {
        private final ByteArrayInputStream inputStream;

        public CachedInputStream(ByteArrayInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return this.inputStream.read();
        }
    }
}