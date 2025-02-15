package cn.horace.cronjob.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 压缩工具类，10进制转64进制
 */
public class CompressUtils {
    private static Logger logger = LoggerFactory.getLogger(CompressUtils.class);
    private static char[] BASE = {'Z', 'W', 'M', '9', 'd', 'y', 'm', 'K', 'J', 'U', 'X', 'q', 's', '5',
            'b', 'a', 'N', 'F', 'Q', 'w', '+', '1', 'g', '4', 'c', 'z', 'E', 'V', 'e', 'C', 'f', 'T',
            'B', 'j', 'o', 'n', 'S', '3', 'Y', '0', 'k', 'i', 'I', '-', 'G', 'L', '6', 'H', '8', '2',
            'l', 'r', 'h', 'R', 'A', 'P', 'D', 'v', 'p', 'u', 'O', 'x', 't', '7'};
    private static List<String> list = new ArrayList<>();

    static {
        for (char c : BASE) {
            list.add(String.valueOf(c));
        }
    }

    /**
     * 将一个long型数字进行压缩
     *
     * @param value
     * @return
     */
    public static String compress(long value) {
        StringBuilder sb = new StringBuilder();
        do {
            // 每6位二进制数就能表示一位64进制数，十进制63刚好是6位二进制的最大值，所以这里按位与就能取出最后6位二进制的值
            int index = (int) (value & 63);
            // 抛弃最后6位二进制数
            value >>= 6;
            sb.append(BASE[index]);
        } while (value > 0);
        return sb.toString();
    }

    /**
     * 将一个字符串解压成long型数字，前提是该字符串是通过 compress(long value) 方法压缩的
     *
     * @param value
     * @return
     */
    public static long unCompress(String value) {
        long result = 0L;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            int index = list.indexOf(String.valueOf(c));

            // 这个 (long) 不能丢
            result += (long) (index * Math.pow(64, i));
        }
        return result;
    }

    /**
     * GZIP 压缩,100字节下，压缩效率不高，越压越大
     *
     * @param data 数据
     * @return
     */
    public static byte[] gzipCompress(byte[] data) {
        byte[] result = data;
        if (data == null || data.length <= 0) {
            return data;
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzipos = new GZIPOutputStream(baos);
            gzipos.write(data);
            gzipos.finish();
            gzipos.close();
            result = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            logger.error("gzipCompress error, msg:{}", e.getMessage(), e);
        }
        logger.debug("gzipCompress, length:{}, gzipLength:{}", data.length, result.length);
        return result;
    }

    /**
     * 解压缩
     *
     * @param data
     * @return
     */
    public static byte[] unGzipCompress(byte[] data) {
        byte[] result = data;
        if (data == null || data.length <= 0) {
            return data;
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            result = out.toByteArray();
            in.close();
            ungzip.close();
            out.close();
        } catch (IOException e) {
            logger.error("unGzipCompress error, msg:{}", e.getMessage(), e);
        }
        logger.debug("unGzipCompress, length:{}, unGzipLength:{}", data.length, result.length);
        return result;
    }
}
