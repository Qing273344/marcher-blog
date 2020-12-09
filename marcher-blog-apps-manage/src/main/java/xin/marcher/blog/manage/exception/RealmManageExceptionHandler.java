package xin.marcher.blog.manage.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xin.marcher.framework.common.constants.GlobalCodeEnum;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.mvc.exception.GlobalExceptionHandler;

/**
 * 模块统一异常处理
 *
 * @author marcher
 */
@Slf4j
@ControllerAdvice
public class RealmManageExceptionHandler extends GlobalExceptionHandler {

    /**
     * shiro 权限异常提示(授权)
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public BaseResult handleAuthorizationException(AuthorizationException ex) {
        this.wrapperLog(ex);
        return BaseResult.error(GlobalCodeEnum.GL_401.getRealCode(), GlobalCodeEnum.GL_401.getRealDesc());
    }

    /**
     * shiro 凭证异常提示(登录)
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public BaseResult handleAuthenticationException(AuthenticationException ex) {
        this.wrapperLog(ex);
        return BaseResult.error(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

}
