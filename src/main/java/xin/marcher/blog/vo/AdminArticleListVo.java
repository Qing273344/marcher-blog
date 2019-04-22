package xin.marcher.blog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文章列表 - 后台
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class AdminArticleListVo {

    private Long articleId;

    private String title;

    private String timeStr;

    private Integer status;

    private Integer isTop;

    private Integer isComment;

    private Integer likedCount;

    private Integer viewsCount;
}
