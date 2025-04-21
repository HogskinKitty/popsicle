package cn.culpro.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 角色持久化对象
 *
 * @author HogskinKitty
 * @date 2025/04/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePO {
    
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
     * 角色排序
     */
    private Integer roleSort;
    
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private Integer dataScope;
    
    /**
     * 状态（0正常 1停用）
     */
    private Integer status;
    
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Integer deleteStatus;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 