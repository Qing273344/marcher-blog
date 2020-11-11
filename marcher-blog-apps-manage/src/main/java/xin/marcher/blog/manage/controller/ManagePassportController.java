package xin.marcher.blog.manage.controller;

import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.account.client.api.AccountPassportApi;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.blog.account.client.model.response.BlogUserResp;
import xin.marcher.blog.manage.manage.BlogUserCache;
import xin.marcher.blog.manage.model.cache.BlogUserCO;
import xin.marcher.blog.manage.shiro.JwtUtil;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.util.CookieUtil;
import xin.marcher.framework.util.HttpContextUtil;
import xin.marcher.framework.util.OrikaMapperUtil;

import javax.validation.Valid;

/**
 * Passport 通行凭证相关
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/manage/passport", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagerUserController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagePassportController {

    private final AccountPassportApi accountPassportApi;
    private final BlogUserCache blogUserCache;
    private final JwtUtil jwtUtil;

    @Autowired
    public ManagePassportController(AccountPassportApi accountPassportApi,
                                    BlogUserCache blogUserCache,
                                    JwtUtil jwtUtil) {
        this.accountPassportApi = accountPassportApi;
        this.blogUserCache = blogUserCache;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 注册
     *
     * @param reqs  注册信息
     */
    @PostMapping("/register")
    BaseResult<Boolean> register(@Valid @RequestBody RegisterReqs reqs) {
        return accountPassportApi.register(reqs);
    }

    /**
     * 登录
     *
     * @param reqs 登录信息
     */
    @PostMapping("/login")
    BaseResult<Boolean> login(@Valid @RequestBody RegisterReqs reqs) {
        BaseResult<BlogUserResp> apiResult = accountPassportApi.login(reqs);
        if (apiResult.isFail()) {
            return BaseResult.error(apiResult.getMessage());
        }

        BlogUserResp userResp = apiResult.getData();

        // 登录成功后用户信息存入缓存中
        BlogUserCO blogUserCo = OrikaMapperUtil.INSTANCE.map(userResp, BlogUserCO.class);
        blogUserCache.saveUserInfoToCache(blogUserCo);

        // 通过用户 id 生成 token, set-cookie 到浏览器, 后续通过 cookie 获取 token 做校验
        String jwtToken = jwtUtil.generateToken(blogUserCo.getUserId());
        CookieUtil.addCookie(HttpContextUtil.getResponse(), jwtUtil.getToken(), jwtToken, CookieUtil.COOKIE_DOMAIN);
        return BaseResult.success();
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    BaseResult<Boolean> logout() {
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
