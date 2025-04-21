package cn.culpro.domain.system.service;

import cn.culpro.domain.system.adapter.repository.IMenuRepository;
import cn.culpro.domain.system.adapter.repository.IRoleRepository;
import cn.culpro.domain.system.model.aggregate.MenuAggregate;
import cn.culpro.domain.system.model.aggregate.RoleAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限校验领域服务实现
 * <p>
 * 实现权限和角色检查相关功能，提供用户权限和菜单的查询能力
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Service
@RequiredArgsConstructor
public class PermissionCheckService implements IPermissionCheckService {
    
    private final IMenuRepository menuRepository;
    
    private final IRoleRepository roleRepository;
    
    @Override
    public Set<String> getUserPermissions(Long userId) {
        // 获取用户菜单权限
        List<MenuAggregate> menus = menuRepository.findByUserId(userId);
        
        // 提取权限标识
        return menus.stream()
                .filter(menu -> menu.getPermission() != null && !menu.getPermission().isEmpty())
                .map(MenuAggregate::getPermission)
                .collect(Collectors.toSet());
    }
    
    @Override
    public List<MenuAggregate> getUserMenus(Long userId) {
        return menuRepository.findByUserId(userId);
    }
    
    @Override
    public boolean hasPermission(Long userId, String permission) {
        Set<String> permissions = getUserPermissions(userId);
        return permissions.contains(permission);
    }
    
    @Override
    public boolean hasRole(Long userId, String roleCode) {
        List<RoleAggregate> roles = roleRepository.findByUserId(userId);
        return roles.stream().anyMatch(role -> role.getRoleCode().equals(roleCode));
    }
    
    @Override
    public boolean hasAnyRole(Long userId, List<String> roleCodes) {
        if (roleCodes == null || roleCodes.isEmpty()) {
            return false;
        }
        
        List<RoleAggregate> roles = roleRepository.findByUserId(userId);
        Set<String> userRoleCodes = roles.stream().map(RoleAggregate::getRoleCode).collect(Collectors.toSet());
        
        for (String roleCode : roleCodes) {
            if (userRoleCodes.contains(roleCode)) {
                return true;
            }
        }
        
        return false;
    }
} 