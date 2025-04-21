package cn.culpro.infrastructure.dao;

import cn.culpro.infrastructure.dao.po.RoleMenuPO;
import cn.culpro.infrastructure.dao.po.RolePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * 角色数据访问接口
 * <p>
 * 定义角色数据的访问操作，包括角色基本信息和角色菜单关联的CRUD操作
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Mapper
public interface IRoleDao {
    
    /**
     * 新增角色
     *
     * @param rolePO 角色PO
     * @return 影响行数
     */
    int insert(RolePO rolePO);
    
    /**
     * 更新角色
     *
     * @param rolePO 角色PO
     * @return 影响行数
     */
    int update(RolePO rolePO);
    
    /**
     * 根据角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色PO
     */
    Optional<RolePO> selectById(Long roleId);
    
    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色PO
     */
    Optional<RolePO> selectByRoleCode(String roleCode);
    
    /**
     * 查询所有角色
     *
     * @return 角色PO列表
     */
    List<RolePO> selectAll();
    
    /**
     * 分页查询角色
     *
     * @param offset 偏移量
     * @param limit  每页大小
     * @return 角色PO列表
     */
    List<RolePO> selectByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色PO列表
     */
    List<RolePO> selectByUserId(Long userId);
    
    /**
     * 新增角色菜单关联
     *
     * @param roleMenuPO 角色菜单关联PO
     * @return 影响行数
     */
    int insertRoleMenu(RoleMenuPO roleMenuPO);
    
    /**
     * 删除角色菜单关联
     *
     * @param roleId 角色ID
     * @param menuId 菜单ID
     * @return 影响行数
     */
    int deleteRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);
    
    /**
     * 删除角色所有菜单关联
     *
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteRoleMenuByRoleId(Long roleId);
    
    /**
     * 查询角色菜单关联
     *
     * @param roleId 角色ID
     * @return 角色菜单关联PO列表
     */
    List<RoleMenuPO> selectRoleMenuByRoleId(Long roleId);
} 