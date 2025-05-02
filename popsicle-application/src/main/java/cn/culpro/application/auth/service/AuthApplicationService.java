package cn.culpro.application.auth.service;

import cn.culpro.application.auth.command.LoginCommand;
import cn.culpro.domain.system.model.aggregate.MenuAggregate;
import cn.culpro.domain.system.model.aggregate.UserAggregate;
import cn.culpro.domain.system.service.auth.IAuthService;
import cn.culpro.domain.system.service.permission.IPermissionCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 认证应用服务
 * <p>
 * 处理用户登录、注销和权限认证相关的应用场景 协调多个领域服务完成用户认证和授权功能
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Service
@RequiredArgsConstructor
public class AuthApplicationService {
    
    private final IAuthService userAuthService;
    
    private final IPermissionCheckService permissionCheckService;
    
    /**
     * 用户登录
     * <p>
     * 验证用户凭证并生成访问令牌
     *
     * @param command 登录命令，包含用户名和密码等信息
     * @return 生成的JWT令牌
     * @throws RuntimeException 用户名或密码错误时抛出异常
     */
    public String login(LoginCommand command) {
        // 验证用户凭证
        UserAggregate user = userAuthService.authenticate(command.getUsername(), command.getPassword());
        
        // 生成令牌
        return userAuthService.generateToken(user);
    }
    
    /**
     * 刷新令牌
     * <p>
     * 验证现有令牌并生成新的访问令牌
     *
     * @param token 现有JWT令牌
     * @return 新生成的JWT令牌，令牌无效时返回null
     */
    public String refreshToken(String token) {
        return userAuthService.refreshToken(token);
    }
    
    /**
     * 获取用户权限
     * <p>
     * 查询指定用户拥有的所有权限标识
     *
     * @param userId 用户ID
     * @return 用户拥有的权限标识集合
     */
    public Set<String> getUserPermissions(Long userId) {
        return permissionCheckService.getUserPermissions(userId);
    }
    
    /**
     * 获取用户菜单
     * <p>
     * 查询指定用户可访问的所有菜单
     *
     * @param userId 用户ID
     * @return 用户可访问的菜单列表
     */
    public List<MenuAggregate> getUserMenus(Long userId) {
        return permissionCheckService.getUserMenus(userId);
    }
    
    /**
     * 检查权限
     * <p>
     * 验证用户是否拥有指定权限
     *
     * @param userId     用户ID
     * @param permission 权限标识
     * @return 是否有权限，有权限返回true，无权限返回false
     */
    public boolean hasPermission(Long userId, String permission) {
        return permissionCheckService.hasPermission(userId, permission);
    }
} 