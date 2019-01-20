package xin.marcher.blog.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加解密工具类
 * 必须先启动堡垒机
 *
 * @author shuzheng
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AESUtil {

    private static final String ALGORITHM_AES = "AES";

    /**
     * 秘钥key, 存放于堡垒机中
     */
    public static String KEY;

    static {
        String configUrl = "http://localhost:9020/config/encryption/key";
        KEY = HttpUtil.get(configUrl);
    }

    /**
     * 加密
     * 加密过程
     * 1.构造密钥生成器
     * 2.根据ecnodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     *
     * @param key     校验秘钥(存放于堡垒机中)
     * @param content 待加密字符串
     */
    public static String encrypt(String key, String content) {
        String encryptedData;
        try {
            // 生成AES密钥
            SecretKeySpec secretKey = getSecretKey(key);

            // 根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteContent = content.getBytes("utf-8");
            // 根据密码器的初始化方式--加密：将数据加密
            byte[] byteAES = cipher.doFinal(byteContent);
            //1 将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            encryptedData = new BASE64Encoder().encode(byteAES);
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }

    /**
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     *
     * @param key     校验秘钥(存放于堡垒机中)
     * @param content 待解密字符串
     */
    public static String decrypt(String key, String content) {
        String decryptedData;
        try {
            // 生成AES密钥
            SecretKeySpec secretKey = getSecretKey(key);

            // 根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // 将加密并编码后的内容解码成字节数组
            byte[] byteContent = new BASE64Decoder().decodeBuffer(content);

            // 解密
            byte[] byteDecode = cipher.doFinal(byteContent);
            decryptedData = new String(byteDecode, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }

    private static SecretKeySpec getSecretKey(final String seed) throws NoSuchAlgorithmException {
        // 构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM_AES);
        // 根据ecnodeRules规则初始化密钥生成器
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed.getBytes());
        // 生成一个128位的随机源,根据传入的字节数组
        keygen.init(128, random);
        // 产生原始对称密钥
        SecretKey originalKey = keygen.generateKey();
        // 获得原始对称密钥的字节数组
        byte[] raw = originalKey.getEncoded();
        // 根据字节数组生成AES密钥
        return new SecretKeySpec(raw, ALGORITHM_AES);
    }


    public static void main(String[] args) {
        String key = "123456";
        System.out.println("key | AESEncode | AESDecode");

        System.out.print(key + " | ");
        String encryptString = encrypt(KEY, key);
        System.out.print(encryptString + " | ");
        String decryptString = decrypt(KEY, encryptString);
        System.out.println(decryptString);
    }
}