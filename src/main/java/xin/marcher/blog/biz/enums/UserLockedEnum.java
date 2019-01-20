package xin.marcher.blog.biz.enums;

import lombok.Getter;
import xin.marcher.blog.utils.EmptyUtil;

/**
 * 用户锁定状态型
 *
 * @author marcher
 */
@Getter
public enum UserLockedEnum {

    /** 禁用 */
    USER_LOCKED_DISABLE(0, "锁定"),
    USER_LOCKED_NORMAL(1, "正常"),
    ;

    private Integer code;
    private String desc;

    UserLockedEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserLockedEnum get(Integer code) {
        if (EmptyUtil.isEmpty(code)) {
            return null;
        }
        for (UserLockedEnum anEnum : UserLockedEnum.values()) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }
}
