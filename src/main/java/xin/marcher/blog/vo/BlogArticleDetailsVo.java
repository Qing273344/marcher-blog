package xin.marcher.blog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博客详情
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogArticleDetailsVo {

    private Long articleId;

    private String title;

    private Integer isComment;

    private String articleContentMd;

}
