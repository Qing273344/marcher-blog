package xin.marcher.blog.article.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.dao.BaseDO;
import xin.marcher.framework.mybatis.dao.DeletableDO;

/**
 * 标签
 * 
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("blog_tag")
public class BlogTag extends DeletableDO {

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

}