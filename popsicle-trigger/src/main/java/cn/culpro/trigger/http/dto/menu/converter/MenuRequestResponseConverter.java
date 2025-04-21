package cn.culpro.trigger.http.dto.menu.converter;

import cn.culpro.application.menu.command.CreateMenuCommand;
import cn.culpro.application.menu.command.UpdateMenuCommand;
import cn.culpro.application.menu.dto.MenuTreeDTO;
import cn.culpro.domain.system.model.aggregate.MenuAggregate;
import cn.culpro.trigger.http.dto.menu.CreateMenuRequest;
import cn.culpro.trigger.http.dto.menu.MenuDetailResponse;
import cn.culpro.trigger.http.dto.menu.MenuTreeResponse;
import cn.culpro.trigger.http.dto.menu.UpdateMenuRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单请求响应转换器
 * <p>
 * 负责菜单相关请求响应对象与应用层命令对象的相互转换
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Component
public class MenuRequestResponseConverter {
    
    /**
     * 将创建菜单请求转换为创建菜单命令
     *
     * @param request 创建菜单请求
     * @return 创建菜单命令
     */
    public CreateMenuCommand toCreateMenuCommand(CreateMenuRequest request) {
        return CreateMenuCommand.builder()
                .menuName(request.getMenuName())
                .parentId(request.getParentId())
                .icon(request.getIcon())
                .sort(request.getSort())
                .routeName(request.getRouteName())
                .routePath(request.getRoutePath())
                .componentPath(request.getComponentPath())
                .permission(request.getPermission())
                .remark(request.getRemark())
                .frameStatus(request.getFrameStatus())
                .frameUrl(request.getFrameUrl())
                .build();
    }
    
    /**
     * 将更新菜单请求转换为更新菜单命令
     *
     * @param request 更新菜单请求
     * @return 更新菜单命令
     */
    public UpdateMenuCommand toUpdateMenuCommand(UpdateMenuRequest request) {
        return UpdateMenuCommand.builder()
                .menuName(request.getMenuName())
                .icon(request.getIcon())
                .sort(request.getSort())
                .routeName(request.getRouteName())
                .routePath(request.getRoutePath())
                .componentPath(request.getComponentPath())
                .permission(request.getPermission())
                .remark(request.getRemark())
                .frameStatus(request.getFrameStatus())
                .frameUrl(request.getFrameUrl())
                .build();
    }
    
    /**
     * 将菜单聚合根转换为菜单详情响应
     *
     * @param menuAggregate 菜单聚合根
     * @return 菜单详情响应
     */
    public MenuDetailResponse toMenuDetailResponse(MenuAggregate menuAggregate) {
        return MenuDetailResponse.builder()
                .menuId(menuAggregate.getMenuId())
                .menuName(menuAggregate.getMenuName())
                .parentId(menuAggregate.getParentId())
                .menuType(menuAggregate.getMenuType().getCode())
                .icon(menuAggregate.getIcon())
                .sort(menuAggregate.getSort())
                .routeName(menuAggregate.getRouteName())
                .routePath(menuAggregate.getRoutePath())
                .componentPath(menuAggregate.getComponentPath())
                .permission(menuAggregate.getPermission())
                .remark(menuAggregate.getRemark())
                .frameStatus(menuAggregate.getFrameStatus())
                .frameUrl(menuAggregate.getFrameUrl())
                .status(menuAggregate.getStatus().getCode())
                .createTime(menuAggregate.getCreateTime())
                .updateTime(menuAggregate.getUpdateTime())
                .build();
    }
    
    /**
     * 将菜单树DTO转换为菜单树响应
     *
     * @param menuTreeDTO 菜单树DTO
     * @return 菜单树响应
     */
    public MenuTreeResponse toMenuTreeResponse(MenuTreeDTO menuTreeDTO) {
        if (menuTreeDTO == null) {
            return null;
        }
        
        List<MenuTreeResponse> children = new ArrayList<>();
        if (menuTreeDTO.getChildren() != null && !menuTreeDTO.getChildren().isEmpty()) {
            children = menuTreeDTO.getChildren().stream().map(this::toMenuTreeResponse).collect(Collectors.toList());
        }
        
        return MenuTreeResponse.builder()
                .id(menuTreeDTO.getId())
                .name(menuTreeDTO.getName())
                .parentId(menuTreeDTO.getParentId())
                .type(menuTreeDTO.getType())
                .icon(menuTreeDTO.getIcon())
                .sort(menuTreeDTO.getSort())
                .routeName(menuTreeDTO.getRouteName())
                .routePath(menuTreeDTO.getRoutePath())
                .componentPath(menuTreeDTO.getComponentPath())
                .permission(menuTreeDTO.getPermission())
                .frameStatus(menuTreeDTO.getFrameStatus())
                .frameUrl(menuTreeDTO.getFrameUrl())
                .status(menuTreeDTO.getStatus())
                .children(children)
                .build();
    }
} 