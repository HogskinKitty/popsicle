package cn.culpro.infrastructure.adapter.repository;

import cn.culpro.domain.system.adapter.repository.IMenuRepository;
import cn.culpro.domain.system.model.aggregate.MenuAggregate;
import cn.culpro.domain.system.model.valobj.MenuTypeVO;
import cn.culpro.infrastructure.dao.IMenuDao;
import cn.culpro.infrastructure.dao.po.MenuPO;
import cn.culpro.infrastructure.dao.po.converter.MenuConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 菜单仓储实现
 * <p>
 * 实现领域层定义的菜单仓储接口，负责菜单聚合根的持久化和查询操作
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Repository
@RequiredArgsConstructor
public class MenuRepository implements IMenuRepository {
    
    private final IMenuDao menuDao;
    
    @Override
    public Long save(MenuAggregate menuAggregate) {
        MenuPO menuPO = MenuConverter.INSTANCE.toMenuPO(menuAggregate);
        
        if (menuPO.getMenuId() == null) {
            // 新增菜单
            menuDao.insert(menuPO);
        } else {
            // 更新菜单
            menuDao.update(menuPO);
        }
        
        return menuPO.getMenuId();
    }
    
    @Override
    public Optional<MenuAggregate> findById(Long menuId) {
        return menuDao.selectById(menuId).map(MenuConverter.INSTANCE::toMenuAggregate);
    }
    
    @Override
    public Optional<MenuAggregate> findByPermission(String permission) {
        return menuDao.selectByPermission(permission).map(MenuConverter.INSTANCE::toMenuAggregate);
    }
    
    @Override
    public List<MenuAggregate> findAll() {
        List<MenuPO> menuPOs = menuDao.selectAll();
        return MenuConverter.INSTANCE.toMenuAggregates(menuPOs);
    }
    
    @Override
    public List<MenuAggregate> findByParentId(Long parentId) {
        List<MenuPO> menuPOs = menuDao.selectByParentId(parentId);
        return MenuConverter.INSTANCE.toMenuAggregates(menuPOs);
    }
    
    @Override
    public List<MenuAggregate> findByMenuType(MenuTypeVO menuType) {
        Integer menuTypeCode = menuType.getCode();
        List<MenuPO> menuPOs = menuDao.selectByMenuType(menuTypeCode);
        return MenuConverter.INSTANCE.toMenuAggregates(menuPOs);
    }
    
    @Override
    public List<MenuAggregate> findByRoleId(Long roleId) {
        List<MenuPO> menuPOs = menuDao.selectByRoleId(roleId);
        return MenuConverter.INSTANCE.toMenuAggregates(menuPOs);
    }
    
    @Override
    public List<MenuAggregate> findByUserId(Long userId) {
        List<MenuPO> menuPOs = menuDao.selectByUserId(userId);
        return MenuConverter.INSTANCE.toMenuAggregates(menuPOs);
    }
} 