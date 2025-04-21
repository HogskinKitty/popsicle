package cn.culpro.application.role.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新角色命令
 * <p>
 * 封装更新角色的请求参数
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleCommand {
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 角色编码
     */
    private String roleCode;
    
    /**
     * 角色描述
     */
    private String roleDesc;
    
    /**
     * 排序
     */
    private Integer sort;
} 