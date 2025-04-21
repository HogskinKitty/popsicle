package cn.culpro.trigger.http.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应DTO
 * <p>
 * 用于返回用户登录成功后的JWT令牌等信息
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    /**
     * JWT令牌
     */
    private String token;
    
    /**
     * 令牌类型
     */
    private String tokenType;
    
    /**
     * 过期时间（秒）
     */
    private Long expiresIn;
    
    /**
     * 刷新令牌
     */
    private String refreshToken;
} 