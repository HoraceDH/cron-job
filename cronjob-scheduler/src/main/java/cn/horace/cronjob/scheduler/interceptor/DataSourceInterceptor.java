package cn.horace.cronjob.scheduler.interceptor;

import cn.horace.cronjob.scheduler.bean.EventObject;
import cn.horace.cronjob.scheduler.constants.EventType;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 数据源拦截器
 * <p>
 *
 * @author Horace
 */
public class DataSourceInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceInterceptor.class);
    private DruidDataSource dataSource;

    /**
     * 设置数据源
     *
     * @param dataSource
     */
    public void setDataSource(DruidDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 关闭数据源
     */
    @Subscribe
    @AllowConcurrentEvents
    public void closeDataSource(EventObject eventObject) {
        if (eventObject.getType() == EventType.TASK_QUEUE_TO_DATABASE) {
            logger.info("received event, start closing data source, event:{}", eventObject);
            this.dataSource.close();
        }
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result;
        if ("close".equals(method.getName())) {
            // 不做处理，等待关闭信号后再做关闭
            return null;
        }
        result = method.invoke(dataSource, objects);
        return result;
    }
}