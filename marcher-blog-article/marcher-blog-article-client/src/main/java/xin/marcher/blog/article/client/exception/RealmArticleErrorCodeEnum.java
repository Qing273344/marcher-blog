package xin.marcher.blog.article.client.exception;

import xin.marcher.framework.core.IEnumNorm;

/**
 * 文章系统，使用 1-002-000-000 段
 *
 * @author marcher
 */
public enum RealmArticleErrorCodeEnum implements IEnumNorm {

    /** ==========  模块 ========== */
    RA_TEMP(1002001001, "temp")
    ;

    private final Integer code;
    private final String message;

    RealmArticleErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public int getRealCode() {
        return code;
    }

    @Override
    public String getRealDesc() {
        return message;
    }
}
