package xin.marcher.blog.article.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.dao.BaseDO;
import xin.marcher.framework.mybatis.dao.DeletableDO;

/**
 * 博客类型
 * 
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("blog_type")
public class BlogType extends DeletableDO {

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

}
