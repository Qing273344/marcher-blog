package xin.marcher.blog.manage.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 发布文章
 *
 * @author marcher
 */
@Data
@ApiModel(value = "发布文章")
public class PublishDTO {

    private Long articleId;

    @NotBlank(message = "请输入文章标题")
    @ApiModelProperty(value = "文章标题")
    private String title;

    @NotBlank(message = "请输入文章内容")
    @ApiModelProperty(value = "文章内容")
    private String articleContent;

    @NotNull(message = "请选择文章类型")
    @ApiModelProperty(value = "文章类型")
    private Long typeId;

    @NotEmpty(message = "请选择文章标签")
    @ApiModelProperty(value = "文章标签集合")
    private List<Long> tagIdList;

    @NotNull(message = "请选择是否公开文章")
    @ApiModelProperty(value = "是否公开文章")
    private Integer status;

    @NotNull(message = "请选择是否开启评论")
    @ApiModelProperty(value = "是否开启评论")
    private Integer isComment;
}
