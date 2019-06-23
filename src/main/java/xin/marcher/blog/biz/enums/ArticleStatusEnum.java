package xin.marcher.blog.biz.enums;

import lombok.Getter;
import xin.marcher.blog.utils.EmptyUtil;

/**
 * 文章状态
 *
 * @author marcher
 */
@Getter
public enum ArticleStatusEnum {

    /**  */
    ALl(0, ""),
    ARTICLE_STATUS_DRAFT(1, "草稿"),
    ARTICLE_STATUS_PUBLISH(2, "已发布"),
    ;

    private Integer code;
    private String desc;

    ArticleStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ArticleStatusEnum get(Integer code) {
        if (EmptyUtil.isEmpty(code)) {
            return null;
        }
        for (ArticleStatusEnum anEnum : ArticleStatusEnum.values()) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }
}
