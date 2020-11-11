package xin.marcher.blog.manage.shiro.credentials;

import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * TODO session-cookie 模式时密码校验
 * 密码凭证匹配 (验证密码有效性)
 *
 * @author marcher
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

//    @Override
//    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
//        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
//        // 获取用户输入的密码
//        String inPassword = new String(usernamePasswordToken.getPassword());
//
//        // 数据库密码
//        BlogUser blogUser = (BlogUser) info.getPrincipals().getPrimaryPrincipal();
//        String dbPassword = blogUser.getPassword();
//
//        // 用户输入的密码与盐得到加密的密码
//        inPassword = OAuthUtil.encrypt(inPassword, blogUser.getCreateTime().toString());
//
//        boolean match = this.equals(inPassword, dbPassword);
//        if (!match) {
//            throw new AuthenticationException("账号或密码错误~");
//        }
//
//        return true;
//    }
}
