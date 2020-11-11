package xin.marcher.blog.manage.shiro.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * @author marcher
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    JwtToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
