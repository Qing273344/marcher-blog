package xin.marcher.blog.article.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.dao.DeletableDO;

/**
 * 博客文章
 * 
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("blog_article")
public class BlogArticle extends DeletableDO {

	/**
	 * 文章编号
	 */
	@TableId
	private Long articleId;

	/**
	 * 类型
	 */
	private Long typeId;

	/**
	 * 文章标题
	 */
	private String title;

	/**
	 * 摘要，最多200字
	 */
	private String summary;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 是否置顶
	 */
	private Integer isTop;

	/**
	 * 是否开启评论
	 */
	private Integer isComment;

	/**
	 * 点赞量
	 */
	private Integer likedCount;

	/**
	 * 浏览量
	 */
	private Integer viewsCount;

}
