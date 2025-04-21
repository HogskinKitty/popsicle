package cn.culpro.trigger.http.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树响应
 * <p>
 * 用于返回菜单树形结构的数据
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeResponse {
    
    /**
     * 菜单ID
     */
    private Long id;
    
    /**
     * 菜单名称
     */
    private String name;
    
    /**
     * 父级菜单ID
     */
    private Long parentId;
    
    /**
     * 菜单类型: 1-目录，2-菜单，3-按钮
     */
    private Integer type;
    
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
    
    /**
     * 外链状态：0否，1是
     */
    private Integer frameStatus;
    
    /**
     * 外链地址
     */
    private String frameUrl;
    
    /**
     * 菜单状态: 0-正常，1-禁用
     */
    private Integer status;
    
    /**
     * 子菜单列表
     */
    @Builder.Default
    private List<MenuTreeResponse> children = new ArrayList<>();
} 