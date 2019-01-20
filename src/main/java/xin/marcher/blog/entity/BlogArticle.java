package xin.marcher.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博客文章
 * 
 * @author marcher
 */
@Getter
@Setter
@ToString
@TableName("blog_article")
public class BlogArticle {

	/**
	 * 文章编号
	 */
	@TableId
	private Long articleId;

	/**
	 * 用户ID
	 */
	private Long userId;

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
	private Integer top;

	/**
	 * 是否推荐
	 */
	private Integer recommended;

	/**
	 * 是否原创
	 */
	private Integer original;

	/**
	 * 是否开启评论
	 */
	private Integer comment;

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
