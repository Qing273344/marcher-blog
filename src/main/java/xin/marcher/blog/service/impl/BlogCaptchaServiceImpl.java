package xin.marcher.blog.service.impl;

import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.RedisKeyConstant;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.common.exception.MarcherHintException;
import xin.marcher.blog.service.BlogCaptchaService;
import xin.marcher.blog.utils.CookieUtil;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.RandomUtil;
import xin.marcher.blog.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author marcher
 */
@Service
public class BlogCaptchaServiceImpl implements BlogCaptchaService {

    @Override
    public String saveKaptchaToCache(String captcha) {
        String kaptchaKey = RandomUtil.randomUUID();

        RedisUtil.setCacheStr(kaptchaKey, captcha, 60L);
        return kaptchaKey;
    }

    @Override
    public void checkCaptcha(HttpServletRequest request, String captcha) {
        if (EmptyUtil.isEmpty(captcha)) {
            throw new MarcherHintException("验证码错误!", RspBaseCodeEnum.PARAM_ILLEGAL.getCode());
        }

        // 从缓存中获取验证码
        String captchaKey = CookieUtil.getCookieValue(request, RedisKeyConstant.U_LOGIN_KAPTCHA_KEY);
        String cacheCaptcha= RedisUtil.getCacheStr(captchaKey);

        if (!captcha.equals(cacheCaptcha)) {
            throw new MarcherHintException("验证码错误!", RspBaseCodeEnum.PARAM_ILLEGAL.getCode());
        }
    }
}
