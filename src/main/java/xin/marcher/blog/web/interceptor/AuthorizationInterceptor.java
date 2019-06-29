package xin.marcher.blog.web.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xin.marcher.blog.common.exception.MarcherException;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.CookieUtil;
import xin.marcher.blog.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 *
 * @author marcher
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义Controller
     * 返回值：
     *  true表示继续流程（如调用下一个拦截器或处理器）；
     *  false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取用户凭证(cookie中)
        String token = CookieUtil.getCookieValue(request, "token");

        //凭证为空
        if (StringUtils.isBlank(token)) {
            throw new MarcherException(jwtUtil.getToken() + "不能为空", HttpStatus.SC_UNAUTHORIZED);
        }

        Claims claims = jwtUtil.getClaimByToken(token);
        if (claims == null || jwtUtil.isTokenExpired(claims.getExpiration())) {
            throw new MarcherException(jwtUtil.getToken() + "失效，请重新登录", HttpStatus.SC_UNAUTHORIZED);
        }

        return true;
    }

    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，类似于try-catch-finally中的finally，但仅调用处理器执行链中
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
    }

    /**
     * 不是HandlerInterceptor的接口实现，是AsyncHandlerInterceptor的，AsyncHandlerInterceptor实现了HandlerInterceptor
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
    }
}
