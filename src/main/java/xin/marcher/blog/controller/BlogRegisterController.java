package xin.marcher.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.dto.RegisterDTO;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.Result;

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
     * @param registerDTO  注册信息
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(@Validated @RequestBody RegisterDTO registerDTO) {

        // check username
        blogUserService.checkUserNameExist(registerDTO.getUsername());

        // save user
        blogUserService.createUser(registerDTO);

        return Result.success();
    }
}
