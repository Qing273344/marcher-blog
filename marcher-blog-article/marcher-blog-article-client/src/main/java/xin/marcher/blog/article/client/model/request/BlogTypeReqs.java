package xin.marcher.blog.article.client.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 博客类型
 *
 * @author marcher
 */
@Data
public class BlogTypeReqs {

    private Long typeId;

    @NotBlank(message = "请填写名称")
    private String name;

    private String description;

    private Integer sort;

    private String icon;

    private Integer available;
}
