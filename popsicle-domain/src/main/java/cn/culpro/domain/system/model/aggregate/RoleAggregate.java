package cn.culpro.domain.system.model.aggregate;

import cn.culpro.domain.system.model.entity.RoleMenuEntity;
import cn.culpro.types.model.Aggregate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 角色聚合根
 * <p>
 * 负责维护角色的生命周期和一致性，是外部访问角色相关数据的唯一入口
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Getter
@Builder
public class RoleAggregate implements Aggregate<Long> {
    
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
     * 角色描述
     */
    private String roleDesc;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 角色关联的菜单列表
     */
    private List<RoleMenuEntity> roleMenus;
    
    /**
     * 创建新角色
     *
     * @param roleName 角色名称
     * @param roleCode 角色编码
     * @param roleDesc 角色描述
     * @return 角色聚合根
     */
    public static RoleAggregate create(String roleName, String roleCode, String roleDesc) {
        return RoleAggregate.builder()
                .roleName(roleName)
                .roleCode(roleCode)
                .roleDesc(roleDesc)
                .sort(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .roleMenus(new ArrayList<>())
                .build();
    }
    
    /**
     * 更新角色基本信息
     *
     * @param roleName 角色名称
     * @param roleCode 角色编码
     * @param roleDesc 角色描述
     */
    public void updateBasicInfo(String roleName, String roleCode, String roleDesc) {
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.roleDesc = roleDesc;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 更新排序
     *
     * @param sort 排序值
     */
    public void updateSort(Integer sort) {
        this.sort = sort;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 分配菜单权限
     *
     * @param menuId 菜单ID
     */
    public void assignMenu(Long menuId) {
        // 检查是否已分配该菜单
        boolean exists = roleMenus.stream().anyMatch(roleMenu -> Objects.equals(roleMenu.getMenuId(), menuId));
        
        if (!exists) {
            RoleMenuEntity roleMenu = RoleMenuEntity.create(this.roleId, menuId);
            roleMenus.add(roleMenu);
        }
    }
    
    /**
     * 批量分配菜单权限
     *
     * @param menuIds 菜单ID列表
     */
    public void assignMenus(List<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return;
        }
        
        for (Long menuId : menuIds) {
            assignMenu(menuId);
        }
    }
    
    /**
     * 移除菜单权限
     *
     * @param menuId 菜单ID
     */
    public void removeMenu(Long menuId) {
        roleMenus.removeIf(roleMenu -> Objects.equals(roleMenu.getMenuId(), menuId));
    }
    
    /**
     * 清空所有菜单权限
     */
    public void clearMenus() {
        roleMenus.clear();
    }
    
    /**
     * 替换所有菜单权限
     *
     * @param menuIds 菜单ID列表
     */
    public void replaceMenus(List<Long> menuIds) {
        clearMenus();
        assignMenus(menuIds);
    }
    
    /**
     * 获取聚合ID
     *
     * @return 角色ID
     */
    @Override
    public Long getId() {
        return this.roleId;
    }
} 