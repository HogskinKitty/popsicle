package cn.culpro.application.role.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色查询对象
 * <p>
 * 封装角色查询条件和分页参数
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleQuery {
    
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
    private int pageNum = 1;
    
    /**
     * 每页大小
     */
    private int pageSize = 10;
} 