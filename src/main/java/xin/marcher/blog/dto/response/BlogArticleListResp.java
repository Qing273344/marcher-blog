package xin.marcher.blog.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博客文章列表
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogArticleListResp {

    private Long articleId;

    private String title;

    private String timeStr;

    private Integer likedCount;
}
