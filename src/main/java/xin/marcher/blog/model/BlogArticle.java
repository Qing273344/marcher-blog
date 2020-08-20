package xin.marcher.blog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import xin.marcher.framework.mybatis.annotation.CreateTime;
import xin.marcher.framework.mybatis.annotation.ModifyTime;

/**
 * 博客文章
 * 
 * @author marcher
 */
@Data
@ToString
@TableName("blog_article")
public class BlogArticle {

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
