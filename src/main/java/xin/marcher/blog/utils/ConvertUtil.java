package xin.marcher.blog.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 转换器
 *
 * @author marcher
 */
public class ConvertUtil {

    /**
     * 数组转List
     *
     * @param array 数组
     * @param <T>   泛型
     * @return
     *      list
     */
    public static <T> List<T> arrayToList(T[] array) {
        List<T> list = new ArrayList<>();
        if (EmptyUtil.isNotEmpty(array)) {
            list = Stream.of(array).collect(Collectors.toList());
        }
        return list;
    }
}
