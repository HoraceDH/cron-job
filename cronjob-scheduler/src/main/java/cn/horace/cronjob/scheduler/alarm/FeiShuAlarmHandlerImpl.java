package cn.horace.cronjob.scheduler.alarm;

import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.constants.AlarmType;
import com.lark.oapi.Client;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created in 2025-03-22 17:15.
 *
 * @author Horace
 */
@Service
public class FeiShuAlarmHandlerImpl extends LarkAlarmHandlerImpl {
    private static final Logger logger = LoggerFactory.getLogger(FeiShuAlarmHandlerImpl.class);
    @Resource
    private AppConfig appConfig;

    @PostConstruct
    public void init() {
        if (StringUtils.isBlank(this.appConfig.getFeiShuAppId()) || StringUtils.isBlank(this.appConfig.getFeiShuAppSecret())) {
            logger.info("feiShu app id or app secret are empty, skip feiShu alarm handler init");
            return;
        }
        this.client = Client.newBuilder(this.appConfig.getFeiShuAppId(), this.appConfig.getFeiShuAppSecret()).build();
        this.available = true;
        logger.info("init feiShu alarm handler success.");
    }

    /**
     * 获取告警通道
     *
     * @return
     */
    @Override
    public AlarmType getAlarmType() {
        return AlarmType.FeiShu;
    }
}