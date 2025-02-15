package cn.horace.cronjob.scheduler;

import cn.horace.cronjob.commons.constants.Constants;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 管理后台启动类
 * <p>
 *
 * @author Horace
 */
@MapperScan(Constants.ROOT_PACKAGE + ".mappers")
@SpringBootApplication(scanBasePackages = {Constants.ROOT_PACKAGE})
public class SchedulerMain {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerMain.class);

    public static void main(String[] args) {
        SpringApplication.run(SchedulerMain.class, args);
    }
}