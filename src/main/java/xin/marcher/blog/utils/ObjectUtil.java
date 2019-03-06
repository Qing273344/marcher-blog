package xin.marcher.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * obj工具类
 *
 * @author marcher
 */
@Slf4j
public class ObjectUtil {

    /**
     * copy属性
     *
     * @param source    源
     * @param target    目标
     */
    public static void copyProperties(Object source, Object target){
        try {
            BeanUtils.copyProperties(source, target);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}
