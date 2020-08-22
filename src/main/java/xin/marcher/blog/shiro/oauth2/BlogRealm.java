package xin.marcher.blog.shiro.oauth2;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.biz.enums.UserLockedEnum;
import xin.marcher.blog.model.cache.BlogUserCO;
import xin.marcher.blog.service.BlogUserResourceService;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.JwtUtil;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.EnumUtil;

import java.util.Set;

/**
 * 认证
 *
 * @author marcher
 */
@Component
public class BlogRealm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private BlogUserResourceService blogUserResourceService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        BlogUserCO blogUser = (BlogUserCO) principals.getPrimaryPrincipal();

        // 通过 user_type 设置对应的权限(博主可访问/粉丝可访问)
        Set<String> roleSet = blogUserResourceService.getByUserType(blogUser.getUserType());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleSet);
        return authorizationInfo;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        /* token */
        String accessToken = (String) token.getPrincipal();

        // 从 token 中获取 userId
        Long userId = jwtUtil.getUserIdFromToke(accessToken);
        if (EmptyUtil.isEmpty(userId)) {
            throw new AuthenticationException(RspBaseCodeEnum.LOGIN_TOKEN_INVALID.getRealDesc());
        }

        BlogUserCO blogUserCO = blogUserService.getUserInfoFormCache(userId);
        if (EmptyUtil.isEmpty(blogUserCO)) {
            throw new AuthenticationException(RspBaseCodeEnum.LOGIN_TOKEN_INVALID.getRealDesc());
        }

        if (blogUserCO.getIsLocked() != null && EnumUtil.isEq(blogUserCO.getIsLocked(), UserLockedEnum.USER_LOCKED_DISABLE)){
            throw new LockedAccountException("账号已被锁定, 禁止登录!");
        }

        // --> CredentialsMatcher.doCredentialsMatch()
        return new SimpleAuthenticationInfo(blogUserCO, accessToken, getName());
    }
}
