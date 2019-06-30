package xin.marcher.blog.utils;

import xin.marcher.blog.common.exception.MarcherException;

import java.lang.reflect.Method;

/**
 * @author marcher
 */
public class EnumUtil {

    public static final String ENUM_CODE_METHOD = "getCode";

    public static final String DESC_METHOD = "getDesc";
    public static final String DESC_MSG_METHOD = "getMsg";

    /**
     * 通过value值获取对应的描述信息
     */
    public static <T> String getEnumDescription(Object enumValue, Class clazz) {
        return getEnumDescription(enumValue, clazz, null);
    }

    /**
     * 通过value值获取对应的描述信息
     */
    public static <T> String getEnumDescription(Object enumValue, Class clazz, String descriptionFieldName) {
        if (enumValue == null) {
            return "";
        }

        if (!clazz.isEnum()) {
            return "";
        }
        T[] enums = (T[]) clazz.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return "";
        }

        descriptionFieldName = descriptionFieldName == null ? DESC_METHOD : descriptionFieldName;

        for (T t : enums) {
            try {
                Object resultValue = getMethodValue(ENUM_CODE_METHOD, t);
                if (resultValue.toString().equalsIgnoreCase(enumValue.toString())) {
                    Object description = getMethodValue(descriptionFieldName, t);
                    return description.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new MarcherException(e.getMessage(), e);
            }
        }
        return "";
    }

    /**
     * 通过方法名称获取方法值，忽略大小写
     */
    public static <T> Object getMethodValue(String methodName, T t) throws MarcherException {
        Object result = "";
        try {
            Method method = t.getClass().getMethod(methodName);
            if (method != null) {
                result = method.invoke(t);
            }
            result = result == null ? "" : result;
        } catch (Exception e) {
            throw new MarcherException("获取方法出错:[" + methodName + "]", e);
        }
        return result;
    }
}
