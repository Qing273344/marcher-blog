package xin.marcher.blog.account.client.enums;

import xin.marcher.framework.core.IEnumCodeWithIntro;

import java.util.Arrays;

/**
 * 用户来源
 *
 * @author marcher
 */
public enum UserSourceEnum implements IEnumCodeWithIntro {

    /**  */
    USER_SOURCE_PC(10, "PC"),
    USER_SOURCE_WEB_MOVE(20, "web移动"),
    ;

    public static final String  INTRO = "用户来源";

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserSourceEnum::getRealCode).toArray();

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

    @Override
    public int[] array() {
        return ARRAYS;
    }

    @Override
    public String intro() {
        return INTRO;
    }
}
