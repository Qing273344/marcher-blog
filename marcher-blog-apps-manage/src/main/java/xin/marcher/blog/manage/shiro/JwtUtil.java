package xin.marcher.blog.manage.shiro;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import xin.marcher.framework.common.util.DateUtil;
import xin.marcher.framework.common.util.EmptyUtil;

import java.util.Date;

/**
 * jwt 工具
 *
 * 前端需要定义请求头 Authorization, 该请求头对应的值需以 'Bearer ' 开头. 栗子 --> Authorization: Bearer xxx
 *
 * @author marcher
 */
@Data
@Slf4j
@Component
@RefreshScope
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * 密钥
     */
    private String secret;

    /**
     * 有效期限
     */
    private int expire;

    /**
     * 生成jwt token
     *
     * @param userId 用户id
     * @return token
     */
    public String generateToken(Long userId) {
        Date nowDate = new Date();
        Date expireDate = DateUtil.addHours(nowDate, expire);

        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析 token
     * jjwt 提供的 parser 传入秘钥
     *
     * @param token token
     */
    public Claims getClaimFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("validate is token error ", e);
            return null;
        }
    }

    /**
     * token 是否过期
     *
     * @param expiration 过期时间
     * @return true:是 false:否
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return isTokenExpired(expiredDate);
    }

    /**
     * 从 token 中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimFromToken(token);
        return claims.getExpiration();
    }


    /**
     * 通过 token 获取 userId
     *
     * @param token token
     * @return userId
     */
    public Long getUserIdFromToke(String token) {
        if (EmptyUtil.isEmpty(token)) {
            return null;
        }
        Claims claims = getClaimFromToken(token);
        if (EmptyUtil.isEmpty(claims) || isTokenExpired(claims.getExpiration())) {
            return null;
        }
        return Long.parseLong(claims.getSubject());
    }

    /**
     * 当原来的 token 没过期时是可以刷新的
     */
    public String refreshHeadToken(String token) {
        if (EmptyUtil.isEmpty(token)) {
            return null;
        }

        // token 校验不通过
        Claims claims = getClaimFromToken(token);
        if (claims == null) {
            return null;
        }

        // 如果 token 已经过期，不支持刷新
        if (isTokenExpired(token)) {
            return null;
        }
        //如果token在30分钟之内刚刷新过，返回原token
        if (tokenRefreshJustBefore(token, 30 * 60)) {
            return token;
        } else {
            claims.put(CLAIM_KEY_CREATED, new Date());
            Long userId = getUserIdFromToke(token);
            return generateToken(userId);
        }
    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     *
     * @param token 原token
     * @param time  指定时间（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if (refreshDate.after(created) && refreshDate.before(cn.hutool.core.date.DateUtil.offsetSecond(created, time))) {
            return true;
        }
        return false;
    }
}
