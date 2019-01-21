package xin.marcher.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 博客用户
 * 
 * @author marcher
 */
@Getter
@Setter
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
	private Integer locked;

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
