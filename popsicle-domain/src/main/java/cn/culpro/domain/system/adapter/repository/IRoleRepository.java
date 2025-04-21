package cn.culpro.domain.system.adapter.repository;

import cn.culpro.domain.system.model.aggregate.RoleAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 角色仓储接口
 * <p>
 * 定义角色聚合根的持久化和查询操作
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
public interface IRoleRepository {
    
    /**
     * 保存角色
     *
     * @param roleAggregate 角色聚合根
     * @return 角色ID
     */
    Long save(RoleAggregate roleAggregate);
    
    /**
     * 根据ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色聚合根
     */
    Optional<RoleAggregate> findById(Long roleId);
    
    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色聚合根
     */
    Optional<RoleAggregate> findByRoleCode(String roleCode);
    
    /**
     * 查询所有角色
     *
     * @return 角色聚合根列表
     */
    List<RoleAggregate> findAll();
    
    /**
     * 分页查询角色
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 角色聚合根列表
     */
    List<RoleAggregate> findByPage(int pageNum, int pageSize);
    
    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色聚合根列表
     */
    List<RoleAggregate> findByUserId(Long userId);
} 