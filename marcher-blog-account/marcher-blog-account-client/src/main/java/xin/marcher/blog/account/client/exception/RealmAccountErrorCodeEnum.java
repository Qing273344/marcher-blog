package xin.marcher.blog.account.client.exception;

import xin.marcher.framework.common.core.IEnumNorm;

/**
 * 账号系统，使用 1-001-000-000 段
 *
 * @author marcher
 */
public enum RealmAccountErrorCodeEnum implements IEnumNorm {

    /** ==========  模块 ========== */
    TEMP(1001001001, "temp")
    ;

    private final Integer code;
    private final String message;

    RealmAccountErrorCodeEnum(Integer code, String message) {
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
