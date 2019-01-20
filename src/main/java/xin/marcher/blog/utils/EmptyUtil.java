package xin.marcher.blog.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 空判断工具
 *
 * @author: marcher
 */
public class EmptyUtil {

    public static String toString(Object object) {
        return object == null ? "" : object.toString();
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(String string) {
        return toString(string).isEmpty();
    }

    public static boolean isEmptyTrim(String string) {
        return toString(string).trim().isEmpty();
    }

    public static boolean isEmpty(Object object) {
        return toString(object).isEmpty();
    }

    public static boolean isEmptyTrim(Object object) {
        return toString(object).trim().isEmpty();
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }


    public static boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map map){
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }

    public static boolean isNotEmptyTrim(String string){
        return !isEmptyTrim(string);
    }

    public static boolean isNotEmpty(Object object){
        return !isEmpty(object);
    }

    public static boolean isNotEmptyTrim(Object object){
        return !isEmptyTrim(object);
    }

    public static <T> boolean isNotEmpty(T[] array){
        return !isEmpty(array);
    }
}