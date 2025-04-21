package cn.culpro.trigger.http.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单响应DTO
 * <p>
 * 用于返回菜单基本信息
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    
    /**
     * 菜单ID
     */
    private Long menuId;
    
    /**
     * 菜单名称
     */
    private String menuName;
    
    /**
     * 父级菜单ID
     */
    private Long parentId;
    
    /**
     * 菜单类型
     */
    private Integer menuType;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 路由名称
     */
    private String routeName;
    
    /**
     * 路由地址
     */
    private String routePath;
    
    /**
     * 组件地址
     */
    private String componentPath;
    
    /**
     * 权限标识
     */
    private String permission;
} 