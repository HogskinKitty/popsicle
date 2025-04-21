package cn.culpro.application.menu.service;

import cn.culpro.application.menu.command.CreateMenuCommand;
import cn.culpro.application.menu.command.UpdateMenuCommand;
import cn.culpro.application.menu.dto.MenuTreeDTO;
import cn.culpro.domain.system.adapter.repository.IMenuRepository;
import cn.culpro.domain.system.model.aggregate.MenuAggregate;
import cn.culpro.domain.system.model.valobj.MenuStatusVO;
import cn.culpro.domain.system.model.valobj.MenuTypeVO;
import cn.culpro.types.enums.ResponseCode;
import cn.culpro.types.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 菜单应用服务
 * <p>
 * 处理菜单管理相关的应用场景，协调菜单领域模型实现业务功能
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Service
@RequiredArgsConstructor
public class MenuApplicationService {
    
    private final IMenuRepository menuRepository;
    
    /**
     * 创建目录
     * <p>
     * 创建新菜单目录
     *
     * @param command 创建菜单命令
     * @return 菜单ID
     */
    @Transactional
    public Long createDirectory(CreateMenuCommand command) {
        // 验证父级菜单
        validateParentMenu(command.getParentId());
        
        // 创建目录聚合根
        MenuAggregate menuAggregate = MenuAggregate.createDirectory(command.getMenuName(), command.getParentId(), command.getIcon(),
                command.getSort());
        
        // 设置备注
        if (StringUtils.hasText(command.getRemark())) {
            menuAggregate.setRemark(command.getRemark());
        }
        
        // 保存菜单
        return menuRepository.save(menuAggregate);
    }
    
