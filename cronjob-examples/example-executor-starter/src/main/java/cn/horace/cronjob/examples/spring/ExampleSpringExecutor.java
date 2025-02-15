package cn.horace.cronjob.examples.spring;

import cn.horace.cronjob.executor.starter.annotations.EnableCronJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created in 2025-01-01 21:00.
 *
 * @author Horace
 */
@EnableCronJob
@SpringBootApplication
public class ExampleSpringExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ExampleSpringExecutor.class);

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringExecutor.class, args);
    }
}