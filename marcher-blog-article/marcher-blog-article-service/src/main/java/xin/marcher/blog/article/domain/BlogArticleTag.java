package xin.marcher.blog.article.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.dao.BaseDO;
import xin.marcher.framework.mybatis.dao.DeletableDO;

/**
 * 博客文章标签关联
 *
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("blog_article_tag")
public class BlogArticleTag extends DeletableDO {

    /**
     * 文章标签id
     */
    @TableId
    private Long articleTagId;

    /**
     * 文章编号
     */
    private Long articleId;

    /**
     * 标签id
     */
    private Long tagId;

}
