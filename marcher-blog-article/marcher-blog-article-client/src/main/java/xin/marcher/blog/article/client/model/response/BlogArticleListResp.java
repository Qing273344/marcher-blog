package xin.marcher.blog.article.client.model.response;

import lombok.Data;

/**
 * 博客文章列表
 *
 * @author marcher
 */
@Data
public class BlogArticleListResp {

    private Long articleId;

    private String title;

    private String timeStr;

    private Integer likedCount;

    private Integer viewsCount;

    private Integer isTop;

    private Integer isComment;

    private Integer status;

}
