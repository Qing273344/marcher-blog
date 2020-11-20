package xin.marcher.blog.manage.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
public class BlogUserVO {

    private Long userId;

    private String username;

    private String  nickName;

    private String avatar;

    private Integer userType;

}
