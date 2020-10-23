package xin.marcher.blog.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.account.biz.enums.UserLockedEnum;
import xin.marcher.blog.account.biz.enums.UserSourceEnum;
import xin.marcher.blog.account.biz.enums.UserTypeEnum;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.blog.account.convert.BlogUserConvert;
import xin.marcher.blog.account.domain.BlogUser;
import xin.marcher.blog.account.exception.RealmAccountException;
import xin.marcher.blog.account.mapper.BlogUserMapper;
import xin.marcher.blog.account.mapper.cache.BlogUserCache;
import xin.marcher.blog.account.model.cache.BlogUserCO;
import xin.marcher.blog.account.service.BlogUserService;
import xin.marcher.blog.account.utils.JwtUtil;
import xin.marcher.blog.account.utils.OAuthUtil;
import xin.marcher.framework.constants.GlobalConstant;
import xin.marcher.framework.constants.GlobalErrorCodeEnum;
import xin.marcher.framework.util.*;

/**
 * @author marcher
 */
@Service
public class BlogUserServiceImpl extends ServiceImpl<BlogUserMapper, BlogUser> implements BlogUserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlogUserCache blogUserCache;

    @Autowired
    private BlogUserMapper blogUserMapper;

    @Override
    public void checkUserNameExist(String username) {
        BlogUser blogUser = getByUsername(username);
        if (EmptyUtil.isNotEmpty(blogUser)) {
            throw new RealmAccountException(GlobalErrorCodeEnum.GL_PARAMETER_ERROR.getRealCode(), "用户名已存在");
        }
    }

    @Override
    public void createUser(RegisterReqs reqs) {
        Long now = DateUtil.now();
        String password = OAuthUtil.encrypt(reqs.getPassword(), now.toString());

        BlogUser blogUser = new BlogUser();
        blogUser.setUsername(reqs.getUsername());
        blogUser.setPassword(password);
        blogUser.setUserType(UserTypeEnum.USER_TYPE_MANITO.getRealCode());
        blogUser.setSource(UserSourceEnum.USER_SOURCE_PC.getRealCode());
        blogUser.setIsLocked(UserLockedEnum.USER_LOCKED_NORMAL.getRealCode());
        blogUser.setCreateTime(now);
        blogUser.setDeleted(GlobalConstant.NO_DELETED);

        blogUserMapper.insert((blogUser));
    }

    @Override
    public BlogUser getByUsername(String username) {
        QueryWrapper<BlogUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogUser::getUsername, username);
        return blogUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void saveUserInfoToCache(BlogUserCO blogUserCo) {
        blogUserCache.saveUserInfoToCache(blogUserCo);
    }

    @Override
    public BlogUserCO getUserInfoFormCache(Long userId) {
        return blogUserCache.getUserInfoFormCache(userId);
    }

    @Override
    public void checkLoginInfo(RegisterReqs reqs) {
        BlogUser blogUser = getByUsername(reqs.getUsername());
        if (EmptyUtil.isEmpty(blogUser)) {
            throw new AuthenticationException("账号或密码错误~");
        }
        String inPassword = OAuthUtil.encrypt(reqs.getPassword(), blogUser.getCreateTime().toString());
        if (!inPassword.equals(blogUser.getPassword())) {
            throw new AuthenticationException("账号或密码错误~");
        }

        if (blogUser.getIsLocked() != null && EnumUtil.isEq(blogUser.getIsLocked(), UserLockedEnum.USER_LOCKED_DISABLE)){
            throw new LockedAccountException("账号已被锁定, 禁止登录!");
        }

        // 登录成功后用户信息存入缓存中
        BlogUserCO blogUserCo = BlogUserConvert.INSTANCE.convert(blogUser);
        blogUserCache.saveUserInfoToCache(blogUserCo);

        // 通过用户 id 生成 token, set-cookie 到浏览器, 后续通过 cookie 获取 token 做校验
        String jwtToken = jwtUtil.generateToken(blogUser.getUserId());
        CookieUtil.addCookie(HttpContextUtil.getResponse(), jwtUtil.getToken(), jwtToken, CookieUtil.COOKIE_DOMAIN);
    }

}
