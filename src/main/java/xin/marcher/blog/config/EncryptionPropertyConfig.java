package xin.marcher.blog.config;

import cn.hutool.http.HttpUtil;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xin.marcher.framework.util.AESUtil;
import xin.marcher.framework.util.EmptyUtil;

/**
 * 加密解密 - 参考 https://blog.csdn.net/Dongguabai/article/details/81114569
 *
 * @author marcher
 */
@Configuration
public class EncryptionPropertyConfig {

    public static final String KEY;

    static {
        KEY = HttpUtil.get("http://localhost:9020/config/encryption/key");
    }

    @Bean
    public EncryptablePropertyResolver encryptablePropertyResolver() {
        return new EncryptionPropertyResolver();
    }

    public static class EncryptionPropertyResolver implements EncryptablePropertyResolver {
        @Override
        public String resolvePropertyValue(String value) {
            if (EmptyUtil.isEmpty(value)) {
                return value;
            }
            // 值以DES@开头的均为DES加密, 需要解密
            if (value.startsWith("AES@")) {
                return decrypt(value.substring(4));
            }
            // 不需要解密的值直接返回
            return value;
        }

        private String decrypt(String value) {
            // 自定义DES密文解密
            return AESUtil.decrypt(KEY, value);
        }

        /**
         * 需要获取加密后的调用此方法
         */
        private void encrypt() {
            String value = "这里是需要加密的东东...";
            String encrypt = AESUtil.encrypt(KEY, value);
        }
    }
}
