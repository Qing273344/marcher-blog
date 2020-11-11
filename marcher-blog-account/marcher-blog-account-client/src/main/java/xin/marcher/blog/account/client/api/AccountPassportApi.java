package xin.marcher.blog.account.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.blog.account.client.model.response.BlogUserResp;
import xin.marcher.framework.mvc.response.BaseResult;

/**
 * account passport feign
 *
 * @author marcher
 */
@FeignClient(name = "marcher-blog-account-service", contextId = "account-passport-api")
@RequestMapping(value = "/rpc/account")
public interface AccountPassportApi {

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
    BaseResult<BlogUserResp> login(@Validated @RequestBody RegisterReqs reqs);

}
