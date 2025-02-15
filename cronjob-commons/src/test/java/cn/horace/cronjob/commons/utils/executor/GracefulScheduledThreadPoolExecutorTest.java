package cn.horace.cronjob.commons.utils.executor;

import cn.horace.cronjob.commons.thread.DefaultThreadFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created in 2025-01-02 15:47.
 *
 * @author Horace
 */
public class GracefulScheduledThreadPoolExecutorTest {
    private static GracefulScheduledThreadPoolExecutor threadScheduledExecutor = new GracefulScheduledThreadPoolExecutor(11, new DefaultThreadFactory("scheduler-thread"), true);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 需要留意，如果任务执行异常，后续任务不会执行
        threadScheduledExecutor.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName());
            throw new RuntimeException();
        }, 0, 3, TimeUnit.SECONDS);

//        threadScheduledExecutor.schedule(() -> {
//            System.out.println(Thread.currentThread().getName());
//            throw new RuntimeException();
//        }, 3, TimeUnit.SECONDS);
    }
}