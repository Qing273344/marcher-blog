package xin.marcher.blog.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xin.marcher.blog.validator.group.FirstGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 博客文章标题
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogTagReq {

    @NotNull(message = "请选择需要修改的标签", groups = FirstGroup.class)
    private Long tagId;

    @NotBlank(message = "请填写标签名称")
    private String name;

    private String description;
}
