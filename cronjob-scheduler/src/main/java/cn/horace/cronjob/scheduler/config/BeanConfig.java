package cn.horace.cronjob.scheduler.config;

import cn.horace.cronjob.commons.GuidGenerate;
import cn.horace.cronjob.commons.cron.CronExpression;
import cn.horace.cronjob.commons.httpclient.HttpClient;
import cn.horace.cronjob.scheduler.interceptor.DataSourceInterceptor;
import cn.horace.cronjob.scheduler.interceptor.SqlMonitorFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Collections;

/**
 * Bean相关配置
 * <p>
 *
 * @author Horace
 */
@Configuration
public class BeanConfig {
    private static final Logger logger = LoggerFactory.getLogger(BeanConfig.class);
    @Resource
    private AppConfig appConfig;
    @Value("${database.driverClassName}")
    private String driverClassName;
    @Value("${database.url}")
    private String url;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;
    @Value("${database.initialSize}")
    private int initialSize;
    @Value("${database.maxActive}")
    private int maxActive;
    @Value("${database.connectTimeout}")
    private int connectTimeout;
    @Value("${database.socketTimeout}")
    private int socketTimeout;

    /**
     * 初始化HttpClient
     *
     * @return
     */
    @PostConstruct
    public HttpClient httpClient() {
        return HttpClient.init(HttpClient.getHttpClientBuilder(this.appConfig.getExecutorSignKey()));
    }

    /**
     * 表达式验证器
     *
     * @return
     */
    @Bean
    public CronExpression cronExpression() {
        return CronExpression.getInstance();
    }

    /**
     * 全局唯一ID
     *
     * @return
     */
    @Bean
    public GuidGenerate guidGenerate() {
        return new GuidGenerate(this.appConfig.getServerId());
    }

    /**
     * 数据源拦截器
     *
     * @return
     */
    @Bean
    public DataSourceInterceptor dataSourceInterceptor() {
        return new DataSourceInterceptor();
    }

    /**
     * 数据源设置
     *
     * @return
     */
    @Bean
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setInitialSize(this.initialSize);
        dataSource.setMaxActive(this.maxActive);
        dataSource.setConnectTimeout(this.connectTimeout);
        dataSource.setSocketTimeout(this.socketTimeout);
        dataSource.setProxyFilters(Collections.singletonList(new SqlMonitorFilter()));
        dataSource.init();
        DataSourceInterceptor dataSourceInterceptor = this.dataSourceInterceptor();
        dataSourceInterceptor.setDataSource(dataSource);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DruidDataSource.class);
        enhancer.setCallback(dataSourceInterceptor);
        return (DruidDataSource) enhancer.create();
    }

    /**
     * Session工厂
     *
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(this.dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:sqlmappers/*Mapper.xml"));
        return bean;
    }
}