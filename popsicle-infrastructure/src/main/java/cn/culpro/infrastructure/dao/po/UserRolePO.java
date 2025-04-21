package cn.culpro.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色关联持久化对象
 *
 * @author HogskinKitty
 * @date 2025/04/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRolePO {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 角色ID
     */
    private Long roleId;
} 