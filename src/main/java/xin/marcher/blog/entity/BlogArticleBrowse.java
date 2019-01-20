package xin.marcher.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户浏览文章
 * 
 * @author marcher
 */
@Getter
@Setter
@ToString
@TableName("blog_article_browse")
public class BlogArticleBrowse {

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

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 修改时间
	 */
	private Long modifyTime;

	/**
	 * 逻辑删除(0:正常,1:删除)
	 */
	@TableLogic
	private Integer deleted;

}
