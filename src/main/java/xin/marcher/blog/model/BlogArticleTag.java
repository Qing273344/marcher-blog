package xin.marcher.blog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import xin.marcher.framework.mybatis.annotation.CreateTime;
import xin.marcher.framework.mybatis.annotation.ModifyTime;

/**
 * 博客文章标签关联
 *
 * @author marcher
 */
@Data
@ToString
@TableName("blog_article_tag")
public class BlogArticleTag {

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

    /**
     * 创建时间
     */
    @CreateTime
    private Long createTime;

    /**
     * 修改时间
     */
    @ModifyTime
    private Long modifyTime;

    /**
     * 逻辑删除(0:正常,1:删除)
     */
    @TableLogic
    private Integer deleted;
}
