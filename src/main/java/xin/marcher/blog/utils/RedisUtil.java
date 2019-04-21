package xin.marcher.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import xin.marcher.blog.biz.property.RedisProperties;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Redis操作工具类
 * 使用该工具类的JavaBean必须实现Serializable接口
 *
 * @author marcher
 */
public class RedisUtil {

    @Autowired
    private RedisProperties redisProperties;

    public final Long U_SESSION_EXPIRE = redisProperties.getExpireTime() * 60 * 24 * 30L;

    /** 不设置过期时间 */
    public final long NOT_EXPIRE = -1;

    /**
     * 由于当前class不在spring boot框架内（不在web项目中）所以无法使用autowired，使用此种方法进行注入
     */
    @SuppressWarnings("unchecked")
    private static RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringBeanUtil.getBean("redisTemplate");

    /**
     * 缓存字符串
     *
     * @param key    key
     * @param data   数据
     * @param expire 过期时间,为null则不设置
     */
    public static void setCacheStr(String key, String data, Long expire) {
        if (EmptyUtil.isNotEmpty(redisTemplate)) {
            ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
            if (EmptyUtil.isNotEmpty(opsForValue)) {
                opsForValue.set(key, data);
                setCacheExpire(key, expire);
            }
        }
    }

    /**
     * 获取缓存字符串数据
     *
     * @param key key
     * @return 缓存字符串数据
     */
    public static String getCacheStr(String key) {
        String result = "";
        if (EmptyUtil.isNotEmpty(redisTemplate)) {
            ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
            if (EmptyUtil.isNotEmpty(opsForValue)) {
                result = String.valueOf(opsForValue.get(key));
            }
        }
        return result;
    }

    public static void setCacheObj(String key, Object data, Long expire) {
        if (EmptyUtil.isNotEmpty(redisTemplate)) {
            ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
            if (EmptyUtil.isNotEmpty(opsForValue)) {
                opsForValue.set(key, data);
                setCacheExpire(key, expire);
            }
        }
    }

    public static <T> T getCacheObj(String key, Class<?> clazz) {
        Object data = null;
        if (EmptyUtil.isNotEmpty(redisTemplate)) {
            ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
            if (EmptyUtil.isNotEmpty(opsForValue)) {
                data = opsForValue.get(key);
            }
        }
        return castData(data, clazz);
    }

    public static void setCacheList(String key, List<?> list, Long expire) {
        if (EmptyUtil.isNotEmpty(redisTemplate)) {
            ListOperations<String, Object> opsForList = redisTemplate.opsForList();
            if (EmptyUtil.isNotEmpty(opsForList)) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    opsForList.leftPush(key, list.get(i));
                }
                setCacheExpire(key, expire);
            }
        }
    }

    public static <T> List<T> getCacheList(String key, Class<?> clazz) {
        List<T> list = new ArrayList<T>();
        if (EmptyUtil.isNotEmpty(redisTemplate)) {
            ListOperations<String, Object> opsForList = redisTemplate.opsForList();
            if (EmptyUtil.isNotEmpty(opsForList)) {
                Long size = opsForList.size(key);
                for (int i = 0; i < size; i++) {
                    Object data = opsForList.index(key, i);
                    list.add(castData(data, clazz));
                }
            }
        }
        return list;
    }

    public static void setCacheSet(String key, Set<?> set, Long expire) {
        if (EmptyUtil.isNotEmpty(redisTemplate)) {
            SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
            if (EmptyUtil.isNotEmpty(opsForSet)) {
                opsForSet.add(key, set);
                setCacheExpire(key, expire);
            }
        }
    }

    public static <T> Set<T> getCacheSet(String key, Class<?> clazz) {
        Set<T> set = new HashSet<>();
        if (EmptyUtil.isNotEmpty(redisTemplate)) {
            SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
            if (EmptyUtil.isNotEmpty(opsForSet)) {
                Set<Object> cacheSet = opsForSet.members(key);
                if (EmptyUtil.isNotEmpty(cacheSet)) {
                    for (Object data : cacheSet) {
                        set.add(castData(data, clazz));
                    }
                }
            }
        }
        return set;
    }

    public static void setCacheMap(String key, Map<Object, Object> map, Long expire) {
        if (EmptyUtil.isNotEmpty(redisTemplate)) {
            HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
            if (EmptyUtil.isNotEmpty(opsForHash)) {
                opsForHash.putAll(key, map);
                setCacheExpire(key, expire);
            }
        }
    }

    public static Map<Object, Object> getCacheMap(String key) {
        Map<Object, Object> map = new HashMap<>();
        if (EmptyUtil.isNotEmpty(redisTemplate)) {
            HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
            if (EmptyUtil.isNotEmpty(opsForHash)) {
                map = opsForHash.entries(key);
            }
        }
        return map;
    }

    /**
     * 根据前缀清除缓存
     *
     * @param prefix 前缀
     */
    public static void cleanByPrefix(String prefix) {
        if (EmptyUtil.isNotEmpty(prefix)) {
            if (EmptyUtil.isNotEmpty(redisTemplate)) {
                Set<String> keys = redisTemplate.keys(prefix + "*");
                if (EmptyUtil.isNotEmpty(keys)) {
                    for (String key : keys) {
                        redisTemplate.delete(key);
                    }
                }
            }
        }
    }

    /**
     * 根据key秦楚缓存
     *
     * @param key key
     */
    public static void cleanByKey(String key) {
        if (EmptyUtil.isNotEmpty(key)) {
            if (EmptyUtil.isNotEmpty(redisTemplate)) {
                redisTemplate.delete(key);
            }
        }
    }

    /**
     * set缓存过期时间
     *
     * @param key    key
     * @param expire 过期时间(秒)
     */
    public static void setCacheExpire(String key, Long expire) {
        if (EmptyUtil.isNotEmpty(expire)) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 数据转换
     *
     * @param data  数据
     * @param clazz 数据class
     * @param <T>   泛型
     * @return 转换后的数据
     */
    public static <T> T castData(Object data, Class<?> clazz) {
        if (EmptyUtil.isNotEmpty(data)) {
            if (clazz.isInstance(data)) {
                return (T) clazz.cast(data);
            }
        }
        return null;
    }
}