    /**
     * 创建菜单
     * <p>
     * 创建可跳转的菜单项
     *
     * @param command 创建菜单命令
     * @return 菜单ID
     */
    @Transactional
    public Long createMenu(CreateMenuCommand command) {
        // 验证父级菜单
        validateParentMenu(command.getParentId());
        
        // 验证必要参数
        if (!StringUtils.hasText(command.getRouteName()) || !StringUtils.hasText(command.getRoutePath()) || !StringUtils.hasText(
                command.getComponentPath())) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "路由名称、路由路径和组件路径不能为空");
        }
        
        // 创建菜单聚合根
        MenuAggregate menuAggregate = MenuAggregate.createMenu(command.getMenuName(), command.getParentId(), command.getIcon(),
                command.getSort(), command.getRouteName(), command.getRoutePath(), command.getComponentPath());
        
        // 设置权限标识
        if (StringUtils.hasText(command.getPermission())) {
            menuAggregate.updatePermission(command.getPermission());
        }
        
        // 设置备注
        if (StringUtils.hasText(command.getRemark())) {
            menuAggregate.setRemark(command.getRemark());
        }
        
        // 设置外链
        if (command.getFrameStatus() != null && command.getFrameStatus() == 1 && StringUtils.hasText(command.getFrameUrl())) {
            menuAggregate.setAsFrame(command.getFrameUrl());
        }
        
        // 保存菜单
        return menuRepository.save(menuAggregate);
    }
    
    /**
     * 创建按钮
     * <p>
     * 创建无路由的按钮权限
     *
     * @param command 创建菜单命令
     * @return 菜单ID
     */
    @Transactional
    public Long createButton(CreateMenuCommand command) {
        // 验证父级菜单
        validateParentMenu(command.getParentId());
        
        // 验证权限标识
        if (!StringUtils.hasText(command.getPermission())) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "按钮权限标识不能为空");
        }
        
        // 检查权限标识唯一性
        Optional<MenuAggregate> existingMenu = menuRepository.findByPermission(command.getPermission());
        if (existingMenu.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "权限标识已存在");
        }
        
        // 创建按钮聚合根
        MenuAggregate menuAggregate = MenuAggregate.createButton(command.getMenuName(), command.getParentId(), command.getPermission(),
                command.getSort());
        
        // 设置备注
        if (StringUtils.hasText(command.getRemark())) {
            menuAggregate.setRemark(command.getRemark());
        }
        
        // 保存菜单
        return menuRepository.save(menuAggregate);
    }
    
    /**
     * 更新菜单
     * <p>
     * 更新菜单基本信息
     *
     * @param command 更新菜单命令
     * @return 是否更新成功
     */
    @Transactional
    public boolean updateMenu(UpdateMenuCommand command) {
        // 查找菜单
        Optional<MenuAggregate> menuOptional = menuRepository.findById(command.getMenuId());
        if (!menuOptional.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "菜单不存在");
        }
        
        MenuAggregate menuAggregate = menuOptional.get();
        MenuTypeVO menuType = menuAggregate.getMenuType();
        
        // 更新基本信息
        menuAggregate.updateBasicInfo(command.getMenuName(), command.getIcon(), command.getSort());
        
        // 设置备注
        if (StringUtils.hasText(command.getRemark())) {
            menuAggregate.setRemark(command.getRemark());
        }
        
        // 根据菜单类型更新特定信息
        if (menuType == MenuTypeVO.MENU) {
            // 更新路由信息
            if (StringUtils.hasText(command.getRouteName()) && StringUtils.hasText(command.getRoutePath()) && StringUtils.hasText(
                    command.getComponentPath())) {
                menuAggregate.updateRouteInfo(command.getRouteName(), command.getRoutePath(), command.getComponentPath());
            }
            
            // 更新外链信息
            if (command.getFrameStatus() != null) {
                if (command.getFrameStatus() == 1 && StringUtils.hasText(command.getFrameUrl())) {
                    menuAggregate.setAsFrame(command.getFrameUrl());
                } else {
                    menuAggregate.cancelFrame();
                }
            }
        }
        
        // 更新权限标识（仅菜单和按钮类型）
        if ((menuType == MenuTypeVO.MENU || menuType == MenuTypeVO.BUTTON) && StringUtils.hasText(command.getPermission())) {
            // 检查权限标识唯一性
            Optional<MenuAggregate> existingMenu = menuRepository.findByPermission(command.getPermission());
            if (existingMenu.isPresent() && !existingMenu.get().getMenuId().equals(command.getMenuId())) {
                throw new BusinessException(ResponseCode.BUSINESS_ERROR, "权限标识已存在");
            }
            menuAggregate.updatePermission(command.getPermission());
        }
        
        // 保存菜单
        menuRepository.save(menuAggregate);
        return true;
    }
    
    /**
     * 删除菜单
     * <p>
     * 根据菜单ID删除菜单
     *
     * @param menuId 菜单ID
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteMenu(Long menuId) {
        // 查找菜单
        Optional<MenuAggregate> menuOptional = menuRepository.findById(menuId);
        if (!menuOptional.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "菜单不存在");
        }
        
        // 检查是否有子菜单
        List<MenuAggregate> children = menuRepository.findByParentId(menuId);
        if (!children.isEmpty()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "存在子菜单，不能删除");
        }
        
        // 标记为已删除
        MenuAggregate menuAggregate = menuOptional.get();
        menuAggregate.markAsDeleted();
        menuRepository.save(menuAggregate);
        return true;
    }
    
    /**
     * 启用菜单
     * <p>
     * 将菜单状态设置为正常
     *
     * @param menuId 菜单ID
     * @return 是否操作成功
     */
    @Transactional
    public boolean enableMenu(Long menuId) {
        // 查找菜单
        Optional<MenuAggregate> menuOptional = menuRepository.findById(menuId);
        if (!menuOptional.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "菜单不存在");
        }
        
        // 启用菜单
        MenuAggregate menuAggregate = menuOptional.get();
        menuAggregate.enable();
        menuRepository.save(menuAggregate);
        return true;
    }
    
    /**
     * 禁用菜单
     * <p>
     * 将菜单状态设置为禁用
     *
     * @param menuId 菜单ID
     * @return 是否操作成功
     */
    @Transactional
    public boolean disableMenu(Long menuId) {
        // 查找菜单
        Optional<MenuAggregate> menuOptional = menuRepository.findById(menuId);
        if (!menuOptional.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "菜单不存在");
        }
        
        // 禁用菜单
        MenuAggregate menuAggregate = menuOptional.get();
        menuAggregate.disable();
        menuRepository.save(menuAggregate);
        return true;
    }
    
    /**
     * 获取菜单树
     * <p>
     * 构建系统菜单树形结构
     *
     * @return 菜单树列表
     */
    public List<MenuTreeDTO> getMenuTree() {
        // 获取所有菜单
        List<MenuAggregate> allMenus = menuRepository.findAll();
        
        // 过滤未删除的菜单并按类型和排序整理
        List<MenuAggregate> validMenus = allMenus.stream()
                .filter(menu -> menu.getDeleteStatus() == 0)
                .sorted(Comparator.comparing(MenuAggregate::getMenuType).thenComparing(MenuAggregate::getSort))
                .collect(Collectors.toList());
        
        // 构建菜单树
        return buildMenuTree(validMenus, 0L);
    }
    
    /**
     * 获取菜单详情
     * <p>
     * 根据菜单ID查询菜单详细信息
     *
     * @param menuId 菜单ID
     * @return 菜单聚合根
     */
    public MenuAggregate getMenuById(Long menuId) {
        Optional<MenuAggregate> menuOptional = menuRepository.findById(menuId);
        if (!menuOptional.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "菜单不存在");
        }
        return menuOptional.get();
    }
    
    /**
     * 获取用户菜单树
     * <p>
     * 根据用户ID获取用户有权限的菜单树
     *
     * @param userId 用户ID
     * @return 菜单树列表
     */
    public List<MenuTreeDTO> getUserMenuTree(Long userId) {
        // 获取用户有权限的菜单
        List<MenuAggregate> userMenus = menuRepository.findByUserId(userId);
        
        // 过滤未删除且正常状态的菜单
        List<MenuAggregate> validMenus = userMenus.stream()
                .filter(menu -> menu.getDeleteStatus() == 0 && menu.getStatus() == MenuStatusVO.NORMAL)
                .sorted(Comparator.comparing(MenuAggregate::getMenuType).thenComparing(MenuAggregate::getSort))
                .collect(Collectors.toList());
        
        // 构建菜单树
        return buildMenuTree(validMenus, 0L);
    }
    
    /**
     * 构建菜单树
     * <p>
     * 递归构建菜单树结构
     *
     * @param menus    菜单列表
     * @param parentId 父级ID
     * @return 菜单树列表
     */
    private List<MenuTreeDTO> buildMenuTree(List<MenuAggregate> menus, Long parentId) {
        List<MenuTreeDTO> menuTree = new ArrayList<>();
        
        // 筛选当前父级下的子菜单
        List<MenuAggregate> childMenus = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .collect(Collectors.toList());
        
        for (MenuAggregate menu : childMenus) {
            MenuTreeDTO treeNode = new MenuTreeDTO();
            treeNode.setId(menu.getMenuId());
            treeNode.setName(menu.getMenuName());
            treeNode.setParentId(menu.getParentId());
            treeNode.setType(menu.getMenuType().getCode());
            treeNode.setIcon(menu.getIcon());
            treeNode.setSort(menu.getSort());
            treeNode.setRouteName(menu.getRouteName());
            treeNode.setRoutePath(menu.getRoutePath());
            treeNode.setComponentPath(menu.getComponentPath());
            treeNode.setPermission(menu.getPermission());
            treeNode.setFrameStatus(menu.getFrameStatus());
            treeNode.setFrameUrl(menu.getFrameUrl());
            treeNode.setStatus(menu.getStatus().getCode());
            
            // 递归获取子菜单
            List<MenuTreeDTO> children = buildMenuTree(menus, menu.getMenuId());
            treeNode.setChildren(children);
            
            menuTree.add(treeNode);
        }
        
        return menuTree;
    }
    
    /**
     * 验证父级菜单
     * <p>
     * 检查父级菜单是否存在且是否为有效类型
     *
     * @param parentId 父级菜单ID
     */
    private void validateParentMenu(Long parentId) {
        // 如果是根菜单，不需要验证
        if (parentId == 0L) {
            return;
        }
        
        // 查找父级菜单
        Optional<MenuAggregate> parentOptional = menuRepository.findById(parentId);
        if (!parentOptional.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "父级菜单不存在");
        }
        
        // 检查父级菜单类型
        MenuAggregate parent = parentOptional.get();
        if (parent.getMenuType() == MenuTypeVO.BUTTON) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "按钮类型不能作为父级菜单");
        }
        
        // 检查父级菜单状态
        if (parent.getDeleteStatus() == 1) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "父级菜单已删除");
        }
    }
} 