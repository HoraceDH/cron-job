package cn.horace.cronjob.scheduler.interceptor;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Horace
 */
public class SqlMonitorFilter extends FilterEventAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SqlMonitorFilter.class);
    /**
     * 慢SQL超时时间
     */
    private int slowSqlTimeout;

    public SqlMonitorFilter() {
        this(1000);
    }

    public SqlMonitorFilter(int slowSqlTimeout) {
        this.slowSqlTimeout = slowSqlTimeout;
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection, String sql) throws SQLException {
        sql = this.compressSql(sql);
        PreparedStatementProxy proxy;
        try {
            proxy = super.connection_prepareStatement(chain, connection, sql);
        } catch (Exception e) {
            logger.error("prepared statement error, sql:{}, msg:{}", sql, e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return proxy;
    }

    @Override
    public boolean preparedStatement_execute(FilterChain chain, PreparedStatementProxy statement) throws SQLException {
        Map<Integer, JdbcParameter> parameters = statement.getParameters();
        String url = statement.getConnectionProxy().getDirectDataSource().getUrl();
        String sql = statement.getSql();
        boolean success;
        try {
            long startTime = System.currentTimeMillis();
            success = super.preparedStatement_execute(chain, statement);
            long endTime = System.currentTimeMillis();
            if (endTime - startTime >= this.slowSqlTimeout) {
                logger.warn("monitor slow sql, elapsed:{}ms, url:{}, sql:{}, params:{}", (endTime - startTime), url, sql, this.getParams(parameters));
            }
        } catch (SQLException e) {
            logger.error("sql statement execute error, url:{}, sql:{}, params:{}, msg:{}", url, sql, this.getParams(parameters), e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return success;
    }

    /**
     * 获取参数列表
     *
     * @param parameters 参数集合
     * @return
     */
    private List<Object> getParams(Map<Integer, JdbcParameter> parameters) {
        List<Object> params = new ArrayList<>();
        for (JdbcParameter value : parameters.values()) {
            params.add(value.getValue());
        }
        return params;
    }

    /**
     * 压缩SQL
     *
     * @param sql SQL语句
     * @return
     */
    private String compressSql(String sql) {
        sql = sql.replace("\n", " ");
        sql = sql.replaceAll("\\s+", " ");
        return sql;
    }
}