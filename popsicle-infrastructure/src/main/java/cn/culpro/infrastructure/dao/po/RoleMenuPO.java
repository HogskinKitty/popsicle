package cn.culpro.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色菜单关联持久化对象
 *
 * @author HogskinKitty
 * @date 2025/04/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuPO {
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 菜单ID
     */
    private Long menuId;
} 