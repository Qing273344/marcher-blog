package xin.marcher.blog.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.account.client.api.BlogUserApi;
import xin.marcher.blog.manage.manage.BlogUserCache;
import xin.marcher.blog.manage.model.cache.BlogUserCO;
import xin.marcher.blog.manage.model.vo.BlogUserVO;
import xin.marcher.blog.manage.service.UserService;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.util.OrikaMapperUtil;

/**
 * User
 *
 * @author marcher
 */
@RequiresRoles("marcher")
@RestController
@RequestMapping(value = "/manage/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagerUserController", tags = "用户", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageUserController {

    private final BlogUserApi blogUserApi;
    private final BlogUserCache blogUserCache;

    @Autowired
    public ManageUserController(BlogUserApi blogUserApi, BlogUserCache blogUserCache) {
        this.blogUserApi = blogUserApi;
        this.blogUserCache = blogUserCache;
    }

    /**
     * 用户信息
     */
    @GetMapping("/info")
    @ApiOperation(httpMethod = "GET", value = "用户信息")
    public BaseResult<BlogUserVO> info() {
        BlogUserCO blogUserCo = blogUserCache.getUserInfoFormCache(UserService.getUser().getUserId());

        BlogUserVO blogUserVo = OrikaMapperUtil.INSTANCE.map(blogUserCo, BlogUserVO.class);
        return BaseResult.success(blogUserVo);
    }
}
