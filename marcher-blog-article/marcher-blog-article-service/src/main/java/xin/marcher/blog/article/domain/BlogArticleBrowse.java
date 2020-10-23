package xin.marcher.blog.article.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.dao.DeletableDO;

/**
 * 用户浏览文章
 * 
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("blog_article_browse")
public class BlogArticleBrowse extends DeletableDO {

	/**
	 * 文章浏览ID
	 */
	@TableId
	private Long browseId;

	/**
	 * 文章ID
	 */
	private Long articleId;

	/**
	 * 已登录用户ID
	 */
	private Long userId;

	/**
	 * 用户IP
	 */
	private String userIp;

	/**
	 * 浏览时间
	 */
	private Long browseTime;

}
