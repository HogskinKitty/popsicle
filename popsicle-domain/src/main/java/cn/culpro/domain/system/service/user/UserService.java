package cn.culpro.domain.system.service.user;

import cn.culpro.domain.system.adapter.repository.IUserRepository;
import cn.culpro.domain.system.model.aggregate.UserAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 用户领域服务实现
 * <p>
 * 处理用户相关的业务逻辑，实现用户领域服务接口
 *
 * @author HogskinKitty
 * @date 2025/4/21
 */
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    
    private final IUserRepository userRepository;
    
    /**
     * 创建用户
     * <p>
     *
     * @param userAggregate 用户聚合
     * @return 创建成功返回用户ID
     */
    @Override
    public Long createUser(UserAggregate userAggregate) {
        return userRepository.save(userAggregate);
    }
    
    /**
     * 检查用户名唯一性
     * <p>
     * 检查系统中是否已存在相同用户名的用户
     *
     * @param username 用户名
     * @return 用户名不存在返回true，已存在返回false
     */
    @Override
    public boolean checkUsernameUnique(String username) {
        return userRepository.countByUsername(username) == 0;
    }
    
    /**
     * 检查手机号唯一性
     * <p>
     * 检查系统中是否已存在相同手机号的用户
     *
     * @param phoneNumber 手机号
     * @return 手机号不存在返回true，已存在返回false
     */
    @Override
    public boolean checkPhoneNumberUnique(String phoneNumber) {
        return userRepository.countByPhoneNumber(phoneNumber) == 0;
    }
    
    /**
     * 更新用户信息
     * <p>
     * 更新用户的基本信息，不包括密码和用户名
     *
     * @param userAggregate 用户聚合
     * @return 更新成功返回用户ID，更新失败返回null
     */
    @Override
    public Long updateUser(UserAggregate userAggregate) {
        // 检查用户是否存在
        Optional<UserAggregate> existingUserOpt = userRepository.findById(userAggregate.getId());
        if (!existingUserOpt.isPresent()) {
            return null;
        }
        
        UserAggregate existingUser = existingUserOpt.get();
        
        // 创建一个新的UserAggregate，保留原有不可修改的字段
        UserAggregate updatedUser = UserAggregate.builder()
                .userId(existingUser.getUserId())
                .username(existingUser.getUsername())
                .password(existingUser.getPassword())
                .realName(userAggregate.getRealName())
                .gender(userAggregate.getGender())
                .avatar(userAggregate.getAvatar())
                .email(userAggregate.getEmail())
                .phoneNumber(userAggregate.getPhoneNumber())
                .status(existingUser.getStatus())
                .deleteStatus(existingUser.getDeleteStatus())
                .remark(userAggregate.getRemark())
                .createTime(existingUser.getCreateTime())
                .updateTime(LocalDateTime.now())
                .userRoles(existingUser.getUserRoles())
                .build();
        
        // 更新用户信息
        return userRepository.save(updatedUser);
    }
    
    /**
     * 更改用户密码
     * <p>
     * 更新用户的密码
     *
     * @param userId      用户ID
     * @param newPassword 加密后的新密码
     * @return 更新成功返回true，用户不存在返回false
     */
    @Override
    public boolean changePassword(Long userId, String newPassword) {
        Optional<UserAggregate> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return false;
        }
        
        UserAggregate user = userOpt.get();
        user.changePassword(newPassword);
        userRepository.save(user);
        return true;
    }
    
    /**
     * 启用用户
     * <p>
     * 将用户状态设置为启用
     *
     * @param userId 用户ID
     * @return 操作成功返回true，用户不存在返回false
     */
    @Override
    public boolean enableUser(Long userId) {
        Optional<UserAggregate> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return false;
        }
        
        UserAggregate user = userOpt.get();
        user.enable();
        userRepository.save(user);
        return true;
    }
    
    /**
     * 禁用用户
     * <p>
     * 将用户状态设置为禁用
     *
     * @param userId 用户ID
     * @return 操作成功返回true，用户不存在返回false
     */
    @Override
    public boolean disableUser(Long userId) {
        Optional<UserAggregate> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return false;
        }
        
        UserAggregate user = userOpt.get();
        user.disable();
        userRepository.save(user);
        return true;
    }
    
    /**
     * 删除用户
     * <p>
     * 将用户标记为删除状态
     *
     * @param userId 用户ID
     * @return 操作成功返回true，用户不存在返回false
     */
    @Override
    public boolean deleteUser(Long userId) {
        Optional<UserAggregate> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return false;
        }
        
        UserAggregate user = userOpt.get();
        user.markAsDeleted();
        userRepository.save(user);
        return true;
    }
}
