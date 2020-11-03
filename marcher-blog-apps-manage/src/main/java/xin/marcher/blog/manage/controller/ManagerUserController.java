package xin.marcher.blog.manage.controller;

import cn.hutool.system.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.framework.mvc.response.BaseResult;

/**
 * User
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/manage/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagerUserController", tags = "用户", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerUserController {

    /**
     * 用户信息
     */
    @PostMapping("/info")
    @ApiOperation(httpMethod = "POST", value = "用户信息")
    public BaseResult<UserInfo> info() {

        return null;
    }
}
