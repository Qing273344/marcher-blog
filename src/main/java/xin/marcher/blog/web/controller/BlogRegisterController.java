package xin.marcher.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.dto.request.RegisterReq;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.Result;

import javax.validation.Valid;

/**
 * 注册
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/blog/passport")
public class BlogRegisterController {

    @Autowired
    private BlogUserService blogUserService;

    /**
     * 注册
     * @param registerReq  注册信息
     */
    @PostMapping("/register")
    @ResponseBody
    public Result  register(@Valid @RequestBody RegisterReq registerReq) {

        // check username
        blogUserService.checkUserNameExist(registerReq.getUsername());

        // save user
        blogUserService.createUser(registerReq);

        return Result.success();
    }
}
