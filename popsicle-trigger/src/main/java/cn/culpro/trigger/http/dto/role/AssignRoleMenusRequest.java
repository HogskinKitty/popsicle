package cn.culpro.trigger.http.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分配角色菜单请求
 * <p>
 * 用于接收分配角色菜单的请求参数
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleMenusRequest {
    
    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;
} 