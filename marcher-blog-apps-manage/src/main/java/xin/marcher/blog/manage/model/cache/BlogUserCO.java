package xin.marcher.blog.manage.model.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * BlogUserCO
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
     * 用户 name
     */
    private String username;

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
