package xin.marcher.blog.article.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BlogType)) return false;
		if (!super.equals(o)) return false;

		BlogType blogType = (BlogType) o;

		if (getTypeId() != null ? !getTypeId().equals(blogType.getTypeId()) : blogType.getTypeId() != null)
			return false;
		if (getName() != null ? !getName().equals(blogType.getName()) : blogType.getName() != null) return false;
		if (getDescription() != null ? !getDescription().equals(blogType.getDescription()) : blogType.getDescription() != null)
			return false;
		if (getSort() != null ? !getSort().equals(blogType.getSort()) : blogType.getSort() != null) return false;
		if (getIcon() != null ? !getIcon().equals(blogType.getIcon()) : blogType.getIcon() != null) return false;
		return getAvailable() != null ? getAvailable().equals(blogType.getAvailable()) : blogType.getAvailable() == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (getTypeId() != null ? getTypeId().hashCode() : 0);
		result = 31 * result + (getName() != null ? getName().hashCode() : 0);
		result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
		result = 31 * result + (getSort() != null ? getSort().hashCode() : 0);
		result = 31 * result + (getIcon() != null ? getIcon().hashCode() : 0);
		result = 31 * result + (getAvailable() != null ? getAvailable().hashCode() : 0);
		return result;
	}


}
