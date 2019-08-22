package xin.marcher.blog.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.utils.Result;

import javax.servlet.ServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

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
     * @return 异常提示
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MarcherHintException.class)
    @ResponseBody
    public Result handleMarcherException(MarcherHintException ex) {
        return Result.error(ex.getCode(), ex.getMsg());
    }

    /**
     * 自定义异常封装返回
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ExceptionHandler(MarcherException.class)
    @ResponseBody
    public Result handleMarcherException(MarcherException ex) {
        log.error(ex.getMsg(), ex);
        return Result.error(ex.getCode(), ex.getMsg());
    }

    /**
     * Hibernate Validated 参数提示封装JSON
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ValidationException.class})
    @ResponseBody
    public Result handleMethodArgumentNotValidException(Exception ex) {
        String errorMessage = "";
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            if (bindingResult.getFieldErrors().size() > 0) {
                FieldError fieldError = bindingResult.getFieldErrors().get(0);
                errorMessage = fieldError.getDefaultMessage();
            }
        }

        if (ex instanceof ValidationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
            if (!constraintViolations.isEmpty()) {
                ConstraintViolation<?> constraintViolation = constraintViolations.iterator().next();
                errorMessage = constraintViolation.getMessage();
            }
        }

        return Result.error(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    /**
     * 404 异常
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Result handlerNoFoundException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(HttpStatus.NOT_FOUND.value(), "你好像访问到了其他地方...");
    }

    /**
     * shiro权限异常提示(授权)
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Result handleAuthorizationException(AuthorizationException ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(RspBaseCodeEnum.NOT_PERMISSION);
    }

    /**
     * shiro凭证异常提示(登录)
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public Result handleAuthenticationException(AuthenticationException ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    /**
     * 顶级异常
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.error();
    }

}
