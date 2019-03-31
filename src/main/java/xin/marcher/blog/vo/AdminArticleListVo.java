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

    private Integer isTop;

    private Integer isComment;
}
