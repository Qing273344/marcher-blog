package xin.marcher.blog.utils;

import xin.marcher.blog.common.exception.MarcherHintException;

import java.util.Collection;

public class Assert {

    public static void isBlank(String str, String msg) {
        if (EmptyUtil.isEmptyTrim(str)) {
            throw new MarcherHintException(msg);
        }
    }

    public static void isEmpty(Collection collection, String msg) {
        if (EmptyUtil.isEmpty(collection)) {
            throw new MarcherHintException(msg);
        }
    }

    public static <T> void isEmpty(T[] array, String msg) {
        if (EmptyUtil.isEmpty(array)) {
            throw new MarcherHintException(msg);
        }
    }

    public static void isNull(Object obj, String msg) {
        if (EmptyUtil.isEmpty(obj)) {
            throw new MarcherHintException(msg);
        }
    }

    public static void isNullOrZero(Number number, String msg) {
        if (EmptyUtil.isEmpty(number) || number.longValue() <= 0) {
            throw new MarcherHintException(msg);
        }
    }

}
