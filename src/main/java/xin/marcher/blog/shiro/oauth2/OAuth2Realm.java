package xin.marcher.blog.shiro.oauth2;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import xin.marcher.blog.biz.enums.UserLockedEnum;
import xin.marcher.blog.entity.BlogUser;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.EmptyUtil;

import javax.annotation.Resource;

/**
 * 认证
 *
 * @author marcher
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Resource
    private BlogUserService blogUserService;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(null);
        return authorizationInfo;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户输入的账号
        String username = (String) token.getPrincipal();

        BlogUser blogUser = blogUserService.getByUsername(username);
        if (EmptyUtil.isEmpty(blogUser)) {
            throw new UnknownAccountException("账号不存在!");
        }
        if (blogUser.getLocked() != null && UserLockedEnum.USER_LOCKED_DISABLE.getCode().equals(blogUser.getLocked())){
            throw new LockedAccountException("账号已被锁定, 禁止登录!");
        }

        // --> CredentialsMatcher.doCredentialsMatch()
        return new SimpleAuthenticationInfo(blogUser, blogUser.getPassword(), getName());
    }
}
