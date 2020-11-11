package xin.marcher.blog.manage.shiro;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import xin.marcher.framework.util.DateUtil;
import xin.marcher.framework.util.EmptyUtil;

import java.util.Date;

/**
 * jwt工具
 *
 * @author marcher
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtUtil {

    /**
     * 密钥
     */
    private String secret;

    /**
     * 有效期限
     */
    private int expire;

    /**
     * 存储 token
     */
    private String token;

    /**
     * 生成jwt token
     *
     * @param userId 用户id
     * @return
     *      token
     */
    public String generateToken(Long userId) {
        Date nowDate = new Date();

        // 过期时间
        Date expireDate = DateUtil.addHours(new Date(), expire);

        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析token
     * jjwt提供的parser传入秘钥
     *
     * @param token token
     */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token 是否过期
     *
     * @param expiration    过期时间
     * @return
     *      true:是 false:否
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    /**
     * 通过token获取userId
     *
     * @param token token
     * @return
     *      userId
     */
    public Long getUserIdFromToke(String token) {
        if (EmptyUtil.isEmpty(token)) {
            return null;
        }
        Claims claims = getClaimByToken(token);
        if (EmptyUtil.isEmpty(claims) || isTokenExpired(claims.getExpiration())) {
            return null;
        }
        return Long.parseLong(claims.getSubject());
    }
}
