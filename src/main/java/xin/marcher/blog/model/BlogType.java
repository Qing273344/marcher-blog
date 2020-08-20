package xin.marcher.blog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import xin.marcher.framework.mybatis.annotation.CreateTime;
import xin.marcher.framework.mybatis.annotation.ModifyTime;

/**
 * 博客类型
 * 
 * @author marcher
 */
@Data
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
