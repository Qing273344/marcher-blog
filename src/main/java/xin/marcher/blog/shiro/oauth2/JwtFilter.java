package xin.marcher.blog.shiro.oauth2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import xin.marcher.blog.utils.CookieUtil;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.HttpContextUtil;
import xin.marcher.blog.utils.Result;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2过滤器
 *
 * @author marcher
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 判断用户是否想要登入。
     * 判断cookie中是否有token信息
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        // 获取请求token
        String token = getRequestToken((HttpServletRequest) request);
        return EmptyUtil.isNotEmpty(token);
    }

    /**
     * shiro权限拦截核心方法 返回true允许访问resource
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return true;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // 获取请求token
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new JwtToken(token);
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest request) {
        // 从cookie中获取token
        return CookieUtil.getCookieValue(request, "token");
    }

}
