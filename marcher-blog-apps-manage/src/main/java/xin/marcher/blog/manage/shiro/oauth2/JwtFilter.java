package xin.marcher.blog.manage.shiro.oauth2;

import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import xin.marcher.blog.manage.exception.RealmManageErrorCodeEnum;
import xin.marcher.blog.manage.exception.RealmManageException;
import xin.marcher.framework.common.constants.GlobalConstant;
import xin.marcher.framework.common.util.EmptyUtil;
import xin.marcher.framework.common.util.HttpContextUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * oauth2 过滤器
 *
 * @author marcher
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * shiro 权限拦截核心方法 返回 true 允许访问 resource
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return ((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name());
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // 获取请求 token
        String token = getRequestToken();
        if (EmptyUtil.isEmpty(token)) {
            return new JwtToken(null);
        }
        // 创建 JWT
        return new JwtToken(token);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = getRequestToken();
        if (EmptyUtil.isEmpty(token)) {
            // 业务状态, 用于 Filter 异常拦截返回特定的状态码
            request.setAttribute(GlobalConstant.BUSINESS_STATUS, RealmManageErrorCodeEnum.LOGIN_TOKEN_INVALID.getRealCode());
            throw new RealmManageException(RealmManageErrorCodeEnum.LOGIN_TOKEN_INVALID);
        }

        // 执行登录逻辑, 其实会调用 Realm 的 doGetAuthenticationInfo 方法
        return executeLogin(request, response);
    }

    /**
     * 获取请求的 token
     */
    private String getRequestToken() {
        // 从 header 中获取 token
        return HttpContextUtil.getAuthorization();
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-control-Allow-Origin", HttpContextUtil.getOrigin());
        httpServletResponse.setHeader("Access-Control-Allow-Methods", HttpContextUtil.getAllMethods());
        httpServletResponse.setHeader("Access-Control-Allow-Headers", HttpContextUtil.getHeaders());
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        // 跨域时会首先发送一个 option 请求，这里我们给 option 请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.HTTP_OK);
            return false;
        }

        return super.preHandle(request, response);
    }

}
