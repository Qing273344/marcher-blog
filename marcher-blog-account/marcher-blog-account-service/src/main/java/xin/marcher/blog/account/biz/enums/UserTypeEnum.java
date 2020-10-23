package xin.marcher.blog.account.biz.enums;

import xin.marcher.framework.core.IEnumNorm;

/**
 * 用户类型
 *
 * @author marcher
 */
public enum UserTypeEnum implements IEnumNorm {

    /**  */
    USER_TYPE_ME(1, "本帅"),
    USER_TYPE_MANITO(2, "大神"),
    ;

    private final Integer code;
    private final String desc;

    UserTypeEnum(Integer code, String desc) {
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
