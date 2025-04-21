package cn.culpro.infrastructure.security;

import cn.culpro.domain.system.adapter.port.ITokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT令牌提供者实现
 * <p>
 * 提供JWT令牌的生成、验证和解析功能
 *
 * @author HogskinKitty
 * @date 2024/10/29
 */
@Component
public class JwtTokenProvider implements ITokenProvider {
    
    @Value("${jwt.secret:popsicleSecretKey}")
    private String secret;
    
    @Value("${jwt.expiration:86400}")
    private Long expiration;
    
    @Override
    public String generateToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return createToken(claims, username);
    }
    
    @Override
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return Long.valueOf(claims.get("userId").toString());
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
     * 从令牌中提取数据
     *
     * @param token          令牌
     * @param claimsResolver 提取数据的函数
     * @param <T>            返回类型
     * @return 提取的数据
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * 提取令牌中的所有数据
     *
     * @param token 令牌
     * @return 数据
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    /**
     * 检查令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    /**
     * 提取令牌的过期时间
     *
     * @param token 令牌
     * @return 过期时间
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
     * 创建令牌
     *
     * @param claims  数据
     * @param subject 主题（用户名）
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
} 