package xin.marcher.blog.manage.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.account.client.feign.AccountPassportFeign;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.framework.mvc.response.BaseResult;

import javax.validation.Valid;

/**
 * 用户
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/article/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagerUserController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerUserController {

    private final AccountPassportFeign accountPassportFeign;

    @Autowired
    public ManagerUserController(AccountPassportFeign accountPassportFeign) {
        this.accountPassportFeign = accountPassportFeign;
    }

    /**
     * 注册
     *
     * @param reqs  注册信息
     */
    @PostMapping("/register")
    BaseResult<Boolean> register(@Valid @RequestBody RegisterReqs reqs) {
        return accountPassportFeign.register(reqs);
    }

    /**
     * 登录
     *
     * @param reqs 登录信息
     */
    @PostMapping("/login")
    BaseResult<Boolean> login(@Valid @RequestBody RegisterReqs reqs) {
        return accountPassportFeign.login(reqs);
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    BaseResult<Boolean> logout() {
        return accountPassportFeign.logout();
    }
}
