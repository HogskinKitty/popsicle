package cn.culpro.trigger.http;

import cn.culpro.application.auth.command.LoginCommand;
import cn.culpro.application.auth.service.AuthApplicationService;
import cn.culpro.application.menu.dto.MenuTreeDTO;
import cn.culpro.application.menu.service.MenuApplicationService;
import cn.culpro.domain.system.service.IUserAuthService;
import cn.culpro.trigger.http.dto.auth.LoginRequest;
import cn.culpro.trigger.http.dto.auth.LoginResponse;
import cn.culpro.trigger.http.dto.auth.UserInfoResponse;
import cn.culpro.trigger.http.dto.menu.MenuTreeResponse;
import cn.culpro.trigger.http.dto.menu.converter.MenuRequestResponseConverter;
import cn.culpro.types.model.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 认证控制器
 * <p>
 * 处理用户登录、获取用户信息和权限等请求
 *
 * @author HogskinKitty
 * @date 2024/10/30
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthApplicationService authApplicationService;
    
    private final IUserAuthService userAuthService;
    
    private final MenuRequestResponseConverter menuConverter;
    
    private final MenuApplicationService menuApplicationService;
    
    /**
     * 用户登录
     * <p>
     * 验证用户名密码并返回JWT令牌
     *
     * @param request 登录请求
     * @return 登录响应（包含JWT令牌）
     */
    @PostMapping("/login")
    public ResponseDTO<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginCommand command = LoginCommand.builder().username(request.getUsername()).password(request.getPassword()).build();
        
        String token = authApplicationService.login(command);
        
        LoginResponse response = LoginResponse.builder().token(token).build();
        
        return ResponseDTO.success(response);
    }
    
    /**
     * 获取用户信息
     * <p>
     * 获取当前登录用户的信息和权限
     *
     * @param token 认证令牌
     * @return 用户信息响应
     */
    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public ResponseDTO<UserInfoResponse> getUserInfo(@RequestHeader("Authorization") String token) {
        // 验证令牌
        Long userId = userAuthService.validateToken(token);
        if (userId == null) {
            return ResponseDTO.fail("无效的令牌");
        }
        
        // 获取用户权限
        Set<String> permissions = authApplicationService.getUserPermissions(userId);
        
        // 构建响应
        UserInfoResponse response = new UserInfoResponse();
        response.setUserId(userId);
        response.setPermissions(permissions);
        return ResponseDTO.success(response);
    }
    
    /**
     * 获取用户权限
     * <p>
     * 获取指定用户拥有的所有权限标识
     *
     * @param userId 用户ID
     * @return 权限标识集合
     */
    @GetMapping("/permissions/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.username")
    public ResponseDTO<Set<String>> getUserPermissions(@PathVariable Long userId) {
        Set<String> permissions = authApplicationService.getUserPermissions(userId);
        return ResponseDTO.success(permissions);
    }
    
    /**
     * 获取用户菜单
     * <p>
     * 获取指定用户可访问的所有菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @GetMapping("/menus/{userId}")
    @PreAuthorize("hasAuthority('sys:menu:view') or #userId == authentication.principal.username")
    public ResponseDTO<List<MenuTreeResponse>> getUserMenus(@PathVariable Long userId) {
        List<MenuTreeDTO> menuTreeDTOs = menuApplicationService.getUserMenuTree(userId);
        List<MenuTreeResponse> responses = menuTreeDTOs.stream().map(menuConverter::toMenuTreeResponse).collect(Collectors.toList());
        return ResponseDTO.success(responses);
    }
    
    /**
     * 刷新令牌
     * <p>
     * 刷新用户的JWT令牌
     *
     * @param token 当前令牌
     * @return 新的JWT令牌
     */
    @PostMapping("/refresh")
    public ResponseDTO<String> refreshToken(@RequestBody String token) {
        String newToken = authApplicationService.refreshToken(token);
        return ResponseDTO.success(newToken);
    }
    
    /**
     * 用户注销
     *
     * @return 注销结果
     */
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseDTO<Void> logout() {
        return ResponseDTO.success();
    }
} 