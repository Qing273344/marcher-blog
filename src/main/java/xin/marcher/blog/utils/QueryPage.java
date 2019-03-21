package xin.marcher.blog.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询参数
 *
 * @author marcher
 */
@Getter
@Setter
public class QueryPage {

    /**
     * 当前页码
     */
    private int curPage = 1;

    /**
     * 每页条数
     */
    private int limit = 10;
}
