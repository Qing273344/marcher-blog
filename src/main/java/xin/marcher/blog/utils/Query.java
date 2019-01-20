package xin.marcher.blog.utils;

import lombok.Getter;
import xin.marcher.blog.common.xss.SQLFilter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author marcher
 */
@Getter
public class Query<T> extends LinkedHashMap<String, Object> {

    /**
     * 当前页码
     */
    private int pageNum = 1;

    /**
     * 每页条数
     */
    private int limit = 10;

    public Query() {
    }


    public Query(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        int pageNum = 1;
        if (params.get("pageNum") != null) {
            pageNum = Integer.parseInt((String) params.get("pageNum"));
        }

        int limit = 10;
        if (params.get("limit") != null) {
            limit = Integer.parseInt((String) params.get("limit"));
        }

        this.put("offset", (pageNum - 1) * limit);
        this.put("page", pageNum);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = SQLFilter.sqlInject((String) params.get("sidx"));
        String order = SQLFilter.sqlInject((String) params.get("order"));
        this.put("sidx", sidx);
        this.put("order", order);

        //mybatis-plus分页
//        this.page = new Page<>(pageNum, limit);
//
//        if (EmptyUtil.isNotEmpty(sidx) && EmptyUtil.isNotEmpty(order)) {
//            this.page.setOrderByField(sidx);
//            this.page.setAsc("ASC".equalsIgnoreCase(order));
//        }
    }

}
