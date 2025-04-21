package cn.culpro.domain.system.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 角色菜单关系实体
 * <p>
 * 表示角色与菜单之间的关联关系
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuEntity {
    
    /**
     * 关系ID
     */
    private Long id;
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 菜单ID
     */
    private Long menuId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建角色菜单关系
     *
     * @param roleId 角色ID
     * @param menuId 菜单ID
     * @return 角色菜单关系实体
     */
    public static RoleMenuEntity create(Long roleId, Long menuId) {
        return RoleMenuEntity.builder()
                .roleId(roleId)
                .menuId(menuId)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }
} 