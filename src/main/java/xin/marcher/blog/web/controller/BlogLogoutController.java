package xin.marcher.blog.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.utils.Result;

/**
 * 退出
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/blog/passport")
public class BlogLogoutController {

    /**
     * 退出
     */
    @PostMapping("/logout")
    @ResponseBody
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            subject.logout();
        }
        return Result.success();
    }

}
