package cn.culpro.trigger.http;

import cn.culpro.application.role.command.AssignRoleMenusCommand;
import cn.culpro.application.role.command.CreateRoleCommand;
import cn.culpro.application.role.command.UpdateRoleCommand;
import cn.culpro.application.role.query.RoleQuery;
import cn.culpro.application.role.service.RoleApplicationService;
import cn.culpro.domain.system.model.aggregate.RoleAggregate;
import cn.culpro.trigger.http.dto.role.AssignRoleMenusRequest;
import cn.culpro.trigger.http.dto.role.CreateRoleRequest;
import cn.culpro.trigger.http.dto.role.RoleDetailResponse;
import cn.culpro.trigger.http.dto.role.RoleListResponse;
import cn.culpro.trigger.http.dto.role.RolePageQuery;
import cn.culpro.trigger.http.dto.role.UpdateRoleRequest;
import cn.culpro.trigger.http.dto.role.converter.RoleRequestResponseConverter;
import cn.culpro.types.model.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色控制器
 * <p>
 * 处理角色相关的HTTP请求
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    
    private final RoleApplicationService roleApplicationService;
    
    private final RoleRequestResponseConverter converter;
    
    /**
     * 创建角色
     * <p>
     * 创建新角色并返回角色ID
     *
     * @param request 创建角色请求
     * @return 角色ID
     */
    @PostMapping
    public ResponseDTO<Long> createRole(@RequestBody CreateRoleRequest request) {
        CreateRoleCommand command = converter.toCreateRoleCommand(request);
        Long roleId = roleApplicationService.createRole(command);
        return ResponseDTO.success(roleId);
    }
    
    /**
     * 更新角色
     * <p>
     * 更新角色信息
     *
     * @param roleId  角色ID
     * @param request 更新角色请求
     * @return 操作结果
     */
    @PutMapping("/{roleId}")
    public ResponseDTO<Boolean> updateRole(@PathVariable Long roleId, @RequestBody UpdateRoleRequest request) {
        UpdateRoleCommand command = converter.toUpdateRoleCommand(request);
        command.setRoleId(roleId);
        boolean result = roleApplicationService.updateRole(command);
        return ResponseDTO.success(result);
    }
    
    /**
     * 删除角色
     * <p>
     * 根据角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 操作结果
     */
    @DeleteMapping("/{roleId}")
    public ResponseDTO<Boolean> deleteRole(@PathVariable Long roleId) {
        boolean result = roleApplicationService.deleteRole(roleId);
        return ResponseDTO.success(result);
    }
    
    /**
     * 分配角色菜单权限
     * <p>
     * 更新角色关联的菜单权限
     *
     * @param roleId  角色ID
     * @param request 分配菜单请求
     * @return 操作结果
     */
    @PostMapping("/{roleId}/menus")
    public ResponseDTO<Boolean> assignRoleMenus(@PathVariable Long roleId, @RequestBody AssignRoleMenusRequest request) {
        AssignRoleMenusCommand command = new AssignRoleMenusCommand();
        command.setRoleId(roleId);
        command.setMenuIds(request.getMenuIds());
        boolean result = roleApplicationService.assignRoleMenus(command);
        return ResponseDTO.success(result);
    }
    
    /**
     * 获取角色详情
     * <p>
     * 根据角色ID查询角色详细信息
     *
     * @param roleId 角色ID
     * @return 角色详情
     */
    @GetMapping("/{roleId}")
    public ResponseDTO<RoleDetailResponse> getRoleDetail(@PathVariable Long roleId) {
        RoleAggregate roleAggregate = roleApplicationService.getRoleById(roleId);
        RoleDetailResponse response = converter.toRoleDetailResponse(roleAggregate);
        return ResponseDTO.success(response);
    }
    
    /**
     * 获取所有角色
     * <p>
     * 查询系统中所有角色列表
     *
     * @return 角色列表
     */
    @GetMapping
    public ResponseDTO<List<RoleListResponse>> getAllRoles() {
        List<RoleAggregate> roleAggregates = roleApplicationService.getAllRoles();
        List<RoleListResponse> responses = roleAggregates.stream().map(converter::toRoleListResponse).collect(Collectors.toList());
        return ResponseDTO.success(responses);
    }
    
    /**
     * 分页查询角色
     * <p>
     * 按条件分页查询角色
     *
     * @param query 查询条件
     * @return 角色列表
     */
    @GetMapping("/page")
    public ResponseDTO<List<RoleListResponse>> getRolesByPage(RolePageQuery query) {
        RoleQuery roleQuery = RoleQuery.builder()
                .roleName(query.getRoleName())
                .roleCode(query.getRoleCode())
                .pageNum(query.getPageNum())
                .pageSize(query.getPageSize())
                .build();
        
        List<RoleAggregate> roleAggregates = roleApplicationService.getRolesByPage(roleQuery);
        List<RoleListResponse> responses = roleAggregates.stream().map(converter::toRoleListResponse).collect(Collectors.toList());
        return ResponseDTO.success(responses);
    }
} 