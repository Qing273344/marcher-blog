package xin.marcher.blog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 博客文章
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogArticleDTO {

    private Long articleId;

    @NotBlank(message = "请输入文章标题")
    private String title;

    @NotBlank(message = "请输入文章内容")
    private String articleContent;

    @NotNull(message = "请选择类型")
    private Long typeId;

    @NotEmpty(message = "请选择文章标题")
    private List<Long> tagIdList;

    @NotNull(message = "请选择是否公开文章")
    private Integer status;

    @NotNull(message = "前选择是否开启评论")
    private Integer isComment;
}
