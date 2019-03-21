package xin.marcher.blog.from;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 博客文章类型
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogArticleTypeFrom {

    private Long typeId;

    @NotBlank(message = "请填写名称")
    private String name;

    private String description;

    private Integer sort;

    private String icon;

    @NotNull(message = "是否可用必填")
    private Integer available;
}
