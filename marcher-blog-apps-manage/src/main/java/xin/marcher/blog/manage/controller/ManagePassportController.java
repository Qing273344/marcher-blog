package xin.marcher.blog.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.account.client.api.AccountPassportApi;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.blog.account.client.model.response.BlogUserResp;
import xin.marcher.blog.manage.manage.BlogUserCache;
import xin.marcher.blog.manage.model.cache.BlogUserCO;
import xin.marcher.blog.manage.model.vo.PassportVO;
import xin.marcher.blog.manage.shiro.JwtUtil;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.util.OrikaMapperUtil;

import javax.validation.Valid;

/**
 * Passport 通行凭证相关
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/manage/passport", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagePassportController", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @ApiOperation(httpMethod = "POST", value = "注册")
    public BaseResult<Boolean> register(@Valid @RequestBody RegisterReqs reqs) {
        return accountPassportApi.register(reqs);
    }

    /**
     * 登录
     *
     * @param reqs 登录信息
     */
    @PostMapping("/login")
    @ApiOperation(httpMethod = "POST", value = "登录")
    public BaseResult<PassportVO> login(@Valid @RequestBody RegisterReqs reqs) {
        BaseResult<BlogUserResp> apiResult = accountPassportApi.login(reqs);
        if (apiResult.hasFail()) {
            return BaseResult.error(apiResult.getMessage());
        }
        BlogUserResp userResp = apiResult.getData();

        // 登录成功后用户信息存入缓存中
//        BlogUserCO blogUserCo = OrikaMapperUtil.INSTANCE.map(userResp, BlogUserCO.class);
//        blogUserCache.saveUserInfoToCache(blogUserCo);

        // 通过用户 id 生成 token, 前端获取后设置到请求头中
        String jwtToken = jwtUtil.generateToken(userResp.getUserId());

        PassportVO passportVO = new PassportVO();
        passportVO.setToken(jwtToken);
        return BaseResult.success(passportVO);
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    @ApiOperation(httpMethod = "POST", value = "退出")
    public BaseResult<Boolean> logout() {
        Subject subject = SecurityUtils.getSubject();
//        BlogUserCO blogUserCo = (BlogUserCO) subject.getPrincipal();
        if (subject.isAuthenticated()) {
            // session 会销毁，在 SessionListener 监听 session 销毁，清理权限缓存
            subject.logout();
        }

        // 清除缓存数据
//        blogUserCache.remove(blogUserCo.getUserId());

        return BaseResult.success();
    }

    @GetMapping("/test")
    public BaseResult<String> test() {
        String secret = jwtUtil.getSecret();
        return BaseResult.success(secret);
    }
}
