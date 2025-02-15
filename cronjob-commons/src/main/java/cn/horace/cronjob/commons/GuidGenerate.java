package cn.horace.cronjob.commons;

import cn.horace.cronjob.commons.utils.BitUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 生成一个64位的long类型数字，支持最大4095个节点，每个节点每毫秒生成2047个不重复的ID
 * <p>
 * <p>说明    符号位  标志位     时间      自增     appId</p>
 * <p>最大值  0      1         17年     2047    4095</p>
 * <p>字节数  1      1         39      11      12</p>
 *
 * @author Horace
 */
public class GuidGenerate {
    private static Logger logger = LoggerFactory.getLogger(GuidGenerate.class);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    /**
     * 时间占用的位数
     */
    private int timeBit = 39;

    /**
     * AppId占用的位数
     */
    private int appIdBit = 12;

    /**
     * 自增数占用的位数
     */
    private int incrementBit = 11;

    /**
     * 标志位，固定为1
     */
    private byte flag = 1;

    /**
     * 应用ID
     */
    private int appId;

    /**
     * AppID支持的最大值
     */
    private int appIdMax = 4095;

    /**
     * 时间字段支持的最大值
     */
    private long timeMax = 549755813887L;

    /**
     * 毫秒内自增的最大值
     */
    private int incrementMax = 2047;

    /**
     * 基准时间，2018-08-17 05:55:55
     */
    private long baseTime = 1534470947000L;

    /**
     * 时间map，记录毫秒内的自增数
     */
    private volatile Map<Long, AtomicInteger> timeMap = new ConcurrentHashMap<>();

    /**
     * 根据AppId生成实例
     *
     * @param appId 应用ID，取值范围： 0-4095
     */
    public GuidGenerate(int appId) {
        this.appId = appId;

        // 合法性判断
        if (!(appId >= 0 && appId <= this.appIdMax)) {
            throw new RuntimeException("Illegal app Id with a range of 0-4095.");
        }

        // 每10秒钟清理一次time map防止oom
        new Timer("clean-increment", true).schedule(new TimerTask() {
            @Override
            public void run() {

                // 倒序排，留前面2个
                TreeMap<Long, AtomicInteger> treeMap = new TreeMap<>(new Comparator<Long>() {
                    @Override
                    public int compare(Long o1, Long o2) {
                        return (int) (o2 - o1);
                    }
                });
                treeMap.putAll(timeMap);

                int index = 0;
                for (Long key : treeMap.keySet()) {
                    if (index >= 2) {
                        AtomicInteger integer = timeMap.remove(key);
                        //logger.info("clean time map, time:{}, increment:{}", dateFormat.format(new Date(key + baseTime)), integer);
                    }
                    index++;
                }
                treeMap = null;
            }
        }, 10000, 10000);
    }

    /**
     * 生成一个分布式唯一Id，有防重设计
     *
     * @return 返回一个long类型ID
     */
    public long genId() {
        long time = System.currentTimeMillis() - this.baseTime;

        // 时间倒退
        if (time < 0) {
            throw new RuntimeException("The time is illegal, please check the system time, the system time must be greater than 2018-08-17 05:55:55.");
        }

        // 时间最大值
        if (time > this.timeMax) {
            throw new RuntimeException("Illegal time, the maximum value of the time field has been exceeded");
        }

        int increment = this.increment(time);

        // 重新生成，防重设计
        if (increment == -1) {
            LockSupport.parkNanos(TimeUnit.NANOSECONDS.toNanos(100));
            return this.genId();
        }

        // 符号位  标志位     时间      自增     appId
        long id = (1L << (this.timeBit + this.incrementBit + this.appIdBit))
                | (time << (this.incrementBit + this.appIdBit))
                | (increment << (this.appIdBit))
                | (appId);

        return id;
    }

    /**
     * 毫秒内自增
     *
     * @param time 时间差，毫秒
     * @return 自增数，如果超过了毫秒内的最大值则返回-1
     */
    private int increment(Long time) {
        AtomicInteger atomicInteger = this.timeMap.get(time);

        if (atomicInteger == null) {
            AtomicInteger temp = new AtomicInteger(0);
            atomicInteger = this.timeMap.putIfAbsent(time, temp);
            if (atomicInteger == null) {
                atomicInteger = temp;
            }
        }

        int increment = atomicInteger.incrementAndGet();

        // 如果超过了最大值
        if (increment > this.incrementMax) {
            return -1;
        }

        return increment;
    }

    /**
     * 解析GlobalId
     *
     * @param id 通过 cn.horace.commons.GuidGenerate#genId() 生成的ID值
     * @return 返回解析后的结果
     */
    public Guid parseId(long id) {
        Guid guid = new Guid();
        guid.baseTime = this.dateFormat.format(this.baseTime);
        guid.flag = this.flag;

        // 符号位  标志位     时间      自增     appId

        // 取时间
        long time = id >> (this.incrementBit + this.appIdBit) & BitUtils.getMax(this.timeBit);
        time += this.baseTime;
        guid.time = this.dateFormat.format(new Date(time));

        // 取自增数
        int increment = (int) (id >> (this.appIdBit) & BitUtils.getMax(this.incrementBit));
        guid.increment = increment;

        // 取AppId
        int appId = (int) (id & BitUtils.getMax(this.appIdBit));
        guid.appId = appId;

        return guid;
    }

    public static class Guid {
        public String baseTime;
        public int flag;
        public String time;
        public int appId;
        public int increment;

        @Override
        public String toString() {
            return "GlobalId{" +
                    "baseTime='" + baseTime + '\'' +
                    ", flag=" + flag +
                    ", time='" + time + '\'' +
                    ", appId=" + appId +
                    ", increment=" + increment +
                    '}';
        }
    }
}