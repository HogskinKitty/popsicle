package cn.culpro.trigger.http;

import cn.culpro.application.user.command.CreateUserCommand;
import cn.culpro.application.user.service.UserApplicationService;
import cn.culpro.domain.system.model.aggregate.UserAggregate;
import cn.culpro.trigger.http.dto.user.PasswordChangeRequest;
import cn.culpro.trigger.http.dto.user.UserRequest;
import cn.culpro.trigger.http.dto.user.UserResponse;
import cn.culpro.trigger.http.dto.user.converter.UserRequestResponseConverter;
import cn.culpro.types.exception.AppException;
import cn.culpro.types.model.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户控制器
 * <p>
 * 用户管理相关接口
 *
 * @author HogskinKitty
 * @date 2024/10/30
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserApplicationService userApplicationService;
    
    private final UserRequestResponseConverter converter;
    
    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    @GetMapping
    @PreAuthorize("hasAuthority('sys:user:view')")
    public ResponseDTO<List<UserResponse>> listUsers() {
        List<UserAggregate> users = userApplicationService.listAllUsers();
        List<UserResponse> responses = users.stream().map(converter::toUserResponse).collect(Collectors.toList());
        return ResponseDTO.success(responses);
    }
    
    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return 用户详情
     */
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:view')")
    public ResponseDTO<UserResponse> getUserById(@PathVariable Long userId) {
        Optional<UserAggregate> userOpt = userApplicationService.getUserById(userId);
        return userOpt.map(user -> ResponseDTO.success(converter.toUserResponse(user)))
                .orElseGet(() -> ResponseDTO.fail("用户不存在"));
    }
    
    /**
     * 创建用户
     *
     * @param request 用户信息
     * @return 创建结果
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:add')")
    public ResponseDTO<Long> createUser(@Valid @RequestBody UserRequest request) {
        CreateUserCommand command = converter.toCreateUserCommand(request);
        Long userId = userApplicationService.createUser(command);
        return ResponseDTO.success(userId);
        
    }
    
    /**
     * 更新用户
     *
     * @param userId  用户ID
     * @param request 用户信息
     * @return 更新结果
     */
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public ResponseDTO<Void> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequest request) {
        try {
            boolean success = userApplicationService.updateUserBasicInfo(userId, request.getRealName(), null, request.getEmail(),
                    request.getPhoneNumber());
            
            return success ? ResponseDTO.success() : ResponseDTO.fail("用户不存在");
        } catch (AppException e) {
            return ResponseDTO.fail(e.getMessage());
        }
    }
    
    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public ResponseDTO<Void> deleteUser(@PathVariable Long userId) {
        boolean success = userApplicationService.deleteUser(userId);
        return success ? ResponseDTO.success() : ResponseDTO.fail("用户不存在");
    }
    
    /**
     * 启用用户
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    @PutMapping("/{userId}/enable")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public ResponseDTO<Void> enableUser(@PathVariable Long userId) {
        boolean success = userApplicationService.enableUser(userId);
        return success ? ResponseDTO.success() : ResponseDTO.fail("用户不存在");
    }
    
    /**
     * 禁用用户
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    @PutMapping("/{userId}/disable")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public ResponseDTO<Void> disableUser(@PathVariable Long userId) {
        boolean success = userApplicationService.disableUser(userId);
        return success ? ResponseDTO.success() : ResponseDTO.fail("用户不存在");
    }
    
    /**
     * 修改密码
     *
     * @param userId  用户ID
     * @param request 包含旧密码和新密码的请求
     * @return 操作结果
     */
    @PutMapping("/{userId}/password")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public ResponseDTO<Void> changePassword(@PathVariable Long userId, @Valid @RequestBody PasswordChangeRequest request) {
        boolean success = userApplicationService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
        return success ? ResponseDTO.success() : ResponseDTO.fail("用户不存在或密码错误");
    }
} 