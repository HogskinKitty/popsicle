package cn.culpro.domain.system.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户角色关系实体
 * <p>
 * 表示用户与角色之间的关联关系
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {
    
    /**
     * 关系ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建用户角色关系
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 用户角色关系实体
     */
    public static UserRoleEntity create(Long userId, Long roleId) {
        return UserRoleEntity.builder()
                .userId(userId)
                .roleId(roleId)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }
} 