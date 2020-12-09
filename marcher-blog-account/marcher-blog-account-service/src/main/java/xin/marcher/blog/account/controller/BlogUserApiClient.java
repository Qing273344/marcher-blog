package xin.marcher.blog.account.controller;

import io.swagger.annotations.Api;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.account.client.api.BlogUserApi;
import xin.marcher.blog.account.client.enums.UserLockedEnum;
import xin.marcher.blog.account.client.model.response.BlogUserResp;
import xin.marcher.blog.account.domain.BlogUser;
import xin.marcher.blog.account.service.BlogUserResourceService;
import xin.marcher.blog.account.service.BlogUserService;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.util.EmptyUtil;
import xin.marcher.framework.common.util.EnumUtil;
import xin.marcher.framework.common.util.OrikaMapperUtil;

import java.util.Set;

/**
 * 用户
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/rpc/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "RPC = BlogUserController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogUserApiClient implements BlogUserApi {

    private final BlogUserService blogUserService;
    private final BlogUserResourceService blogUserResourceService;

    @Autowired
    public BlogUserApiClient(BlogUserService blogUserService,
                             BlogUserResourceService blogUserResourceService) {
        this.blogUserService = blogUserService;
        this.blogUserResourceService = blogUserResourceService;
    }

    @Override
    @GetMapping("getResource")
    public BaseResult<Set<String>> getResource(@RequestParam("type") Integer type) {
        Set<String> userResourceSet = blogUserResourceService.getByUserType(type);
        return BaseResult.success(userResourceSet);
    }

    @Override
    @GetMapping("/info")
    public BaseResult<BlogUserResp> getUserInfo(@RequestParam("id") Long id) {
        BlogUser blogUser = blogUserService.getById(id);
        if (EmptyUtil.isEmpty(blogUser)) {
            return BaseResult.error("用户不存在");
        }

        if (blogUser.getIsLocked() != null && EnumUtil.isEq(blogUser.getIsLocked(), UserLockedEnum.USER_LOCKED_DISABLE)) {
            throw new LockedAccountException("账号已被锁定!");
        }

        BlogUserResp userResp = OrikaMapperUtil.INSTANCE.map(blogUser, BlogUserResp.class);
        return BaseResult.success(userResp);
    }
}
