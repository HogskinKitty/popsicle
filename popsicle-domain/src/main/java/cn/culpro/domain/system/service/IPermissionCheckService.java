package cn.culpro.domain.system.service;

import cn.culpro.domain.system.model.aggregate.MenuAggregate;

import java.util.List;
import java.util.Set;

/**
 * 权限校验领域服务接口
 * <p>
 * 定义权限和角色检查相关功能
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
public interface IPermissionCheckService {
    
    /**
     * 获取用户的权限标识集合
     *
     * @param userId 用户ID
     * @return 权限标识集合
     */
    Set<String> getUserPermissions(Long userId);
    
    /**
     * 获取用户的菜单列表
     *
     * @param userId 用户ID
     * @return 菜单聚合根列表
     */
    List<MenuAggregate> getUserMenus(Long userId);
    
    /**
     * 检查用户是否拥有指定权限
     *
     * @param userId     用户ID
     * @param permission 权限标识
     * @return 有权限返回true，无权限返回false
     */
    boolean hasPermission(Long userId, String permission);
    
    /**
     * 检查用户是否拥有指定角色
     *
     * @param userId   用户ID
     * @param roleCode 角色编码
     * @return 有角色返回true，无角色返回false
     */
    boolean hasRole(Long userId, String roleCode);
    
    /**
     * 检查用户是否拥有指定角色列表中的任意一个角色
     *
     * @param userId    用户ID
     * @param roleCodes 角色编码列表
     * @return 有任一角色返回true，无角色返回false
     */
    boolean hasAnyRole(Long userId, List<String> roleCodes);
} 