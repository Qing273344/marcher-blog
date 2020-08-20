package xin.marcher.blog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import xin.marcher.framework.mybatis.annotation.ModifyTime;

import java.io.Serializable;

/**
 * 博客用户
 *
 * @author marcher
 */
@Data
@ToString
@TableName("blog_user")
public class BlogUser implements Serializable {

	private static final long serialVersionUID = 7628883607894181236L;

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

	/**
	 * 创建时间
	 * TODO: 该字段不做自动更新操作, 时间需与加密中的一致
	 */
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
