package xin.marcher.blog.biz.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 阿里云配置
 *
 * @author marcher
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oss")
@Order(-1)
public class OssProperties {

    /** 阿里云aliyunEndPoint */
    private String aliyunEndPoint;

    /** 阿里云aliyunAccessKeyId */
    private String aliyunAccessKeyId;

    /** 阿里云aliyunAccessKeySecret */
    private String aliyunAccessKeySecret;

    /** 阿里云aliyunBucketName(正式) */
    private String aliyunBucketName;

    /** 阿里云文件域名(正式) */
    private String aliyunRegion;

}
