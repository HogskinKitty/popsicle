package cn.culpro.application.user.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建用户命令
 * <p>
 * 包含创建用户所需的必要信息
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCommand {
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 姓名
     */
    private String realName;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机号
     */
    private String phoneNumber;
} 