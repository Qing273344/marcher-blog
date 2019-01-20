package xin.marcher.blog.biz.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Redis 属性
 *
 * @author marcher
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Order(-1)
public class RedisProperties {

    /** 180秒 - 三分钟 */
    @Value("${cache.expire-time}")
    private int expireTime;
    /** 60秒 - 一分钟 */
    @Value("${cache.default-expire-time}")
    private int defaultExpireTime;
    @Value("${cache.name}")
    private String cacheName;

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private int redisPort;
}
