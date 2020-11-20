package xin.marcher.blog.account.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.account.client.enums.UserLockedEnum;
import xin.marcher.blog.account.client.enums.UserSourceEnum;
import xin.marcher.blog.account.client.enums.UserTypeEnum;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.blog.account.domain.BlogUser;
import xin.marcher.blog.account.exception.RealmAccountException;
import xin.marcher.blog.account.mapper.BlogUserMapper;
import xin.marcher.blog.account.service.BlogUserService;
import xin.marcher.blog.account.utils.CryptoUtil;
import xin.marcher.framework.constants.GlobalCodeEnum;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.EnumUtil;

/**
 * @author marcher
 */
@Service
public class BlogUserServiceImpl extends ServiceImpl<BlogUserMapper, BlogUser> implements BlogUserService {

    @Autowired
    private BlogUserMapper blogUserMapper;

    @Override
    public void checkUserNameExist(String username) {
        BlogUser blogUser = getByUsername(username);
        if (EmptyUtil.isNotEmpty(blogUser)) {
            throw new RealmAccountException(GlobalCodeEnum.GL_PARAMETER_ERROR.getRealCode(), "用户名已存在");
        }
    }

    @Override
    public void createUser(RegisterReqs reqs) {
        DateTime date = DateUtil.date();
        String password = CryptoUtil.encrypt(reqs.getPassword(), reqs.getUsername());

        BlogUser blogUser = new BlogUser();
        blogUser.setUsername(reqs.getUsername());
        blogUser.setPassword(password);
        blogUser.setUserType(UserTypeEnum.USER_TYPE_MANITO.getRealCode());
        blogUser.setSource(UserSourceEnum.USER_SOURCE_PC.getRealCode());
        blogUser.setIsLocked(UserLockedEnum.USER_LOCKED_NORMAL.getRealCode());
        blogUser.setCreateTime(date);
        blogUserMapper.insert((blogUser));
    }

    @Override
    public BlogUser getByUsername(String username) {
        return blogUserMapper.getByUsername(username);
    }

    @Override
    public BlogUser checkLoginInfo(RegisterReqs reqs) {
        BlogUser blogUser = getByUsername(reqs.getUsername());
        if (EmptyUtil.isEmpty(blogUser)) {
            throw new AuthenticationException("账号或密码错误~");
        }

        String inPassword = CryptoUtil.encrypt(reqs.getPassword(), reqs.getUsername());
        if (!inPassword.equals(blogUser.getPassword())) {
            throw new AuthenticationException("账号或密码错误~");
        }

        if (blogUser.getIsLocked() != null && EnumUtil.isEq(blogUser.getIsLocked(), UserLockedEnum.USER_LOCKED_DISABLE)){
            throw new LockedAccountException("账号已被锁定, 禁止登录!");
        }

        return blogUser;
    }

}
