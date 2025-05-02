package cn.culpro.application.user.service;

import cn.culpro.application.user.command.CreateUserCommand;
import cn.culpro.domain.shared.adapter.port.IEventPublisher;
import cn.culpro.domain.system.adapter.event.UserCreatedMessageEvent;
import cn.culpro.domain.system.adapter.port.IPasswordEncoder;
import cn.culpro.domain.system.adapter.repository.IUserRepository;
import cn.culpro.domain.system.model.aggregate.UserAggregate;
import cn.culpro.domain.system.service.notification.INotificationService;
import cn.culpro.domain.system.service.user.IUserService;
import cn.culpro.types.enums.NotificationType;
import cn.culpro.types.enums.ResponseCode;
import cn.culpro.types.event.BaseEvent;
import cn.culpro.types.exception.AppException;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 用户应用服务
 * <p>
 * 处理用户相关的应用场景，协调领域服务完成用户管理功能 应用层负责事务控制、协调多个领域服务，但不包含具体业务逻辑
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Service
@RequiredArgsConstructor
public class UserApplicationService {
    
    private final IUserRepository userRepository;
    
    private final IUserService userService;
    
    private final IPasswordEncoder passwordEncoder;
    
    private final INotificationService notificationService;
    
    private final UserCreatedMessageEvent userCreatedMessageEvent;
    
    private final IEventPublisher eventPublisher;
    
    /**
     * 获取所有用户
     * <p>
     * 查询系统中的所有用户
     *
     * @return 用户列表
     */
    @Transactional(readOnly = true)
    public List<UserAggregate> listAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * 根据ID获取用户
     * <p>
     * 查询指定ID的用户信息
     *
     * @param userId 用户ID
     * @return 用户信息，不存在则返回空
     */
    @Transactional(readOnly = true)
    public Optional<UserAggregate> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    
    /**
     * 创建用户
     * <p>
     * 创建新用户并保存，自动发送初始密码给用户
     *
     * @param command 创建用户命令
     * @return 创建后的用户ID，创建失败返回null
     * @throws AppException 如果用户名或手机号已存在
     */
    @Transactional
    public Long createUser(CreateUserCommand command) {
        // 检查用户名唯一性
        if (!userService.checkUsernameUnique(command.getUsername())) {
            throw new AppException(ResponseCode.USER_EXISTS.getCode(), ResponseCode.USER_EXISTS.getInfo());
        }
        
        // 检查手机号唯一性
        if (command.getPhoneNumber() != null && !userService.checkPhoneNumberUnique(command.getPhoneNumber())) {
            throw new AppException(ResponseCode.PHONE_NUMBER_EXISTS.getCode(), ResponseCode.PHONE_NUMBER_EXISTS.getInfo());
        }
        
        // 生成随机初始密码
        String initialPassword = RandomUtil.randomString(10);
        
        // 密码加密
        String encryptedPassword = passwordEncoder.encode(initialPassword);
        
        // 创建用户聚合根
        UserAggregate userAggregate = UserAggregate.create(command.getUsername(), encryptedPassword, command.getRealName(), null,
                command.getEmail(), command.getPhoneNumber());
        
        // 领域服务创建用户
        Long userId = userService.createUser(userAggregate);
        
        // 构建消息对象
        UserCreatedMessageEvent.UserCreatedMessage userCreatedMessage = UserCreatedMessageEvent.UserCreatedMessage.builder()
                .userId(userId)
                .username(command.getUsername())
                .password(initialPassword)
                .email(command.getEmail())
                .phoneNumber(command.getPhoneNumber())
                .build();
        
        BaseEvent.EventMessage<UserCreatedMessageEvent.UserCreatedMessage> message = userCreatedMessageEvent.buildEventMessage(
                userCreatedMessage);
        
        // 发布用户已创建事件
        eventPublisher.publish(userCreatedMessageEvent.topic(), message);
        
        return userId;
    }
    
