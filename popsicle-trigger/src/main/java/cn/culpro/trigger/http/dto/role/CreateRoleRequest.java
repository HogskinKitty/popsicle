package cn.culpro.trigger.http.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创建角色请求
 * <p>
 * 用于接收创建角色的请求参数
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleRequest {
    
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
    
    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;
} 