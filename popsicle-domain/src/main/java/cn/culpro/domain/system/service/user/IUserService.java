package cn.culpro.domain.system.service.user;

import cn.culpro.domain.system.model.aggregate.UserAggregate;

/**
 * 用户领域服务接口
 * <p>
 * 定义用户相关的领域操作
 *
 * @author HogskinKitty
 * @date 2024/10/12
 */
public interface IUserService {
    
    /**
     * 创建用户
     * <p>
     * 创建新用户并保存
     *
     * @param userAggregate 用户聚合
     * @return 创建后的用户ID
     */
    Long createUser(UserAggregate userAggregate);
    
    /**
     * 校验用户名是否唯一
     *
     * @param username 用户名
     * @return 用户名不存在返回true，已存在返回false
     */
    boolean checkUsernameUnique(String username);
    
    /**
     * 校验手机号是否唯一
     *
     * @param phoneNumber 手机号
     * @return 手机号不存在返回true，已存在返回false
     */
    boolean checkPhoneNumberUnique(String phoneNumber);
    
    /**
     * 更新用户
     * <p>
     * 更新现有用户信息
     *
     * @param userAggregate 待更新的用户信息
     * @return 更新后的用户ID
     */
    Long updateUser(UserAggregate userAggregate);
    
    /**
     * 更改用户密码
     * <p>
     * 更新用户的密码
     *
     * @param userId      用户ID
     * @param newPassword 加密后的新密码
     * @return 更新成功返回true，用户不存在返回false
     */
    boolean changePassword(Long userId, String newPassword);
    
    /**
     * 启用用户
     * <p>
     * 将用户状态设置为启用
     *
     * @param userId 用户ID
     * @return 操作成功返回true，用户不存在返回false
     */
    boolean enableUser(Long userId);
    
    /**
     * 禁用用户
     * <p>
     * 将用户状态设置为禁用
     *
     * @param userId 用户ID
     * @return 操作成功返回true，用户不存在返回false
     */
    boolean disableUser(Long userId);
    
    /**
     * 删除用户
     * <p>
     * 将用户标记为删除状态
     *
     * @param userId 用户ID
     * @return 操作成功返回true，用户不存在返回false
     */
    boolean deleteUser(Long userId);
}
