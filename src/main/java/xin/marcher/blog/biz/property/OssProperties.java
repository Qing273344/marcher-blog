package xin.marcher.blog.biz.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xin.marcher.aliyun.oss.config.StorageConfig;
import xin.marcher.blog.utils.EmptyUtil;

/**
 * 阿里云配置
 *
 * @author marcher
 */
@Component
@ConfigurationProperties(prefix = "oss")
@Order(-1)
public class OssProperties extends StorageConfig {

    /** 获取正式域名 */
    @Override
    public String getAliyunRegion() {
        if (EmptyUtil.isNotEmpty(aliyunRegion)) {
            return aliyunRegion;
        }
        return "http://" + aliyunBucketName + "." + aliyunEndPoint + "/";
    }

    /** 获取临时域名 */
    @Override
    public String getAliyunTempRegion() {
        if (EmptyUtil.isNotEmpty(aliyunTempRegion)) {
            return aliyunTempRegion;
        }
        return "http://" + aliyunTempBucketName + "." + aliyunEndPoint + "/";
    }

}
