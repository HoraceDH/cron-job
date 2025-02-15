package cn.horace.cronjob.executor.starter.annotations;

import cn.horace.cronjob.executor.starter.EnabledMarker;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于开启CronJob功能
 * <p>
 * Created in 2024-11-24 17:15.
 *
 * @author Horace
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({EnabledMarker.class})
public @interface EnableCronJob {

}