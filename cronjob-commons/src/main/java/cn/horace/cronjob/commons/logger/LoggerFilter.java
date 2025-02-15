package cn.horace.cronjob.commons.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import cn.horace.cronjob.commons.config.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import static cn.horace.cronjob.commons.constants.Constants.ROOT_PACKAGE;

/**
 * 通过如下方式添加日志过滤器：
 * LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
 * context.addTurboFilter(new LoggerFilter());
 * <p>
 *
 * @author Horace
 */
public class LoggerFilter extends TurboFilter {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public FilterReply decide(Marker marker, ch.qos.logback.classic.Logger logger, Level level, String format, Object[] params, Throwable t) {
        if (level == Level.TRACE || level == Level.DEBUG || level == Level.INFO) {
            // 全局日志控制，避免每个打印日志的地方都做一次开关判断
            String name = logger.getName();
            if (name != null && name.startsWith(ROOT_PACKAGE)) {
                return GlobalConfig.isEnabledLog() ? FilterReply.NEUTRAL : FilterReply.DENY;
            }
        }
        return FilterReply.NEUTRAL;
    }
}