package xin.marcher.blog.article.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.dao.DeletableDO;

/**
 * 博客文章内容
 * 
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("blog_article_content")
public class BlogArticleContent extends DeletableDO {

	/**
	 * 文章编号
	 */
	@TableId
	private Long articleId;

	/**
	 * markdown版的文章内容
	 */
	private String contentMd;

}
