package xin.marcher.blog.account.client.enums;

import xin.marcher.framework.core.IEnumNorm;

/**
 * 用户来源
 *
 * @author marcher
 */
public enum UserSourceEnum implements IEnumNorm {

    /**  */
    USER_SOURCE_PC(1, "PC"),
    USER_SOURCE_WEB_MOVE(2, "web移动"),
    ;

    private final Integer code;
    private final String desc;

    UserSourceEnum(Integer code, String desc) {
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
