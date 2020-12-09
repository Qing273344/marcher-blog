package xin.marcher.blog.account.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.account.client.api.AccountPassportApi;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.blog.account.client.model.response.BlogUserResp;
import xin.marcher.blog.account.domain.BlogUser;
import xin.marcher.blog.account.service.BlogUserService;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.util.OrikaMapperUtil;

/**
 * passport 相关
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/rpc/account", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "RPC = BlogPassportController", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountPassportApiClient implements AccountPassportApi {

    private final BlogUserService blogUserService;

    @Autowired
    public AccountPassportApiClient(BlogUserService blogUserService) {
        this.blogUserService = blogUserService;
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
    public BaseResult<BlogUserResp> login(@Validated @RequestBody RegisterReqs reqs) {
        // 验证用户输入的账号信息
        BlogUser blogUser = blogUserService.checkLoginInfo(reqs);
        BlogUserResp userResp = OrikaMapperUtil.INSTANCE.map(blogUser, BlogUserResp.class);
        return BaseResult.success(userResp);
    }

}
