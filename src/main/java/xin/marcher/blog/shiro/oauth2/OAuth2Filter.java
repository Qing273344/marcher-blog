package xin.marcher.blog.shiro.oauth2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import xin.marcher.blog.common.exception.MarcherException;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.HttpContextUtil;
import xin.marcher.blog.utils.JwtUtil;
import xin.marcher.blog.utils.Result;

import javax.annotation.Resource;
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
public class OAuth2Filter extends AuthenticatingFilter {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求token
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            return null;
        }

        return new OAuth2Token(token);
    }

    /**
     * shiro权限拦截核心方法 返回true允许访问resource
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }

        return false;
    }

    /**
     * 当访问拒绝时是否已经处理了, 如果返回true表示需要继续处理, 如果返回false表示该拦截器实例已经处理完成了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());

            Result data = Result.error(HttpStatus.SC_UNAUTHORIZED, "invalid token");
            String json = JSON.toJSONString(data, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
            httpResponse.getWriter().print(json);
            return false;
        }

        return executeLogin(request, response);
    }

    /**
     * 鉴定失败，返回错误信息
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        try {
            // 处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Result data = Result.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());

            String json = JSON.toJSONString(data, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
            httpResponse.getWriter().print(json);
            return false;
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        // 从header中获取token
        String token = httpRequest.getHeader("token");

        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token");
        }

        return token;
    }

    /**
     * 通过token获取userId
     *
     * @param token token
     * @return
     *      userId
     */
    private Long getUserIdFromToke(String token) {
        if (EmptyUtil.isEmpty(token)) {
            throw new MarcherException("invalid token", HttpStatus.SC_UNAUTHORIZED);
        }
        Claims claims = jwtUtil.getClaimByToken(token);
        if (EmptyUtil.isEmpty(claims) || jwtUtil.isTokenExpired(claims.getExpiration())) {
            throw new MarcherException(jwtUtil.getHeader() + "失效, 请重新登录", HttpStatus.SC_UNAUTHORIZED);
        }
        return Long.parseLong(claims.getSubject());
    }

}
