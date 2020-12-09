package xin.marcher.blog.account.exception;

import xin.marcher.framework.common.core.IEnumNorm;
import xin.marcher.framework.common.exception.BusinessException;

/**
 * realm article exception
 *
 * @author marcher
 */
public class RealmAccountException extends BusinessException {

    private static final long serialVersionUID = -4025564821698558442L;

    public RealmAccountException(String msg) {
        super(msg);
    }

    public RealmAccountException(int code, String msg) {
        super(code, msg);
    }

    public RealmAccountException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public RealmAccountException(IEnumNorm code) {
        super(code);
    }

    public RealmAccountException(IEnumNorm code, Object... args) {
        super(code, args);
    }

}
