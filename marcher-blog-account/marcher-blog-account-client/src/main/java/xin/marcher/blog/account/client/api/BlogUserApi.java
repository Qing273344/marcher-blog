package xin.marcher.blog.account.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xin.marcher.blog.account.client.model.response.BlogUserResp;
import xin.marcher.framework.common.mvc.response.BaseResult;

import java.util.Set;

/**
 * account passport feign
 *
 * @author marcher
 */
@FeignClient(name = "marcher-blog-account-service", contextId = "account-user-api")
@RequestMapping(value = "/rpc/user")
public interface BlogUserApi {

    /**
     * 获取用户权限
     *
     * @param type  用户类型
     * @return
     *      资源
     */
    @GetMapping("getResource")
    BaseResult<Set<String>> getResource(@RequestParam("type") Integer type);

    /**
     * 获取用户权限
     *
     * @param userId  用户
     * @return
     *      资源
     */
    @GetMapping("getResourceFromUserId")
    BaseResult<Set<String>> getResourceFromUserId(@RequestParam("userId") Long userId);

    /**
     * 获取用户信息
     *
     * @return
     *      用户信息
     */
    @GetMapping("/info")
    BaseResult<BlogUserResp> getUserInfo(@RequestParam("id") Long id);
}
