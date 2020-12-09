package xin.marcher.blog.manage.exception;

import xin.marcher.framework.common.core.IEnumNorm;
import xin.marcher.framework.common.exception.BusinessException;

/**
 * realm article exception
 *
 * @author marcher
 */
public class RealmManageException extends BusinessException {

    private static final long serialVersionUID = 5582190386789749632L;

    public RealmManageException(String msg) {
        super(msg);
    }

    public RealmManageException(int code, String msg) {
        super(code, msg);
    }

    public RealmManageException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public RealmManageException(IEnumNorm code) {
        super(code);
    }

    public RealmManageException(IEnumNorm code, Object... args) {
        super(code, args);
    }

}
