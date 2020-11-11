package xin.marcher.blog.manage.service;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import xin.marcher.blog.manage.model.cache.BlogUserCO;

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
    public static BlogUserCO getUser() {
        return (BlogUserCO) SecurityUtils.getSubject().getPrincipal();
    }
}
