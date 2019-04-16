package xin.marcher.blog.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.utils.CookieUtil;
import xin.marcher.blog.utils.JwtUtil;
import xin.marcher.blog.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/blog/passport")
public class BlogLogoutController {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 退出
     */
    @PostMapping("/logout")
    @ResponseBody
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            subject.logout();
        }

        // 清除cookie信息
        CookieUtil.delCookie(request, response, jwtUtil.getToken());

        return Result.success();
    }

}
