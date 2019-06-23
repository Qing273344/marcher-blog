package xin.marcher.blog.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博客类型
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogArticleTypeResp {

    private Long typeId;

    private String name;

    private String description;

    private Integer sort;

    private String icon;

    private Integer available;
}
