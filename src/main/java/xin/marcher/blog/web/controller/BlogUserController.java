package xin.marcher.blog.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.entity.BlogUser;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.ObjectUtil;
import xin.marcher.blog.utils.Result;
import xin.marcher.blog.dto.response.BlogUserResp;

/**
 * 用户
 *
 * @author marcher
 */
@Slf4j
@RestController
@RequestMapping(value = "/blog/user")
public class BlogUserController extends AbstractBaseController {

    @Autowired
    private BlogUserService blogUserService;

    /**
     * 获取用户信息
     *
     * @return
     *      用户信息
     */
    @GetMapping("/info")
    public Result getUserInfo() {
        BlogUser blogUser = getUser();
        if (EmptyUtil.isEmpty(blogUser)) {
            return Result.error();
        }

        BlogUserResp blogUserResp = new BlogUserResp();
        ObjectUtil.copyProperties(blogUser, blogUserResp);

        Result data = new Result()
                .put("info", blogUserResp);
        return Result.success(data);
    }
}
