package cn.culpro.application.role.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分配角色菜单命令
 * <p>
 * 封装分配角色菜单权限的请求参数
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleMenusCommand {
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;
} 