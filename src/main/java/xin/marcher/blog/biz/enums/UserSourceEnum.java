package xin.marcher.blog.biz.enums;

import lombok.Getter;
import xin.marcher.blog.utils.EmptyUtil;

/**
 * 用户来源
 *
 * @author marcher
 */
@Getter
public enum UserSourceEnum {

    /** 保留 */
    ALl(0, ""),
    USER_SOURCE_PC(1, "PC"),
    USER_SOURCE_WEB_MOVE(2, "web移动"),
    ;

    private Integer code;
    private String desc;

    UserSourceEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserSourceEnum get(Integer code) {
        if (EmptyUtil.isEmpty(code)) {
            return null;
        }
        for (UserSourceEnum anEnum : UserSourceEnum.values()) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }
}
