package xin.marcher.blog.account.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.blog.account.client.model.response.BlogUserResp;
import xin.marcher.framework.common.mvc.response.BaseResult;

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
     * @return result
     */
    @PostMapping("/register")
    BaseResult<Boolean> register(@RequestBody RegisterReqs reqs);

    /**
     * 登录
     *
     * @param reqs 登录信息
     * @return result
     */
    @PostMapping("/login")
    BaseResult<BlogUserResp> login(@RequestBody RegisterReqs reqs);

}
