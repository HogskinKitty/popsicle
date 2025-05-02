package cn.culpro.infrastructure.dao;

import cn.culpro.infrastructure.dao.po.UserPO;
import cn.culpro.infrastructure.dao.po.UserRolePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问接口
 * <p>
 * 定义用户数据的访问操作，包括用户基本信息和用户角色关联的CRUD操作
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Mapper
public interface IUserDao {
    
    /**
     * 新增用户
     *
     * @param userPO 用户PO
     * @return 影响行数
     */
    int insert(UserPO userPO);
    
    /**
     * 更新用户
     *
     * @param userPO 用户PO
     * @return 影响行数
     */
    int update(UserPO userPO);
    
    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户PO
     */
    Optional<UserPO> selectById(Long userId);
    
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户PO
     */
    Optional<UserPO> selectByUsername(String username);
    
    /**
     * 统计指定用户名的用户数量
     *
     * @param username 用户名
     * @return 用户数量
     */
    int countByUsername(String username);
    
    /**
     * 根据手机号查询用户
     *
     * @param phoneNumber 手机号
     * @return 用户PO
     */
    Optional<UserPO> selectByPhoneNumber(String phoneNumber);
    
    /**
     * 统计指定手机号的用户数量
     *
     * @param phoneNumber 手机号
     * @return 用户数量
     */
    int countByPhoneNumber(String phoneNumber);
    
    /**
     * 查询所有用户
     *
     * @return 用户PO列表
     */
    List<UserPO> selectAll();
    
    /**
     * 分页查询用户
     *
     * @param offset 偏移量
     * @param limit  每页大小
     * @return 用户PO列表
     */
    List<UserPO> selectByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据角色ID查询用户
     *
     * @param roleId 角色ID
     * @return 用户PO列表
     */
    List<UserPO> selectByRoleId(Long roleId);
    
    /**
     * 新增用户角色关联
     *
     * @param userRolePO 用户角色关联PO
     * @return 影响行数
     */
    int insertUserRole(UserRolePO userRolePO);
    
    /**
     * 删除用户角色关联
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    /**
     * 删除用户所有角色关联
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteUserRoleByUserId(Long userId);
    
    /**
     * 查询用户角色关联
     *
     * @param userId 用户ID
     * @return 用户角色关联PO列表
     */
    List<UserRolePO> selectUserRoleByUserId(Long userId);
} 