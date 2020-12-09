package xin.marcher.blog.manage.exception;

import xin.marcher.framework.common.core.IEnumNorm;

/**
 * 管理系统，使用 1-080-000-000 段
 *
 * @author marcher
 */
public enum RealmManageErrorCodeEnum implements IEnumNorm {

    /** ==========  模块 ========== */
    LOGIN_TOKEN_INVALID(1080000001, "登录失效咯, 再登一次吧!")
    ;

    private final Integer code;
    private final String message;

    RealmManageErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public int getRealCode() {
        return code;
    }

    @Override
    public String getRealDesc() {
        return message;
    }
}
