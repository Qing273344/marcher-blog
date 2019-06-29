package xin.marcher.blog.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xin.marcher.blog.validator.group.FirstGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 博客类型
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogArticleTypeReq {

    @NotNull(message = "请选择需要修改的分类", groups = FirstGroup.class)
    private Long typeId;

    @NotBlank(message = "请填写名称")
    private String name;

    private String description;

    private Integer sort;

    private String icon;

//    @NotNull(message = "是否可用必填")
    private Integer available;
}
