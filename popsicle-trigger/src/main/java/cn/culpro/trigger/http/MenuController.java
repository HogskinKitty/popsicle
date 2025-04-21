package cn.culpro.trigger.http;


import cn.culpro.application.menu.command.CreateMenuCommand;
import cn.culpro.application.menu.command.UpdateMenuCommand;
import cn.culpro.application.menu.dto.MenuTreeDTO;
import cn.culpro.application.menu.service.MenuApplicationService;
import cn.culpro.domain.system.model.aggregate.MenuAggregate;
import cn.culpro.trigger.http.dto.menu.CreateMenuRequest;
import cn.culpro.trigger.http.dto.menu.MenuDetailResponse;
import cn.culpro.trigger.http.dto.menu.MenuTreeResponse;
import cn.culpro.trigger.http.dto.menu.UpdateMenuRequest;
import cn.culpro.trigger.http.dto.menu.converter.MenuRequestResponseConverter;
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
 * 菜单控制器
 * <p>
 * 处理菜单相关的HTTP请求
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {
    
    private final MenuApplicationService menuApplicationService;
    
    private final MenuRequestResponseConverter converter;
    
    /**
     * 创建目录
     * <p>
     * 创建新菜单目录
     *
     * @param request 创建菜单请求
     * @return 菜单ID
     */
    @PostMapping("/directory")
    public ResponseDTO<Long> createDirectory(@RequestBody CreateMenuRequest request) {
        CreateMenuCommand command = converter.toCreateMenuCommand(request);
        Long menuId = menuApplicationService.createDirectory(command);
        return ResponseDTO.success(menuId);
    }
    
    /**
     * 创建菜单
     * <p>
     * 创建可跳转的菜单项
     *
     * @param request 创建菜单请求
     * @return 菜单ID
     */
    @PostMapping("/menu")
    public ResponseDTO<Long> createMenu(@RequestBody CreateMenuRequest request) {
        CreateMenuCommand command = converter.toCreateMenuCommand(request);
        Long menuId = menuApplicationService.createMenu(command);
        return ResponseDTO.success(menuId);
    }
    
    /**
     * 创建按钮
     * <p>
     * 创建无路由的按钮权限
     *
     * @param request 创建菜单请求
     * @return 菜单ID
     */
    @PostMapping("/button")
    public ResponseDTO<Long> createButton(@RequestBody CreateMenuRequest request) {
        CreateMenuCommand command = converter.toCreateMenuCommand(request);
        Long menuId = menuApplicationService.createButton(command);
        return ResponseDTO.success(menuId);
    }
    
    /**
     * 更新菜单
     * <p>
     * 更新菜单信息
     *
     * @param menuId  菜单ID
     * @param request 更新菜单请求
     * @return 操作结果
     */
    @PutMapping("/{menuId}")
    public ResponseDTO<Boolean> updateMenu(@PathVariable Long menuId, @RequestBody UpdateMenuRequest request) {
        UpdateMenuCommand command = converter.toUpdateMenuCommand(request);
        command.setMenuId(menuId);
        boolean result = menuApplicationService.updateMenu(command);
        return ResponseDTO.success(result);
    }
    
    /**
     * 删除菜单
     * <p>
     * 根据菜单ID删除菜单
     *
     * @param menuId 菜单ID
     * @return 操作结果
     */
    @DeleteMapping("/{menuId}")
    public ResponseDTO<Boolean> deleteMenu(@PathVariable Long menuId) {
        boolean result = menuApplicationService.deleteMenu(menuId);
        return ResponseDTO.success(result);
    }
    
    /**
     * 启用菜单
     * <p>
     * 将菜单状态设置为正常
     *
     * @param menuId 菜单ID
     * @return 操作结果
     */
    @PutMapping("/{menuId}/enable")
    public ResponseDTO<Boolean> enableMenu(@PathVariable Long menuId) {
        boolean result = menuApplicationService.enableMenu(menuId);
        return ResponseDTO.success(result);
    }
    
    /**
     * 禁用菜单
     * <p>
     * 将菜单状态设置为禁用
     *
     * @param menuId 菜单ID
     * @return 操作结果
     */
    @PutMapping("/{menuId}/disable")
    public ResponseDTO<Boolean> disableMenu(@PathVariable Long menuId) {
        boolean result = menuApplicationService.disableMenu(menuId);
        return ResponseDTO.success(result);
    }
    
    /**
     * 获取菜单树
     * <p>
     * 构建系统菜单树形结构
     *
     * @return 菜单树列表
     */
    @GetMapping("/tree")
    public ResponseDTO<List<MenuTreeResponse>> getMenuTree() {
        List<MenuTreeDTO> menuTreeDTOs = menuApplicationService.getMenuTree();
        List<MenuTreeResponse> responses = menuTreeDTOs.stream().map(converter::toMenuTreeResponse).collect(Collectors.toList());
        return ResponseDTO.success(responses);
    }
    
    /**
     * 获取菜单详情
     * <p>
     * 根据菜单ID查询菜单详细信息
     *
     * @param menuId 菜单ID
     * @return 菜单详情
     */
    @GetMapping("/{menuId}")
    public ResponseDTO<MenuDetailResponse> getMenuDetail(@PathVariable Long menuId) {
        MenuAggregate menuAggregate = menuApplicationService.getMenuById(menuId);
        MenuDetailResponse response = converter.toMenuDetailResponse(menuAggregate);
        return ResponseDTO.success(response);
    }
    
    /**
     * 获取用户菜单树
     * <p>
     * 根据用户ID获取用户有权限的菜单树
     *
     * @param userId 用户ID
     * @return 菜单树列表
     */
    @GetMapping("/user/{userId}/tree")
    public ResponseDTO<List<MenuTreeResponse>> getUserMenuTree(@PathVariable Long userId) {
        List<MenuTreeDTO> menuTreeDTOs = menuApplicationService.getUserMenuTree(userId);
        List<MenuTreeResponse> responses = menuTreeDTOs.stream().map(converter::toMenuTreeResponse).collect(Collectors.toList());
        return ResponseDTO.success(responses);
    }
} 