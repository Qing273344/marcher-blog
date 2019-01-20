package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.entity.BlogUser;
import xin.marcher.blog.from.RegisterForm;

/**
 * 博客用户
 *
 * @author marcher
 */
public interface BlogUserService extends IService<BlogUser> {

    /**
     * check username
     *
     * @param username  username
     */
    void checkUserNameExist(String username);

    /**
     * 创建用户
     *
     * @param registerForm  用户注册信息
     */
    void createUser(RegisterForm registerForm);


    /**
     * 通过用户名获取用户信息
     *
     * @param username  用户名
     */
    BlogUser getByUsername(String username);

    /**
     * 用户登录成功后保存信息到redis缓存中
     *
     * @param blogUser  用户信息
     */
    void saveUserInfoToCache(BlogUser blogUser);

    /**
     * 通过用户id获取用户信息
     *
     * @param userId    用户id
     */
    BlogUser getUserInfoFormCache(String userId);
}

