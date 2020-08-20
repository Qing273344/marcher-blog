package xin.marcher.blog.mapper.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.biz.consts.RedisKeyConstant;
import xin.marcher.blog.model.BlogUser;
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

    public void saveUserInfoToCache(BlogUser blogUser) {
        redisService.setObj(RedisKeyConstant.U_INFO_KEY + blogUser.getUserId(), blogUser, 60 * 120L);
    }

    public BlogUser getUserInfoFormCache(String userId) {
        return redisService.getObj(RedisKeyConstant.U_INFO_KEY + userId, BlogUser.class);
    }
}
