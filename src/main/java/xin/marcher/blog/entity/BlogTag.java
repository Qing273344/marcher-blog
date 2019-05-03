package xin.marcher.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xin.marcher.blog.common.annotion.CreateTime;
import xin.marcher.blog.common.annotion.ModifyTime;

/**
 * 标签
 * 
 * @author marcher
 */
@Getter
@Setter
@ToString
@TableName("blog_tag")
public class BlogTag {

	/**
	 * 
	 */
	@TableId
	private Long tagId;

	/**
	 * 标签名
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

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