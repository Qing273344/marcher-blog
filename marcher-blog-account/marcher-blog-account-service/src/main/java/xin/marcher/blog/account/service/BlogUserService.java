package xin.marcher.blog.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.account.client.model.request.RegisterReqs;
import xin.marcher.blog.account.domain.BlogUser;

/**
 * 博客用户
 *
 * @author marcher
 */
public interface BlogUserService extends IService<BlogUser> {

    /**
     * check username
     *
     * @param username username
     */
    void checkUserNameExist(String username);

    /**
     * 创建用户
     *
     * @param reqs 用户注册信息
     */
    void createUser(RegisterReqs reqs);

    /**
     * 通过用户名获取用户信息
     *
     * @param username 用户名
     * @return
     *      用户信息
     */
    BlogUser getByUsername(String username);

    /**
     * 校验登录信息
     *
     * @param reqs      用户输入的信息
     */
    BlogUser checkLoginInfo(RegisterReqs reqs);

}