    /**
     * 发送初始密码通知
     *
     * @param user            用户聚合根
     * @param initialPassword 初始密码（明文）
     */
    private void sendInitialPasswordNotification(UserAggregate user, String initialPassword) {
        String messageContent = String.format("尊敬的用户 %s，您的账号已创建成功。您的初始密码为：%s，请登录后及时修改密码。",
                user.getUsername(), initialPassword);
        
        // 发送短信通知
        if (StrUtil.isNotBlank(user.getPhoneNumber())) {
            notificationService.sendNotification(NotificationType.SMS, user.getPhoneNumber(), null, messageContent);
        }
        
        // 发送邮件通知
        if (StrUtil.isNotBlank(user.getEmail())) {
            notificationService.sendNotification(NotificationType.EMAIL, user.getEmail(), "账号创建成功 - 初始密码", messageContent);
        }
    }
    
    /**
     * 更新用户基本信息
     * <p>
     * 更新用户的非敏感信息
     *
     * @param userId      用户ID
     * @param realName    真实姓名
     * @param gender      性别
     * @param email       邮箱
     * @param phoneNumber 手机号
     * @return 更新成功返回true，用户不存在返回false
     * @throws AppException 如果手机号已被其他用户使用
     */
    @Transactional
    public boolean updateUserBasicInfo(Long userId, String realName, Integer gender, String email, String phoneNumber) {
        // 检查用户是否存在
        Optional<UserAggregate> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return false;
        }
        
        UserAggregate user = userOpt.get();
        
        // 检查手机号唯一性
        if (phoneNumber != null && !phoneNumber.equals(user.getPhoneNumber()) && !userService.checkPhoneNumberUnique(phoneNumber)) {
            throw new AppException("手机号已被使用");
        }
        
        // 创建用户更新对象
        UserAggregate updatedUser = UserAggregate.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .realName(realName)
                .gender(gender != null ? gender : user.getGender())
                .avatar(user.getAvatar())
                .email(email != null ? email : user.getEmail())
                .phoneNumber(phoneNumber != null ? phoneNumber : user.getPhoneNumber())
                .status(user.getStatus())
                .deleteStatus(user.getDeleteStatus())
                .remark(user.getRemark())
                .createTime(user.getCreateTime())
                .userRoles(user.getUserRoles())
                .build();
        
        // 委托给领域服务更新用户
        Long updatedUserId = userService.updateUser(updatedUser);
        return updatedUserId != null;
    }
    
    /**
     * 修改用户密码
     * <p>
     * 验证旧密码并设置新密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 更新成功返回true，用户不存在或旧密码错误返回false
     */
    @Transactional
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        // 验证用户是否存在
        Optional<UserAggregate> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return false;
        }
        
        UserAggregate user = userOpt.get();
        
        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }
        
        // 加密新密码
        String encryptedPassword = passwordEncoder.encode(newPassword);
        
        // 委托给领域服务更改密码
        return userService.changePassword(userId, encryptedPassword);
    }
    
    /**
     * 启用用户
     * <p>
     * 将用户状态设置为启用
     *
     * @param userId 用户ID
     * @return 操作成功返回true，用户不存在返回false
     */
    @Transactional
    public boolean enableUser(Long userId) {
        // 委托给领域服务启用用户
        return userService.enableUser(userId);
    }
    
    /**
     * 禁用用户
     * <p>
     * 将用户状态设置为禁用
     *
     * @param userId 用户ID
     * @return 操作成功返回true，用户不存在返回false
     */
    @Transactional
    public boolean disableUser(Long userId) {
        // 委托给领域服务禁用用户
        return userService.disableUser(userId);
    }
    
    /**
     * 删除用户
     * <p>
     * 将用户标记为删除状态
     *
     * @param userId 用户ID
     * @return 操作成功返回true，用户不存在返回false
     */
    @Transactional
    public boolean deleteUser(Long userId) {
        // 委托给领域服务删除用户
        return userService.deleteUser(userId);
    }
} 