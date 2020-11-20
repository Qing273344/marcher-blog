package xin.marcher.blog.account.client.enums;

import xin.marcher.framework.core.IEnumCodeWithIntro;

import java.util.Arrays;

/**
 * 用户类型
 *
 * @author marcher
 */
public enum UserTypeEnum implements IEnumCodeWithIntro {

    /**  */
    USER_TYPE_ME(10, "本帅"),
    USER_TYPE_MANITO(20, "大神"),
    ;

    public static final String  INTRO = "用户类型";

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserTypeEnum::getRealCode).toArray();

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

    @Override
    public int[] array() {
        return ARRAYS;
    }

    @Override
    public String intro() {
        return INTRO;
    }
}
