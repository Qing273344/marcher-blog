package xin.marcher.blog.utils;

import xin.marcher.framework.exception.HintException;
import xin.marcher.framework.util.EmptyUtil;

import java.util.Collection;

/**
 * assert
 *
 * @author marcher
 */
public class Assert {

    public static void isBlank(String str, String msg) {
        if (EmptyUtil.isEmptyTrim(str)) {
            throw new HintException(msg);
        }
    }

    public static void isEmpty(Collection collection, String msg) {
        if (EmptyUtil.isEmpty(collection)) {
            throw new HintException(msg);
        }
    }

    public static <T> void isEmpty(T[] array, String msg) {
        if (EmptyUtil.isEmpty(array)) {
            throw new HintException(msg);
        }
    }

    public static void isNull(Object obj, String msg) {
        if (EmptyUtil.isEmpty(obj)) {
            throw new HintException(msg);
        }
    }

    public static void isNullOrZero(Number number, String msg) {
        if (EmptyUtil.isEmpty(number) || number.longValue() <= 0) {
            throw new HintException(msg);
        }
    }

}
