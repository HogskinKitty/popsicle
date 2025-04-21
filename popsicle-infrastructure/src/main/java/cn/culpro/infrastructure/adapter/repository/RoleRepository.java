package cn.culpro.infrastructure.adapter.repository;

import cn.culpro.domain.system.adapter.repository.IRoleRepository;
import cn.culpro.domain.system.model.aggregate.RoleAggregate;
import cn.culpro.infrastructure.dao.IRoleDao;
import cn.culpro.infrastructure.dao.po.RoleMenuPO;
import cn.culpro.infrastructure.dao.po.RolePO;
import cn.culpro.infrastructure.dao.po.converter.RoleConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色仓储实现
 * <p>
 * 实现领域层定义的角色仓储接口，负责角色聚合根的持久化和查询操作
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Repository
@RequiredArgsConstructor
public class RoleRepository implements IRoleRepository {
    
    private final IRoleDao roleDao;
    
    @Override
    public Long save(RoleAggregate roleAggregate) {
        RolePO rolePO = RoleConverter.INSTANCE.toRolePO(roleAggregate);
        
        if (rolePO.getRoleId() == null) {
            // 新增角色
            roleDao.insert(rolePO);
        } else {
            // 更新角色
            roleDao.update(rolePO);
            
            // 删除原有角色菜单关联
            roleDao.deleteRoleMenuByRoleId(rolePO.getRoleId());
        }
        
        // 保存角色菜单关联
        roleAggregate.getRoleMenus().forEach(roleMenu -> {
            RoleMenuPO roleMenuPO = RoleMenuPO.builder().roleId(rolePO.getRoleId()).menuId(roleMenu.getMenuId()).build();
            roleDao.insertRoleMenu(roleMenuPO);
        });
        
        return rolePO.getRoleId();
    }
    
    @Override
    public Optional<RoleAggregate> findById(Long roleId) {
        return roleDao.selectById(roleId).map(rolePO -> {
            RoleAggregate roleAggregate = RoleConverter.INSTANCE.toRoleAggregate(rolePO);
            
            // 查询角色菜单关联
            List<RoleMenuPO> roleMenuPOs = roleDao.selectRoleMenuByRoleId(roleId);
            List<Long> menuIds = roleMenuPOs.stream().map(RoleMenuPO::getMenuId).collect(Collectors.toList());
            roleAggregate.assignMenus(menuIds);
            
            return roleAggregate;
        });
    }
    
    @Override
    public Optional<RoleAggregate> findByRoleCode(String roleCode) {
        return roleDao.selectByRoleCode(roleCode).map(rolePO -> {
            RoleAggregate roleAggregate = RoleConverter.INSTANCE.toRoleAggregate(rolePO);
            
            // 查询角色菜单关联
            List<RoleMenuPO> roleMenuPOs = roleDao.selectRoleMenuByRoleId(rolePO.getRoleId());
            List<Long> menuIds = roleMenuPOs.stream().map(RoleMenuPO::getMenuId).collect(Collectors.toList());
            roleAggregate.assignMenus(menuIds);
            
            return roleAggregate;
        });
    }
    
    @Override
    public List<RoleAggregate> findAll() {
        List<RolePO> rolePOs = roleDao.selectAll();
        List<RoleAggregate> roleAggregates = RoleConverter.INSTANCE.toRoleAggregates(rolePOs);
        
        // 查询并设置角色菜单关联
        roleAggregates.forEach(roleAggregate -> {
            List<RoleMenuPO> roleMenuPOs = roleDao.selectRoleMenuByRoleId(roleAggregate.getRoleId());
            List<Long> menuIds = roleMenuPOs.stream().map(RoleMenuPO::getMenuId).collect(Collectors.toList());
            roleAggregate.assignMenus(menuIds);
        });
        
        return roleAggregates;
    }
    
    @Override
    public List<RoleAggregate> findByPage(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<RolePO> rolePOs = roleDao.selectByPage(offset, pageSize);
        List<RoleAggregate> roleAggregates = RoleConverter.INSTANCE.toRoleAggregates(rolePOs);
        
        // 查询并设置角色菜单关联
        roleAggregates.forEach(roleAggregate -> {
            List<RoleMenuPO> roleMenuPOs = roleDao.selectRoleMenuByRoleId(roleAggregate.getRoleId());
            List<Long> menuIds = roleMenuPOs.stream().map(RoleMenuPO::getMenuId).collect(Collectors.toList());
            roleAggregate.assignMenus(menuIds);
        });
        
        return roleAggregates;
    }
    
    @Override
    public List<RoleAggregate> findByUserId(Long userId) {
        List<RolePO> rolePOs = roleDao.selectByUserId(userId);
        List<RoleAggregate> roleAggregates = RoleConverter.INSTANCE.toRoleAggregates(rolePOs);
        
        // 查询并设置角色菜单关联
        roleAggregates.forEach(roleAggregate -> {
            List<RoleMenuPO> roleMenuPOs = roleDao.selectRoleMenuByRoleId(roleAggregate.getRoleId());
            List<Long> menuIds = roleMenuPOs.stream().map(RoleMenuPO::getMenuId).collect(Collectors.toList());
            roleAggregate.assignMenus(menuIds);
        });
        
        return roleAggregates;
    }
} 