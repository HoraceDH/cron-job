package cn.horace.cronjob.commons.utils;

/**
 */
public class BitUtils {

    /**
     * 获取指定bit位能表示的最大值
     *
     * @param bitSize
     * @return
     */
    public static long getMax(int bitSize) {
        return (1L << bitSize) - 1;
    }
}
