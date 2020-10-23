package xin.marcher.blog.account.client.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 注册表单
 *
 * @author marcher
 */
@Setter
@Getter
@ToString
public class RegisterReqs {

    @NotBlank(message = "请输入用户名")
    private String username;

    @NotBlank(message = "请输入密码")
    private String password;
}
