package xin.marcher.blog.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xin.marcher.framework.mvc.request.PageParam;

/**
 * query data
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class QueryData extends PageParam {

    private String keyword;
}
