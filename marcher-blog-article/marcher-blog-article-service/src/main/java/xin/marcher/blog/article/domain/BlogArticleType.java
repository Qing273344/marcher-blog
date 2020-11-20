package xin.marcher.blog.article.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.dao.DeletableDO;

/**
 * 博客文章类型管理
 *
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("blog_article_type")
public class BlogArticleType extends DeletableDO {

    /**
     * 文章类型id
     */
    @TableId
    private Long articleTypeId;

    /**
     * 文章编号
     */
    private Long articleId;

    /**
     * 类型id
     */
    private Long typeId;

}
