package xin.marcher.blog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xin.marcher.framework.mvc.validation.groups.GroupUpdate;

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
public class BlogArticleTypeDTO {

    @NotNull(message = "请选择需要修改的分类", groups = GroupUpdate.class)
    private Long typeId;

    @NotBlank(message = "请填写名称")
    private String name;

    private String description;

    private Integer sort;

    private String icon;

    private Integer available;
}
