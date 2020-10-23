package xin.marcher.blog.article.client.model.response;

import lombok.Data;

/**
 * 标签
 *
 * @author marcher
 */
@Data
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
