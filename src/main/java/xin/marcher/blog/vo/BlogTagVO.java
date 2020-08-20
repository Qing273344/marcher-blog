package xin.marcher.blog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 标签
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogTagVO {

    private Long tagId;

    /**
     * 标签名
     */
    private String name;

    /**
     * 描述
     */
    private String description;
}
