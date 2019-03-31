package xin.marcher.blog.from;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 博客文章
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogArticleFrom {

    private Long articleId;

    @NotBlank(message = "请输入文章标题")
    private String title;

    @NotBlank(message = "请输入文章内容")
    private String articleContent;

    @NotNull(message = "请选择类型")
    private Long typeId;

    @NotEmpty(message = "请选择文章标题")
    private List<Long> tagIdList = new ArrayList<>();

    @NotNull(message = "前选择是否公开文章")
    @Min(1)
    private Integer status;
}
