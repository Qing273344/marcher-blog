package xin.marcher.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博客文章内容
 * 
 * @author marcher
 */
@Getter
@Setter
@ToString
@TableName("blog_article_content")
public class BlogArticleContent {

	/**
	 * 文章编号
	 */
	@TableId
	private Long articleId;

	/**
	 * markdown版的文章内容
	 */
	private String contentMd;

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
