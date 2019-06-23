package xin.marcher.blog.dto.response;

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
public class BlogTagResp {

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
