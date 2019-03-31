package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.authc.LockedAccountException;
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
import xin.marcher.blog.from.LoginFrom;
import xin.marcher.blog.from.RegisterForm;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author marcher
 */
@Service
public class BlogUserServiceImpl extends ServiceImpl<BlogUserDao, BlogUser> implements BlogUserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private BlogUserService blogUserService;

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
        blogUser.setUserType(UserTypeEnum.USER_TYPE_MANITO.getCode());
        blogUser.setSource(UserSourceEnum.USER_SOURCE_PC.getCode());
        blogUser.setIsLocked(UserLockedEnum.USER_LOCKED_NORMAL.getCode());
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
        RedisUtil.setCacheObj(RedisKeyConstant.U_INFO_KEY + blogUser.getUserId(), blogUser, redisProperties.getDefaultExpireTime() * 120L);
    }

    @Override
    public BlogUser getUserInfoFormCache(String userId) {
        return RedisUtil.getCacheObj(RedisKeyConstant.U_INFO_KEY + userId, BlogUser.class);
    }

    @Override
    public void checkLoginInfo(HttpServletResponse response, LoginFrom loginFrom) {
        BlogUser blogUser = getByUsername(loginFrom.getUsername());
        if (EmptyUtil.isEmpty(blogUser)) {
            throw new MarcherException("账号或密码错误~");
        }
        if (blogUser.getIsLocked() != null && UserLockedEnum.USER_LOCKED_DISABLE.getCode().equals(blogUser.getIsLocked())){
            throw new LockedAccountException("账号已被锁定, 禁止登录!");
        }

        String inPassword = OAuthUtil.encrypt(loginFrom.getPassword(), blogUser.getCreateTime().toString());

        if (!inPassword.equals(blogUser.getPassword())) {
            throw new MarcherException("账号或密码错误~");
        }

        // 登录成功后用户信息存入缓存中
        blogUserService.saveUserInfoToCache(blogUser);

        // 通过用户id生成token, set-cookie到浏览器, 后续通过cookie获取token做校验
        String jwtToken = jwtUtil.generateToken(blogUser.getUserId());
        CookieUtil.addCookie(response, jwtUtil.getToken(), jwtToken, CookieUtil.COOKIE_DOMAIN);
    }

}
