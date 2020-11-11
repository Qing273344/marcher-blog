package xin.marcher.blog.manage.shiro.oauth2;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.account.client.api.BlogUserApi;
import xin.marcher.blog.account.client.enums.UserLockedEnum;
import xin.marcher.blog.account.client.exception.RealmAccountErrorCodeEnum;
import xin.marcher.blog.account.client.model.response.BlogUserResp;
import xin.marcher.blog.manage.model.cache.BlogUserCO;
import xin.marcher.blog.manage.shiro.JwtUtil;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.EnumUtil;
import xin.marcher.framework.util.OrikaMapperUtil;

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
        BlogUserCO blogUser = (BlogUserCO) principals.getPrimaryPrincipal();

        // 通过 user_type 设置对应的权限(博主可访问/粉丝可访问)
        BaseResult<Set<String>> apiResult = blogUserApi.getResource(blogUser.getUserType());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(apiResult.getData());
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
            throw new AuthenticationException(RealmAccountErrorCodeEnum.LOGIN_TOKEN_INVALID.getRealDesc());
        }

        BaseResult<BlogUserResp> apiResult = blogUserApi.getUserInfo(userId);
        BlogUserResp blogUserResp = apiResult.getData();

        BlogUserCO blogUserCO = OrikaMapperUtil.INSTANCE.map(blogUserResp, BlogUserCO.class);
        // --> CredentialsMatcher.doCredentialsMatch()
        return new SimpleAuthenticationInfo(blogUserCO, accessToken, getName());
    }
}
