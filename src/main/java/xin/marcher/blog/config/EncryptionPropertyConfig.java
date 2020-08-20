package xin.marcher.blog.config;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xin.marcher.blog.utils.AESUtil;

/**
 * 加密解密 - 参考 https://blog.csdn.net/Dongguabai/article/details/81114569
 *
 * @author marcher
 */
@Configuration
public class EncryptionPropertyConfig {

    @Bean(name = "encryptablePropertyResolver")
    public EncryptablePropertyResolver encryptablePropertyResolver() {
        return new EncryptionPropertyResolver();
    }

    public static class EncryptionPropertyResolver implements EncryptablePropertyResolver {
        @Override
        public String resolvePropertyValue(String value) {
            if (StringUtils.isBlank(value)) {
                return value;
            }
            // 值以DES@开头的均为DES加密, 需要解密
            if (value.startsWith("AES@")) {
                return resolveDESValue(value.substring(4));
            }
            // 不需要解密的值直接返回
            return value;
        }

        private String resolveDESValue(String value) {
            // 自定义DES密文解密
            return AESUtil.decrypt(AESUtil.KEY, value);
        }
    }
}
