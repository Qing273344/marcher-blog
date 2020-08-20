package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.dto.LoginDTO;
import xin.marcher.blog.dto.RegisterDTO;
import xin.marcher.blog.model.BlogUser;
import xin.marcher.blog.model.cache.BlogUserCO;

import javax.servlet.http.HttpServletResponse;

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
     * @param registerDTO 用户注册信息
     */
    void createUser(RegisterDTO registerDTO);


    /**
     * 通过用户名获取用户信息
     *
     * @param username 用户名
     * @return
     *      用户信息
     */
    BlogUser getByUsername(String username);

    /**
     * 用户登录成功后保存信息到redis缓存中
     *
     * @param blogUserCO 用户信息
     */
    void saveUserInfoToCache(BlogUserCO blogUserCO);

    /**
     * 通过用户id获取用户信息
     *
     * @param userId 用户id
     * @return
     *      用户信息
     */
    BlogUserCO getUserInfoFormCache(Long userId);

    /**
     * 校验登录信息
     *
     * @param response  响应(用户set token 到cookie中)
     * @param loginDTO 用户输入的信息
     */
    void checkLoginInfo(HttpServletResponse response, LoginDTO loginDTO);

}
