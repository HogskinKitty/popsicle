package cn.culpro.application.user.service;

import cn.culpro.domain.system.adapter.repository.IUserRepository;
import cn.culpro.domain.system.model.aggregate.UserAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 用户应用服务
 * <p>
 * 处理用户相关的应用场景，协调领域服务完成用户管理功能
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Service
@RequiredArgsConstructor
public class UserApplicationService {
    
    private final IUserRepository userRepository;
    
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
     * 创建新用户并保存
     *
     * @param userAggregate 用户聚合
     * @return 创建后的用户ID
     */
    @Transactional
    public Long createUser(UserAggregate userAggregate) {
        // 可以在这里添加业务规则验证，例如检查用户名是否已存在
        return userRepository.save(userAggregate);
    }
    
    /**
     * 更新用户
     * <p>
     * 更新现有用户信息
     *
     * @param userAggregate 待更新的用户信息
     * @return 更新后的用户ID
     */
    @Transactional
    public Long updateUser(UserAggregate userAggregate) {
        // 可以添加业务规则验证
        return userRepository.save(userAggregate);
    }
    
    /**
     * 删除用户
     * <p>
     * 删除指定ID的用户
     * <p>
     * 注：当前仓储接口未提供删除方法，实际实现需要根据仓储接口进行调整
     *
     * @param userId 用户ID
     */
    @Transactional
    public void deleteUser(Long userId) {
        // 由于IUserRepository没有提供删除方法，可以通过以下方式实现：
        // 1. 获取用户
        Optional<UserAggregate> userOpt = userRepository.findById(userId);
        // 2. 如果用户存在，将其状态设为删除状态，然后保存
        userOpt.ifPresent(user -> {
            // 假设UserAggregate有setDeleted方法
            // user.setDeleted(true);
            userRepository.save(user);
        });
    }
} 