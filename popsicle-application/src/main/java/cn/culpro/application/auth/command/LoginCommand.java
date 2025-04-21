package cn.culpro.application.auth.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录命令
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCommand {
    
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