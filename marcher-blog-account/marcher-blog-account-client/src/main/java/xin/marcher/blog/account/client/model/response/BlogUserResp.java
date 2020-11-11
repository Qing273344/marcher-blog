package xin.marcher.blog.account.client.model.response;

import lombok.Data;

/**
 * user
 *
 * @author marcher
 */
@Data
public class BlogUserResp {

    private String userId;

    private String username;

    private String  nickName;

    private String avatar;

    private Integer userType;

}
