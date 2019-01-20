package xin.marcher.blog.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 授权工具类
 *
 * @author marcher
 */
public class OAuthUtil {

    /**
     * 密码加密
     *
     * @param password  密码
     * @param salt      加密盐
     * @return
     *      加密后的密码
     */
    public static String encrypt(String password, String salt) {
        return String.valueOf(new SimpleHash("MD5", password, salt));
    }
}
