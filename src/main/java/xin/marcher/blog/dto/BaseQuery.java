package xin.marcher.blog.dto;

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
public class BaseQuery extends PageParam {

    private String keyword;
}
