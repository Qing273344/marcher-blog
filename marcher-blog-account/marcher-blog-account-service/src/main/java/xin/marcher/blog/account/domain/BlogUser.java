package xin.marcher.blog.account.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.dao.DeletableDO;

/**
 * 博客用户
 *
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("blog_user")
public class BlogUser extends DeletableDO {

	/**
	 * 用户编号
	 */
	@TableId
	private Long userId;

	/**
	 * 账号
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 用户类型, 1:本帅, 2普通用户
	 */
	private Integer userType;

	/**
	 * 用户来源
	 */
	private Integer source;

	/**
	 * 状态(0:锁定,1:正常)
	 */
	private Integer isLocked;

}
