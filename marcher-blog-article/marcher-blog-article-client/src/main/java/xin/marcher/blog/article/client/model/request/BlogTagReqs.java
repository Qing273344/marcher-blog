package xin.marcher.blog.article.client.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 博客文章标题
 *
 * @author marcher
 */
@Data
public class BlogTagReqs {

    private Long tagId;

    @NotBlank(message = "请填写标签名称")
    private String name;

    private String description;
}
