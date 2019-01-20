package xin.marcher.blog.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.entity.BlogUser;
import xin.marcher.blog.from.LoginFrom;
import xin.marcher.blog.plugin.kaptcha.AbstractCaptcha;
import xin.marcher.blog.plugin.kaptcha.GifCaptcha;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author marcher
 */
@Slf4j
@RestController
@RequestMapping(value = "/blog/login")
public class BlogLoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlogUserService blogUserService;

    /**
     * 获取验证码
     *
     * @param response 请求
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/gif");

        try {
            // gif格式动画验证码(宽，高，位数)
            AbstractCaptcha captcha = new GifCaptcha(150, 35, 6);
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取验证码异常：{}", e.getMessage());
        }
    }

    /**
     * 登录
     *
     * @param loginFrom 登录信息
     */
    @PostMapping("")
    @ResponseBody
    public Result login(HttpServletResponse response, @RequestBody LoginFrom loginFrom) {

        UsernamePasswordToken token = new UsernamePasswordToken(loginFrom.getUsername(), loginFrom.getPassword());
        Subject currentUser = SecurityUtils.getSubject();
        // --> OAuth2Realm.doGetAuthenticationInfo()
        currentUser.login(token);

        // 获取用户信息
        BlogUser blogUser = (BlogUser) currentUser.getPrincipals().getPrimaryPrincipal();

        // 登录成功后用户信息存入缓存中
        blogUserService.saveUserInfoToCache(blogUser);

        // 通过用户id生成token, set-cookie到浏览器, 后续通过cookie获取token做校验
        String jwtToken = jwtUtil.generateToken(blogUser.getUserId());
        CookieUtil.addCookie(response, "token", jwtToken, "localhost");

        return Result.success();
    }

}
