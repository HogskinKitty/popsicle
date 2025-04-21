package cn.culpro.domain.system.adapter.repository;

import cn.culpro.domain.system.model.aggregate.MenuAggregate;
import cn.culpro.domain.system.model.valobj.MenuTypeVO;

import java.util.List;
import java.util.Optional;

/**
 * 菜单仓储接口
 * <p>
 * 定义菜单聚合根的持久化和查询操作
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
public interface IMenuRepository {
    
    /**
     * 保存菜单
     *
     * @param menuAggregate 菜单聚合根
     * @return 菜单ID
     */
    Long save(MenuAggregate menuAggregate);
    
    /**
     * 根据ID查询菜单
     *
     * @param menuId 菜单ID
     * @return 菜单聚合根
     */
    Optional<MenuAggregate> findById(Long menuId);
    
    /**
     * 根据权限标识查询菜单
     *
     * @param permission 权限标识
     * @return 菜单聚合根
     */
    Optional<MenuAggregate> findByPermission(String permission);
    
    /**
     * 查询所有菜单
     *
     * @return 菜单聚合根列表
     */
    List<MenuAggregate> findAll();
    
    /**
     * 根据父级ID查询菜单
     *
     * @param parentId 父级ID
     * @return 菜单聚合根列表
     */
    List<MenuAggregate> findByParentId(Long parentId);
    
    /**
     * 根据菜单类型查询菜单
     *
     * @param menuType 菜单类型
     * @return 菜单聚合根列表
     */
    List<MenuAggregate> findByMenuType(MenuTypeVO menuType);
    
    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单聚合根列表
     */
    List<MenuAggregate> findByRoleId(Long roleId);
    
    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单聚合根列表
     */
    List<MenuAggregate> findByUserId(Long userId);
} 