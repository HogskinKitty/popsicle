package cn.culpro.trigger.http.dto.role.converter;

import cn.culpro.application.role.command.CreateRoleCommand;
import cn.culpro.application.role.command.UpdateRoleCommand;
import cn.culpro.domain.system.model.aggregate.RoleAggregate;
import cn.culpro.domain.system.model.entity.RoleMenuEntity;
import cn.culpro.trigger.http.dto.role.CreateRoleRequest;
import cn.culpro.trigger.http.dto.role.RoleDetailResponse;
import cn.culpro.trigger.http.dto.role.RoleListResponse;
import cn.culpro.trigger.http.dto.role.UpdateRoleRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色请求响应转换器
 * <p>
 * 负责角色相关请求响应对象与应用层命令对象的相互转换
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Component
public class RoleRequestResponseConverter {
    
    /**
     * 将创建角色请求转换为创建角色命令
     *
     * @param request 创建角色请求
     * @return 创建角色命令
     */
    public CreateRoleCommand toCreateRoleCommand(CreateRoleRequest request) {
        return CreateRoleCommand.builder()
                .roleName(request.getRoleName())
                .roleCode(request.getRoleCode())
                .roleDesc(request.getRoleDesc())
                .sort(request.getSort())
                .menuIds(request.getMenuIds())
                .build();
    }
    
    /**
     * 将更新角色请求转换为更新角色命令
     *
     * @param request 更新角色请求
     * @return 更新角色命令
     */
    public UpdateRoleCommand toUpdateRoleCommand(UpdateRoleRequest request) {
        return UpdateRoleCommand.builder()
                .roleName(request.getRoleName())
                .roleCode(request.getRoleCode())
                .roleDesc(request.getRoleDesc())
                .sort(request.getSort())
                .build();
    }
    
    /**
     * 将角色聚合根转换为角色详情响应
     *
     * @param roleAggregate 角色聚合根
     * @return 角色详情响应
     */
    public RoleDetailResponse toRoleDetailResponse(RoleAggregate roleAggregate) {
        List<Long> menuIds = roleAggregate.getRoleMenus().stream().map(RoleMenuEntity::getMenuId).collect(Collectors.toList());
        
        return RoleDetailResponse.builder()
                .roleId(roleAggregate.getRoleId())
                .roleName(roleAggregate.getRoleName())
                .roleCode(roleAggregate.getRoleCode())
                .roleDesc(roleAggregate.getRoleDesc())
                .sort(roleAggregate.getSort())
                .createTime(roleAggregate.getCreateTime())
                .updateTime(roleAggregate.getUpdateTime())
                .menuIds(menuIds)
                .build();
    }
    
    /**
     * 将角色聚合根转换为角色列表响应
     *
     * @param roleAggregate 角色聚合根
     * @return 角色列表响应
     */
    public RoleListResponse toRoleListResponse(RoleAggregate roleAggregate) {
        return RoleListResponse.builder()
                .roleId(roleAggregate.getRoleId())
                .roleName(roleAggregate.getRoleName())
                .roleCode(roleAggregate.getRoleCode())
                .roleDesc(roleAggregate.getRoleDesc())
                .sort(roleAggregate.getSort())
                .createTime(roleAggregate.getCreateTime())
                .build();
    }
} 