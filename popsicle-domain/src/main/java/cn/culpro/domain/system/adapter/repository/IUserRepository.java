package cn.culpro.domain.system.adapter.repository;

import cn.culpro.domain.system.model.aggregate.UserAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 用户仓储接口
 * <p>
 * 定义用户聚合根的持久化和查询操作
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
public interface IUserRepository {
    
    /**
     * 保存用户
     *
     * @param userAggregate 用户聚合根
     * @return 用户ID
     */
    Long save(UserAggregate userAggregate);
    
    /**
     * 根据ID查询用户
     *
     * @param userId 用户ID
     * @return 用户聚合根
     */
    Optional<UserAggregate> findById(Long userId);
    
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户聚合根
     */
    Optional<UserAggregate> findByUsername(String username);
    
    /**
     * 查询所有用户
     *
     * @return 用户聚合根列表
     */
    List<UserAggregate> findAll();
    
    /**
     * 分页查询用户
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 用户聚合根列表
     */
    List<UserAggregate> findByPage(int pageNum, int pageSize);
    
    /**
     * 根据角色ID查询用户
     *
     * @param roleId 角色ID
     * @return 用户聚合根列表
     */
    List<UserAggregate> findByRoleId(Long roleId);
} 