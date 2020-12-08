package xin.marcher.blog.manage.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.manage.consts.CacheConst;
import xin.marcher.blog.manage.model.cache.BlogUserCO;
import xin.marcher.framework.redis.RedisService;

/**
 * user cache
 *
 * @author marcher
 */
@Component
public class BlogUserCache {

    private static final long USER_TOKEN_EXPIRE = 60L * 60 * 24 * 30;

    @Autowired
    private RedisService redisService;

    public void saveUserInfoToCache(BlogUserCO blogUserCo) {
        redisService.setObj(CacheConst.U_INFO_KEY + blogUserCo.getUserId(), blogUserCo, USER_TOKEN_EXPIRE);
    }

    public BlogUserCO getUserInfoFormCache(Long userId) {
        return redisService.getObj(CacheConst.U_INFO_KEY + userId, BlogUserCO.class);
    }

    public void remove(Long userId) {
        redisService.delete(CacheConst.U_INFO_KEY + userId);
    }
}
