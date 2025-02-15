package cn.horace.cronjob.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Horace on 2017/8/7 11:59.
 */
public class IOUtils {
    private static Logger logger = LoggerFactory.getLogger(IOUtils.class);

    /**
     * 读取到字节数组，并关闭输入流
     *
     * @param inputStream
     * @return
     */
    public static byte[] copy(InputStream inputStream) {
        if (inputStream == null) {
            return new byte[0];
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(inputStream, out);
        return out.toByteArray();
    }

    /**
     * 将输入流拷贝数据到输出流中
     *
     * @param in  输入流
     * @param out 输出流
     */
    public static void copy(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException ignore) {

            }
            try {
                out.close();
            } catch (IOException ignore) {
            }
        }
    }
}
