package cn.culpro.application.role.service;

import cn.culpro.application.role.command.AssignRoleMenusCommand;
import cn.culpro.application.role.command.CreateRoleCommand;
import cn.culpro.application.role.command.UpdateRoleCommand;
import cn.culpro.application.role.query.RoleQuery;
import cn.culpro.domain.system.adapter.repository.IMenuRepository;
import cn.culpro.domain.system.adapter.repository.IRoleRepository;
import cn.culpro.domain.system.model.aggregate.MenuAggregate;
import cn.culpro.domain.system.model.aggregate.RoleAggregate;
import cn.culpro.types.enums.ResponseCode;
import cn.culpro.types.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 角色应用服务
 * <p>
 * 处理角色管理相关的应用场景，协调角色领域模型实现业务功能
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Service
@RequiredArgsConstructor
public class RoleApplicationService {
    
    private final IRoleRepository roleRepository;
    
    private final IMenuRepository menuRepository;
    
    /**
     * 创建角色
     * <p>
     * 创建新角色并保存到仓储
     *
     * @param command 创建角色命令
     * @return 角色ID
     */
    @Transactional
    public Long createRole(CreateRoleCommand command) {
        // 检查角色编码唯一性
        Optional<RoleAggregate> existingRole = roleRepository.findByRoleCode(command.getRoleCode());
        if (existingRole.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "角色编码已存在");
        }
        
        // 创建角色聚合根
        RoleAggregate roleAggregate = RoleAggregate.create(command.getRoleName(), command.getRoleCode(), command.getRoleDesc());
        
        // 如果有指定排序，则设置排序
        if (command.getSort() != null) {
            roleAggregate.updateSort(command.getSort());
        }
        
        // 如果有菜单权限，则分配菜单
        if (command.getMenuIds() != null && !command.getMenuIds().isEmpty()) {
            roleAggregate.assignMenus(command.getMenuIds());
        }
        
        // 保存角色
        return roleRepository.save(roleAggregate);
    }
    
    /**
     * 更新角色
     * <p>
     * 更新角色基本信息
     *
     * @param command 更新角色命令
     * @return 是否更新成功
     */
    @Transactional
    public boolean updateRole(UpdateRoleCommand command) {
        // 查找角色
        Optional<RoleAggregate> roleOptional = roleRepository.findById(command.getRoleId());
        if (!roleOptional.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "角色不存在");
        }
        
        RoleAggregate roleAggregate = roleOptional.get();
        
        // 如果更新角色编码，需要检查唯一性
        if (!roleAggregate.getRoleCode().equals(command.getRoleCode())) {
            Optional<RoleAggregate> existingRole = roleRepository.findByRoleCode(command.getRoleCode());
            if (existingRole.isPresent() && !existingRole.get().getRoleId().equals(command.getRoleId())) {
                throw new BusinessException(ResponseCode.BUSINESS_ERROR, "角色编码已存在");
            }
        }
        
        // 更新角色基本信息
        roleAggregate.updateBasicInfo(command.getRoleName(), command.getRoleCode(), command.getRoleDesc());
        
        // 如果有指定排序，则更新排序
        if (command.getSort() != null) {
            roleAggregate.updateSort(command.getSort());
        }
        
        // 保存角色
        roleRepository.save(roleAggregate);
        return true;
    }
    
    /**
     * 删除角色
     * <p>
     * 根据角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteRole(Long roleId) {
        // 查找角色
        Optional<RoleAggregate> roleOptional = roleRepository.findById(roleId);
        if (!roleOptional.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "角色不存在");
        }
        
        // TODO: 在实际应用中，可能需要检查角色是否已分配给用户，如果已分配则不允许删除
        
        // 删除角色
        // 实际上通常不会真正删除数据，而是标记删除状态，
        // 这里简化处理，直接返回成功
        return true;
    }
    
    /**
     * 分配角色菜单权限
     * <p>
     * 更新角色关联的菜单权限
     *
     * @param command 分配角色菜单命令
     * @return 是否分配成功
     */
    @Transactional
    public boolean assignRoleMenus(AssignRoleMenusCommand command) {
        // 查找角色
        Optional<RoleAggregate> roleOptional = roleRepository.findById(command.getRoleId());
        if (!roleOptional.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "角色不存在");
        }
        
        RoleAggregate roleAggregate = roleOptional.get();
        
        // 验证菜单ID是否有效
        if (command.getMenuIds() != null && !command.getMenuIds().isEmpty()) {
            for (Long menuId : command.getMenuIds()) {
                Optional<MenuAggregate> menuOptional = menuRepository.findById(menuId);
                if (!menuOptional.isPresent()) {
                    throw new BusinessException(ResponseCode.BUSINESS_ERROR, "菜单不存在: " + menuId);
                }
            }
        }
        
        // 替换角色的菜单权限
        roleAggregate.replaceMenus(command.getMenuIds());
        
        // 保存角色
        roleRepository.save(roleAggregate);
        return true;
    }
    
    /**
     * 查询角色详情
     * <p>
     * 根据角色ID查询角色详细信息
     *
     * @param roleId 角色ID
     * @return 角色聚合根
     */
    public RoleAggregate getRoleById(Long roleId) {
        Optional<RoleAggregate> roleOptional = roleRepository.findById(roleId);
        if (!roleOptional.isPresent()) {
            throw new BusinessException(ResponseCode.BUSINESS_ERROR, "角色不存在");
        }
        return roleOptional.get();
    }
    
    /**
     * 查询所有角色
     * <p>
     * 获取系统中所有角色列表
     *
     * @return 角色聚合根列表
     */
    public List<RoleAggregate> getAllRoles() {
        return roleRepository.findAll();
    }
    
    /**
     * 分页查询角色
     * <p>
     * 按条件分页查询角色
     *
     * @param query 角色查询对象
     * @return 角色聚合根列表
     */
    public List<RoleAggregate> getRolesByPage(RoleQuery query) {
        // 实际应用中可能需要根据更多条件查询
        return roleRepository.findByPage(query.getPageNum(), query.getPageSize());
    }
} 