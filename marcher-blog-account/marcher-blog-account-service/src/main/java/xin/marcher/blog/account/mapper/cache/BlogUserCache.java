package xin.marcher.blog.account.mapper.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.account.biz.consts.CacheConst;
import xin.marcher.blog.account.model.cache.BlogUserCO;
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
        redisService.setObj(CacheConst.U_INFO_KEY + blogUserCO.getUserId(), blogUserCO, 60L * 60 * 24 * 30L);
    }

    public BlogUserCO getUserInfoFormCache(Long userId) {
        return redisService.getObj(CacheConst.U_INFO_KEY + userId, BlogUserCO.class);
    }
}