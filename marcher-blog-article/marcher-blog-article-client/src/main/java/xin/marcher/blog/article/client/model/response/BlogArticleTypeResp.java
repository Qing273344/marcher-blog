package xin.marcher.blog.article.client.model.response;

import lombok.Data;

/**
 * 博客类型
 *
 * @author marcher
 */
@Data
public class BlogArticleTypeResp {

    private Long typeId;

    private String name;

    private String description;

    private Integer sort;

    private String icon;

    private Integer available;
}
