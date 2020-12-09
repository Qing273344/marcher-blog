package xin.marcher.blog.article.exception;

import xin.marcher.framework.common.core.IEnumNorm;
import xin.marcher.framework.common.exception.BusinessException;

/**
 * realm article exception
 *
 * @author marcher
 */
public class RealmArticleException extends BusinessException {

    private static final long serialVersionUID = -4025564821698558442L;

    public RealmArticleException(String msg) {
        super(msg);
    }

    public RealmArticleException(int code, String msg) {
        super(code, msg);
    }

    public RealmArticleException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public RealmArticleException(IEnumNorm code) {
        super(code);
    }

    public RealmArticleException(IEnumNorm code, Object... args) {
        super(code, args);
    }

}
