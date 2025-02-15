package cn.horace.cronjob.commons.cron;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cron 表达式
 *
 * @author Horace
 */
public class CronExpression {
    private static final Logger logger = LoggerFactory.getLogger(CronExpression.class);
    private static final CronExpression INSTANCE = new CronExpression();
    private final CronDefinition definition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
    private final CronParser cronParser = new CronParser(definition);
    private ConcurrentHashMap<String, ExecutionTime> executionTimes = new ConcurrentHashMap<>();

    private CronExpression() {
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static CronExpression getInstance() {
        return INSTANCE;
    }

    /**
     * 是否是有效的CRON表达式
     *
     * @param cron 表达式
     * @return
     */
    public boolean isValid(String cron) {
        if (StringUtils.isBlank(cron)) {
            return false;
        }
        try {
            cronParser.parse(cron).validate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取后续几次的执行时间
     *
     * @param cron cron 表达式
     * @return
     */
    public List<Date> getNextExecutionTime(String cron, int count) {
        List<Date> result = new ArrayList<>();
        ExecutionTime executionTime = this.getExecutionTime(cron);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        for (int i = 0; i < count; i++) {
            long epochMilli = executionTime.nextExecution(zonedDateTime).get().toInstant().toEpochMilli();
            // 去除毫秒级，只精确到秒级
            epochMilli = epochMilli / 1000 * 1000;
            result.add(new Date(epochMilli));
            zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault());
        }
        return result;
    }

    /**
     * 获取下次的执行时间
     *
     * @param cron      cron 表达式
     * @param timeMilli 基准时间戳
     * @return
     */
    public Date getNextExecutionTime(String cron, long timeMilli) {
        ExecutionTime executionTime = this.getExecutionTime(cron);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timeMilli), ZoneId.systemDefault());
        long epochMilli = executionTime.nextExecution(zonedDateTime).get().toInstant().toEpochMilli();
        // 去除毫秒级，只精确到秒级
        epochMilli = epochMilli / 1000 * 1000;
        return new Date(epochMilli);
    }

    /**
     * 获取执行时间对象
     *
     * @param cron cron表达式
     * @return
     */
    private ExecutionTime getExecutionTime(String cron) {
        ExecutionTime executionTime = this.executionTimes.get(cron);
        if (executionTime == null) {
            // 控制最小的并发度
            synchronized (cron.intern()) {
                executionTime = this.executionTimes.get(cron);
                if (executionTime == null) {
                    executionTime = ExecutionTime.forCron(cronParser.parse(cron));
                    this.executionTimes.put(cron, executionTime);
                }
            }
        }
        return executionTime;
    }
}