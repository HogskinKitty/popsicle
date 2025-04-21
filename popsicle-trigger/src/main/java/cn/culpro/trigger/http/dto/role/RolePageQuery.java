package cn.culpro.trigger.http.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色分页查询
 * <p>
 * 用于接收角色分页查询的参数
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePageQuery {
    
    /**
     * 角色名称（模糊查询）
     */
    private String roleName;
    
    /**
     * 角色编码（模糊查询）
     */
    private String roleCode;
    
    /**
     * 页码
     */
    private Integer pageNum = 1;
    
    /**
     * 每页大小
     */
    private Integer pageSize = 10;
} 