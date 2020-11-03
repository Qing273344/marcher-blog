package xin.marcher.blog.account.model.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * BlogUser
 *
 * @author marcher
 */
@Data
public class BlogUserCO implements Serializable {

    private static final long serialVersionUID = -53965383960258369L;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 是否锁定
     */
    private Integer isLocked;
}
