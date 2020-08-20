package xin.marcher.blog.vo;

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

    private String username;

    private String  nickName;

    private String avatar;

    private Integer userType;


}
