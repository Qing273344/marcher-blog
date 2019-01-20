package xin.marcher.blog.from;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单
 *
 * @author marcher
 */
@Setter
@Getter
@ToString
public class LoginFrom {

    @NotBlank(message = "请输入用户名")
    private String username;

    @NotBlank(message = "请输入密码")
    private String password;

    @NotBlank(message = "请输入验证码")
    private String captcha;
}
