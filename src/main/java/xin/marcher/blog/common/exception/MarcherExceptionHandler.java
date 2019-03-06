package xin.marcher.blog.common.exception;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.utils.Result;

/**
 * 统一异常处理
 *
 * @author marcher
 */
@Slf4j
@ControllerAdvice
public class MarcherExceptionHandler {

    /**
     * 自定义异常(提示)封装返回
     *
     * @param ex 异常
     * @return
     *      异常提示
     */
    @ExceptionHandler(MarcherHintException.class)
    @ResponseBody
    public static Result handleMarcherException(MarcherHintException ex) {
        return Result.error(ex.getCode(), ex.getMsg());
    }

    /**
     * 自定义异常封装返回
     *
     * @param ex 异常
     * @return
     *      异常提示
     */
    @ExceptionHandler(MarcherException.class)
    @ResponseBody
    public static Result handleMarcherException(MarcherException ex) {
        log.error(ex.getMsg(), ex);
        return Result.error(ex.getCode(), ex.getMsg());
    }

    /**
     * Hibernate Validated 参数提示封装JSON
     *
     * @param ex 异常
     * @return
     *      异常提示
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public static Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder(bindingResult.getFieldErrors().size() * 16);

        if (bindingResult.getFieldErrors().size() > 0) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            errorMessage.append(fieldError.getDefaultMessage());
        }
        return Result.error(errorMessage.toString());
    }

    /**
     * 404 异常
     * @param ex    异常
     * @return
     *      异常提示
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Result handlerNoFoundException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(404, "路径不存在，请检查路径是否正确");
    }

    /**
     * shiro权限异常提示
     *
     * @param ex    异常
     * @return
     *      异常提示
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public static Result handleAuthorizationException(AuthorizationException ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(RspBaseCodeEnum.PERMISSION_NOT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public static Result handleUnauthorizedException(UnauthorizedException ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(RspBaseCodeEnum.PERMISSION_NOT);
    }

    /**
     * 顶级异常
     *
     * @param ex 异常
     * @return
     *      异常提示
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.error();
    }

}
