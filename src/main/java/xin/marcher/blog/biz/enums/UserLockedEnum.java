package xin.marcher.blog.biz.enums;

import xin.marcher.framework.core.IEnumNorm;

/**
 * 用户锁定状态型
 *
 * @author marcher
 */
public enum UserLockedEnum implements IEnumNorm {

    /**  */
    USER_LOCKED_NORMAL(0, "正常"),
    USER_LOCKED_DISABLE(1, "锁定"),
    ;

    private final Integer code;
    private final String desc;

    UserLockedEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getRealCode() {
        return code;
    }

    @Override
    public String getRealDesc() {
        return desc;
    }
}
