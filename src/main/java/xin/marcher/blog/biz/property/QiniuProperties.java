package xin.marcher.blog.biz.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 七牛云配置
 *
 * @author marcher
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oss")
@Order(-1)
public class QiniuProperties {

    /** 七牛云qiniuAccessKey */
    private String qiniuAccessKey;

    /** 七牛云qiniuSecreKey */
    private String qiniuSecreKey;

    /** 七牛云BucketName(正式) */
    private String qiniuBucketName;

    /** 七牛云文件域名(正式) */
    private String qiniuRegion;

}
