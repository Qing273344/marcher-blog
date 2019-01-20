package xin.marcher.blog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * jwt工具
 *
 * @author marcher
 */
@Setter
@Getter
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
    private String header;

    /**
     * 生成jwt token
     *
     * @param userId 用户id
     * @return
     */
    public String generateToken(Long userId) {
        Date nowDate = new Date();

        // 过期时间
        Date expireDate = DateUtils.addHours(nowDate, expire);

        return Jwts.builder()
//                .setHeaderParam("type", "JWT")
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
     * @return
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
     * token是否过期
     *
     * @param expiration    过期时间
     * @return
     *      true:是 false:否
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
