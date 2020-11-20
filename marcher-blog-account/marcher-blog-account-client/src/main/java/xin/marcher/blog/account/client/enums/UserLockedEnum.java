package xin.marcher.blog.account.client.enums;

import xin.marcher.framework.core.IEnumCodeWithIntro;

import java.util.Arrays;

/**
 * 用户锁定状态
 *
 * @author marcher
 */
public enum UserLockedEnum implements IEnumCodeWithIntro {

    /**  */
    USER_LOCKED_NORMAL(0, "正常"),
    USER_LOCKED_DISABLE(1, "锁定"),
    ;

    public static final String  INTRO = "用户锁定状态";

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserLockedEnum::getRealCode).toArray();

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

    @Override
    public int[] array() {
        return ARRAYS;
    }

    @Override
    public String intro() {
        return INTRO;
    }
}
