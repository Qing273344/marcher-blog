package xin.marcher.blog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import xin.marcher.framework.mybatis.annotation.CreateTime;
import xin.marcher.framework.mybatis.annotation.ModifyTime;

/**
 * 标签
 * 
 * @author marcher
 */
@Data
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