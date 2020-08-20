package xin.marcher.blog.web.controller;

import org.apache.shiro.SecurityUtils;
import xin.marcher.blog.model.BlogUser;

/**
 * Controller base组件
 *
 * @author marcher
 */
public abstract class AbstractBaseController {

    /**
     * 当前用户
     * @return
     *      用户信息
     */
    BlogUser getUser() {
        return (BlogUser) SecurityUtils.getSubject().getPrincipal();
    }
}
