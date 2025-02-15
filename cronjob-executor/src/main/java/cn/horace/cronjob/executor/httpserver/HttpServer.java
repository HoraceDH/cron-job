package cn.horace.cronjob.executor.httpserver;

import cn.horace.cronjob.commons.thread.DefaultThreadFactory;
import cn.horace.cronjob.commons.utils.IPUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.BindException;

/**
 * @author Horace
 */
public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private static HttpServer INSTANCE = new HttpServer();
    private volatile boolean started = false;
    private String host;
    private int port;

    private HttpServer() {
        this.host = IPUtils.getLocalIpAddress();
        this.port = 8527;
    }

    public boolean isStarted() {
        return started;
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static HttpServer getInstance() {
        return INSTANCE;
    }

    /**
     * 获取地址本机监听的地址
     *
     * @return
     */
    public String getAddress() {
        return host + ":" + port;
    }

    /**
     * 启动服务
     */
    public void start() {
        if (this.started) {
            return;
        }

        Thread thread = new Thread(this::startJettyServer, "cron-job-http-server");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 启动Jetty服务器
     */
    private void startJettyServer() {
        try {
            int maxThreads = 100;
            int minThreads = 5;
            // 空闲线程的超时时间（秒）
            int idleTimeout = 120;
            BlockingArrayQueue<Runnable> queue = new BlockingArrayQueue<>(1024);
            DefaultThreadFactory threadFactory = new DefaultThreadFactory(true, "cron-job-http-task");
            QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout, -1, queue, null, threadFactory);
            // 配置ServerConnector
            Server server = new Server(threadPool);
            ServerConnector connector = new ServerConnector(server);
            connector.setHost(this.host);
            connector.setPort(this.port);
            server.addConnector(connector);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);
            context.addServlet(new ServletHolder(new ExecutorServlet()), "/dispatch");
            server.start();
            logger.info("cron job web server started, address:{}", this.getAddress());
            this.started = true;
            server.join();
        } catch (Exception e) {
            // 端口被占用，则端口递增继续启动
            if (e.getMessage().contains("Failed to bind") || e.getCause() instanceof BindException) {
                this.port += 1;
                logger.info(e.getMessage() + ", increment port {}", this.port);
                this.startJettyServer();
                return;
            }
            throw new RuntimeException(e);
        }
    }
}