package xin.marcher.blog.model.cache;

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

    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    private Integer userType;

    private Integer isLocked;
}
