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
    NOT_PERMISSION(3, "是想访问么? 让我想想..."),
    NOT_RESOURCE(5, "没有找到你想要的..."),

    NOT_LOGIN(10, "登录后才可以悄悄的干坏事!"),
    LOGIN_TOKEN_INVALID(11, "登录失效咯, 再登一次吧!"),

    MARCHER_CODE(999, ""),
    ;

    private Integer code;
    private String msg;

    RspBaseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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
