package xin.marcher.blog.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 博客文章标题
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogTagFrom {

    private Long tagId;

    @NotBlank(message = "请填写标签名称")
    private String name;

    private String description;
}
