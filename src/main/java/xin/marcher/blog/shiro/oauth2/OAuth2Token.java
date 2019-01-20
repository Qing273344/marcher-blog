package xin.marcher.blog.shiro.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * @author marcher
 */
public class OAuth2Token implements AuthenticationToken {

    private String token;

    OAuth2Token(String token) {
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
