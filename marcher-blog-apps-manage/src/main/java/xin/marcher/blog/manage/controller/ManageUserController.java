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
import xin.marcher.blog.account.client.model.response.BlogUserResp;
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

    public ManageUserController(BlogUserApi blogUserApi) {
        this.blogUserApi = blogUserApi;
    }

    /**
     * 用户信息
     */
    @GetMapping("/info")
    @ApiOperation(httpMethod = "GET", value = "用户信息")
    public BaseResult<BlogUserVO> info() {
        Long userId = UserService.getUser();
        BaseResult<BlogUserResp> blogUserApiResult = blogUserApi.getUserInfo(userId);

        BlogUserVO blogUserVo = OrikaMapperUtil.INSTANCE.map(blogUserApiResult.getData(), BlogUserVO.class);
        return BaseResult.success(blogUserVo);
    }
}
