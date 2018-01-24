package iandroid.club.bblogs.util;

import com.orhanobut.logger.Logger;

import java.io.Closeable;
import java.io.IOException;

/**
 * @描述 IO流工具类
 */
public class IOUtils {
    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                Logger.d(e.getMessage());
            }
        }
        return true;
    }
}