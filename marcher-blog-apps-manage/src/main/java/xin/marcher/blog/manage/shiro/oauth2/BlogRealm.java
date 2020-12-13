package xin.marcher.blog.manage.shiro.oauth2;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.account.client.api.BlogUserApi;
import xin.marcher.blog.manage.exception.RealmManageErrorCodeEnum;
import xin.marcher.blog.manage.shiro.JwtUtil;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.util.EmptyUtil;

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
    private BlogUserApi blogUserApi;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long userId = (Long) principals.getPrimaryPrincipal();

        // 通过 user_type 设置对应的权限(博主可访问/粉丝可访问)
        BaseResult<Set<String>> apiResult = blogUserApi.getResourceFromUserId(userId);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(apiResult.getData());
        return authorizationInfo;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        String token = (String) authenticationToken.getPrincipal();

        // 从 token 中获取 userId
        Long userId = jwtUtil.getUserIdFromToke(token);
        if (EmptyUtil.isEmpty(userId)) {
            throw new AuthenticationException(RealmManageErrorCodeEnum.LOGIN_TOKEN_INVALID.getRealDesc());
        }

        // --> 若配置了, 此处也可调用自定义的解密 CredentialsMatcher.doCredentialsMatch()
        return new SimpleAuthenticationInfo(userId, token, getName());
    }
}
