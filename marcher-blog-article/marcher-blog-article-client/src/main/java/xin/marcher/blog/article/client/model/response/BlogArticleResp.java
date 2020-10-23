package xin.marcher.blog.article.client.model.response;

import lombok.Data;

import java.util.List;

/**
 * 博客文章
 *
 * @author marcher
 */
@Data
public class BlogArticleResp {

    private Long articleId;

    private String title;

    private String articleContent;

    private Long typeId;

    private List<Long> tagIdList;

    private Integer status;

    private Integer isComment;
}
