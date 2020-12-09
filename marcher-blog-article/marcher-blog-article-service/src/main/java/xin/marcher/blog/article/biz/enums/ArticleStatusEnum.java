package xin.marcher.blog.article.biz.enums;


import xin.marcher.framework.common.core.IEnumNorm;

/**
 * 文章状态
 *
 * @author marcher
 */
public enum ArticleStatusEnum implements IEnumNorm {

    /**  */
    ARTICLE_STATUS_DRAFT(1, "草稿"),
    ARTICLE_STATUS_PUBLISH(2, "已发布"),
    ;

    private final Integer code;
    private final String desc;

    ArticleStatusEnum(Integer code, String desc) {
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
