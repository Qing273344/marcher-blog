package xin.marcher.blog.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码
 *
 * @author marcher
 */
public interface BlogCaptchaService {

    /**
     * 保存验证码到缓存
     *
     * @param captcha   验证码
     * @return
     *      返回缓存的key
     */
    String saveKaptchaToCache(String captcha);

    /**
     * 校验验证码正确性
     *
     * @param request   请求(获取cookie)
     * @param captcha   输入的验证码
     */
    void checkCaptcha(HttpServletRequest request, String captcha);
}
