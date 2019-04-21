package xin.marcher.blog.utils;

import xin.marcher.blog.common.exception.MarcherHintException;

public class Assert {

    public static void isBlank(String str, String msg) {
        if (EmptyUtil.isEmptyTrim(str)) {
            throw new MarcherHintException(msg);
        }
    }

    public static void isNull(Object obj, String msg) {
        if (EmptyUtil.isEmpty(obj)) {
            throw new MarcherHintException(msg);
        }
    }

    public static void isNullOrZero(Number number, String msg) {
        System.out.println(number.intValue());
        if (EmptyUtil.isEmpty(number) || number.longValue() <= 0) {
            throw new MarcherHintException(msg);
        }
    }

}
