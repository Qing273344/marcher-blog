package xin.marcher.blog.utils;

import lombok.Getter;
import lombok.Setter;
import xin.marcher.framework.util.EmptyUtil;

import javax.validation.Valid;

/**
 * query
 *
 * @author marcher
 */
@Getter
@Setter
public class Query<T> {

    /**
     * 加入参数校验注解, Controller中也加入该注解即可校验
     */
    @Valid
    private T data;

    private QueryPage page;

    public QueryPage getPage() {
        if (EmptyUtil.isEmpty(this.page) || this.page.getCurPage() == 0) {
            return new QueryPage();
        }
        return page;
    }

}
