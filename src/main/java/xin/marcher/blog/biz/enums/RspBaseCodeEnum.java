package xin.marcher.blog.biz.enums;

import lombok.Getter;
import xin.marcher.blog.utils.EmptyUtil;

/**
 * 返回code
 *
 * @author marcher
 */
@Getter
public enum RspBaseCodeEnum {

    /** ok */
    OK(0, "ok"),
    PARAM_MISS(1, "请提交"),
    PARAM_ILLEGAL(2, "错误的参数"),
    PERMISSION_NOT(3, "permission"),

    LOGIN_FAIL(10, "请登录"),
    LOGIN_TOKEN_INVALID(11, "token失效"),
    ;

    private Integer code;
    private String msg;

    RspBaseCodeEnum(Integer code, String desc) {
        this.code = code;
        this.msg = desc;
    }

    public static RspBaseCodeEnum get(Integer code) {
        if (EmptyUtil.isEmpty(code)) {
            return null;
        }
        for (RspBaseCodeEnum anEnum : RspBaseCodeEnum.values()) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }
}
