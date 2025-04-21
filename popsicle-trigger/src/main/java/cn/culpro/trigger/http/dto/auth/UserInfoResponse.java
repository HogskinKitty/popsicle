package cn.culpro.trigger.http.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 用户信息响应DTO
 * <p>
 * 用于返回用户基本信息与权限信息
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 权限集合
     */
    private Set<String> permissions;
    
    /**
     * 角色集合
     */
    private Set<String> roles;
} 