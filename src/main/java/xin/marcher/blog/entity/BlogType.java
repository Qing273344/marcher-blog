package xin.marcher.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博客类型
 * 
 * @author marcher
 */
@Getter
@Setter
@ToString
@TableName("blog_type")
public class BlogType {

	/**
	 * 类型id
	 */
	@TableId
	private Long typeId;

	/**
	 * 类型名称
	 */
	private String name;

	/**
	 * 类型介绍
	 */
	private String description;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 是否可用
	 */
	private Integer available;

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