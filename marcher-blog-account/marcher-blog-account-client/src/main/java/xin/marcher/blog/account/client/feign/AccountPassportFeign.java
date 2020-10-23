package xin.marcher.blog.account.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.framework.mvc.response.BaseResult;

/**
 * account passport feign
 *
 * @author marcher
 */
@FeignClient(name = "marcher-blog-account-client", contextId = "account-passport-feign")
@RequestMapping(value = "/rpc/account")
public interface AccountPassportFeign {

    /**
     * 注册
     *
     * @param reqs  注册信息
     */
    @PostMapping("/register")
    BaseResult<Boolean> register(@Validated @RequestBody RegisterReqs reqs);

    /**
     * 登录
     *
     * @param reqs 登录信息
     */
    @PostMapping("/login")
    BaseResult<Boolean> login(@Validated @RequestBody RegisterReqs reqs);

    /**
     * 退出
     */
    @PostMapping("/logout")
    BaseResult<Boolean> logout();

}
