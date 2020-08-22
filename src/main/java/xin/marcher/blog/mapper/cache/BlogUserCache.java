package xin.marcher.blog.mapper.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.biz.consts.CacheConsts;
import xin.marcher.blog.model.cache.BlogUserCO;
import xin.marcher.framework.redis.RedisService;

/**
 * user cache
 *
 * @author marcher
 */
@Component
public class BlogUserCache {

    @Autowired
    private RedisService redisService;

    public void saveUserInfoToCache(BlogUserCO blogUserCO) {
        redisService.setObj(CacheConsts.U_INFO_KEY + blogUserCO.getUserId(), blogUserCO, 60L * 60 * 24 * 30L);
    }

    public BlogUserCO getUserInfoFormCache(Long userId) {
        return redisService.getObj(CacheConsts.U_INFO_KEY + userId, BlogUserCO.class);
    }
}
