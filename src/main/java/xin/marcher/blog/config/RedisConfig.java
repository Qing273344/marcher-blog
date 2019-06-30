package xin.marcher.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import xin.marcher.blog.biz.property.RedisProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Redis配置
 * 注解: @EnableTransactionManagement 事物支持
 * 注解: @EnableCaching 启用缓存
 *
 * @author marcher
 */
@EnableCaching
@Order(1)
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 缓存管理器
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();

        // 设置缓存管理器管理的缓存的默认过期时间
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(redisProperties.getDefaultExpireTime()))
                // 设置key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不缓存空值
                .disableCachingNullValues();

        Set<String> cacheNames = new HashSet<>();
        cacheNames.add(redisProperties.getCacheName());

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put(redisProperties.getCacheName(), defaultCacheConfig.entryTtl(Duration.ofSeconds(redisProperties.getExpireTime())));

        RedisCacheManager cacheManager = RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }


    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        RedisSerializer<?> stringRedisSerializer = new StringRedisSerializer();

        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        // key序列化
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        //template.setEnableTransactionSupport(true);//是否启用事务
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 缓存对象集合中，缓存是以key-value形式保存的。当不指定缓存的key时，SpringBoot会使用SimpleKeyGenerator生成key。
     * 查看源码可以发现，它是使用方法参数组合生成的一个key。
     * 此时有一个问题： 如果2个方法，参数是一样的，但执行逻辑不同，那么将会导致执行第二个方法时命中第一个方法的缓存
     * 修改如下:
     * 缓存的key是包名+方法名+参数列表
     *
     * @return key生成
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * redis连接工厂(单机模式)
     *
     * @return 连接工厂
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisProperties.getRedisHost(), redisProperties.getRedisPort()));
    }

    /**
     * redis响应式连接工厂
     * @return
     *      连接工厂
     */
//    @Bean
//    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(){
//        return new LettuceConnectionFactory(redisHost, redisPort);
//    }

    /**
     * Lettuce
     *      高可用redis连接工厂（容灾）
     */
//    public RedisConnectionFactory redisConnectionFactory(){
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .master("master")
////                .sentinel(redisHost, redisPort)
//                .sentinel(redisHost, redisPort);
//        return new LettuceConnectionFactory(sentinelConfig);
//    }
}
