package xin.marcher.blog.utils;

import java.util.UUID;

/**
 * 随机数工具
 *
 * @author marcher
 */
public class RandomUtil {

    /**
     * UUID
     *
     * @return UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
