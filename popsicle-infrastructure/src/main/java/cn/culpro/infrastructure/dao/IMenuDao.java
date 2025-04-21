package cn.culpro.infrastructure.dao;

import cn.culpro.infrastructure.dao.po.MenuPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * 菜单数据访问接口
 * <p>
 * 定义菜单数据的访问操作，包括菜单基本信息的CRUD操作和按各种条件查询菜单
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Mapper
public interface IMenuDao {
    
    /**
     * 新增菜单
     *
     * @param menuPO 菜单PO
     * @return 影响行数
     */
    int insert(MenuPO menuPO);
    
    /**
     * 更新菜单
     *
     * @param menuPO 菜单PO
     * @return 影响行数
     */
    int update(MenuPO menuPO);
    
    /**
     * 根据菜单ID查询菜单
     *
     * @param menuId 菜单ID
     * @return 菜单PO
     */
    Optional<MenuPO> selectById(Long menuId);
    
    /**
     * 根据权限标识查询菜单
     *
     * @param permission 权限标识
     * @return 菜单PO
     */
    Optional<MenuPO> selectByPermission(String permission);
    
    /**
     * 查询所有菜单
     *
     * @return 菜单PO列表
     */
    List<MenuPO> selectAll();
    
    /**
     * 根据父级ID查询菜单
     *
     * @param parentId 父级ID
     * @return 菜单PO列表
     */
    List<MenuPO> selectByParentId(Long parentId);
    
    /**
     * 根据菜单类型查询菜单
     *
     * @param menuType 菜单类型
     * @return 菜单PO列表
     */
    List<MenuPO> selectByMenuType(Integer menuType);
    
    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单PO列表
     */
    List<MenuPO> selectByRoleId(Long roleId);
    
    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单PO列表
     */
    List<MenuPO> selectByUserId(Long userId);
} 