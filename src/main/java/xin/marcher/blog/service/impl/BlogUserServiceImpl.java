package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.Constant;
import xin.marcher.blog.biz.consts.RedisKeyConstant;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.biz.enums.UserLockedEnum;
import xin.marcher.blog.biz.enums.UserSourceEnum;
import xin.marcher.blog.biz.enums.UserTypeEnum;
import xin.marcher.blog.biz.property.RedisProperties;
import xin.marcher.blog.dao.BlogUserDao;
import xin.marcher.blog.entity.BlogUser;
import xin.marcher.blog.common.exception.*;
import xin.marcher.blog.from.RegisterForm;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.DateUtil;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.OAuthUtil;
import xin.marcher.blog.utils.RedisUtil;

/**
 * @author marcher
 */
@Service
public class BlogUserServiceImpl extends ServiceImpl<BlogUserDao, BlogUser> implements BlogUserService {

    @Autowired
    private RedisProperties redisProperties;

    @Override
    public void checkUserNameExist(String username) {
        BlogUser blogUser = getByUsername(username);
        if (EmptyUtil.isNotEmpty(blogUser)) {
            throw new MarcherHintException( "用户名已存在", RspBaseCodeEnum.PARAM_ILLEGAL.getCode());
        }
    }

    @Override
    public void createUser(RegisterForm registerForm) {
        Long now = DateUtil.getTimestamp();
        String password = OAuthUtil.encrypt(registerForm.getPassword(), now.toString());

        BlogUser blogUser = new BlogUser();
        blogUser.setUsername(registerForm.getUsername());
        blogUser.setPassword(password);
        blogUser.setUserType(UserTypeEnum.USER_TYPE_OKAMI.getCode());
        blogUser.setSource(UserSourceEnum.USER_SOURCE_PC.getCode());
        blogUser.setLocked(UserLockedEnum.USER_LOCKED_NORMAL.getCode());
        blogUser.setCreateTime(now);
        blogUser.setModifyTime(now);
        blogUser.setDeleted(Constant.NO_DELETED);

        save(blogUser);
    }

    @Override
    public BlogUser getByUsername(String username) {
        QueryWrapper<BlogUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogUser::getUsername, username);
        return getOne(queryWrapper);
    }

    @Override
    public void saveUserInfoToCache(BlogUser blogUser) {
        RedisUtil.setCacheObj(RedisKeyConstant.CACHE_USER_INFO + blogUser.getUserId(), blogUser, redisProperties.getDefaultExpireTime() * 120L);
    }

    @Override
    public BlogUser getUserInfoFormCache(String userId) {
        return RedisUtil.getCacheObj(RedisKeyConstant.CACHE_USER_INFO + userId, BlogUser.class);
    }

}
