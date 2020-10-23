package xin.marcher.blog.article.client.model.response;

import lombok.Data;

/**
 * 博客详情
 *
 * @author marcher
 */
@Data
public class BlogArticleDetailsResp {

    private Long articleId;

    private String title;

    private Integer isComment;

    private String articleContentMd;

}
