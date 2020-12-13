package xin.marcher.blog.manage.service;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

/**
 * user service
 *
 * @author marcher
 */
@Component
public class UserService {

    /**
     * 当前用户
     * @return
     *      用户信息
     */
    public static Long getUser() {
        return (Long) SecurityUtils.getSubject().getPrincipal();
    }
}
