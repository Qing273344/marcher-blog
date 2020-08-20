package xin.marcher.blog.biz.enums;

import xin.marcher.framework.core.IEnumNorm;

/**
 * 返回code
 *
 * @author marcher
 */
public enum RspBaseCodeEnum implements IEnumNorm {

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

    private final Integer code;
    private final String msg;

    RspBaseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getRealCode() {
        return code;
    }

    @Override
    public String getRealDesc() {
        return msg;
    }
}
