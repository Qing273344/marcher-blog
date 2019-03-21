package xin.marcher.blog.biz.enums;

import lombok.Getter;
import xin.marcher.blog.utils.EmptyUtil;

/**
 * 用户类型
 *
 * @author marcher
 */
@Getter
public enum  UserTypeEnum {

    /** 保留 */
    ALl(0, ""),
    USER_TYPE_ME(1, "本帅"),
    USER_TYPE_MANITO(2, "大神"),
    ;

    private Integer code;
    private String desc;

    UserTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserTypeEnum get(Integer code) {
        if (EmptyUtil.isEmpty(code)) {
            return null;
        }
        for (UserTypeEnum anEnum : UserTypeEnum.values()) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }
}
