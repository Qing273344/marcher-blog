package xin.marcher.blog.vo;

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
public class BlogArticleListVO {

    private Long articleId;

    private String title;

    private String timeStr;

    private Integer likedCount;
}
