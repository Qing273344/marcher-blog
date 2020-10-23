package xin.marcher.blog.account.controller;

import org.apache.shiro.SecurityUtils;
import xin.marcher.blog.account.model.cache.BlogUserCO;

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
    protected BlogUserCO getUser() {
        return (BlogUserCO) SecurityUtils.getSubject().getPrincipal();
    }
}
