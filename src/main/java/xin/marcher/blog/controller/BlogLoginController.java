package xin.marcher.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.dto.LoginDTO;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.framework.mvc.response.BaseResult;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录相关
 *
 * @author marcher
 */
@Slf4j
@RestController
@RequestMapping(value = "/blog/passport")
public class BlogLoginController {

    @Autowired
    private BlogUserService blogUserService;

    /**
     * 登录
     *
     * @param loginDTO 登录信息
     */
    @PostMapping("/login")
    @ResponseBody
    public BaseResult login(HttpServletResponse response, @Validated @RequestBody LoginDTO loginDTO) {

        // 验证用户输入的账号信息
        blogUserService.checkLoginInfo(response, loginDTO);

        return BaseResult.success();
    }

}
