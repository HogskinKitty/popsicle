package cn.culpro.trigger.http.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录请求DTO
 * <p>
 * 用于接收用户登录请求参数
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 验证码
     */
    private String captchaCode;
    
    /**
     * 验证码键
     */
    private String captchaKey;
    
    /**
     * 是否记住我
     */
    private Boolean rememberMe;
} 