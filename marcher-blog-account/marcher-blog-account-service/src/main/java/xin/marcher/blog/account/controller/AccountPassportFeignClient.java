package xin.marcher.blog.account.controller;

import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.account.client.feign.AccountPassportFeign;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.blog.account.service.BlogUserService;
import xin.marcher.blog.account.utils.JwtUtil;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.util.CookieUtil;
import xin.marcher.framework.util.HttpContextUtil;

/**
 * passport 相关
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/rpc/account", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "RPC = BlogPassportController", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountPassportFeignClient implements AccountPassportFeign {

    private final BlogUserService blogUserService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AccountPassportFeignClient(BlogUserService blogUserService, JwtUtil jwtUtil) {
        this.blogUserService = blogUserService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 注册
     *
     * @param reqs  注册信息
     */
    @Override
    @PostMapping("/register")
    public BaseResult<Boolean> register(@Validated @RequestBody RegisterReqs reqs) {
        // check username
        blogUserService.checkUserNameExist(reqs.getUsername());

        // save user
        blogUserService.createUser(reqs);

        return BaseResult.success();
    }

    /**
     * 登录
     *
     * @param reqs 登录信息
     */
    @Override
    @PostMapping("/login")
    public BaseResult<Boolean> login(@Validated @RequestBody RegisterReqs reqs) {
        // 验证用户输入的账号信息
        blogUserService.checkLoginInfo(reqs);

        return BaseResult.success(true);
    }

    /**
     * 退出
     */
    @Override
    @PostMapping("/logout")
    public BaseResult<Boolean> logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            subject.logout();
        }

        // 清除cookie信息
        CookieUtil.delCookie(HttpContextUtil.getRequest(), HttpContextUtil.getResponse(), jwtUtil.getToken());

        return BaseResult.success();
    }
}
