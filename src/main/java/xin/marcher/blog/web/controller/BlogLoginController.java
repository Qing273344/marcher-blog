package xin.marcher.blog.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.biz.consts.RedisKeyConstant;
import xin.marcher.blog.from.LoginFrom;
import xin.marcher.blog.plugin.kaptcha.AbstractCaptcha;
import xin.marcher.blog.plugin.kaptcha.GifCaptcha;
import xin.marcher.blog.service.BlogCaptchaService;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.CookieUtil;
import xin.marcher.blog.utils.JwtUtil;
import xin.marcher.blog.utils.Result;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author marcher
 */
@Slf4j
@RestController
@RequestMapping(value = "/blog/passport")
public class BlogLoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private BlogCaptchaService blogCaptchaService;

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
            AbstractCaptcha gifCaptcha = new GifCaptcha(150, 35, 6);
            String captcha = gifCaptcha.out(response.getOutputStream());

            // 缓存验证码
            String captchaKey = blogCaptchaService.saveKaptchaToCache(captcha);
            // 存入cookie中, 验证时从cookie获取key
            CookieUtil.addCookie(response, RedisKeyConstant.U_LOGIN_KAPTCHA_KEY, captchaKey, CookieUtil.COOKIE_DOMAIN);
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
    @PostMapping("/login")
    @ResponseBody
    public Result login(HttpServletResponse response, @Valid @RequestBody LoginFrom loginFrom) {

        // 验证码校验
//        blogCaptchaService.checkCaptcha(request, loginFrom.getCaptcha());

        // 验证用户输入的账号信息
        blogUserService.checkLoginInfo(response, loginFrom);

        return Result.success();
    }

}
