package cn.culpro.application.menu.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建菜单命令
 * <p>
 * 封装创建菜单的请求参数
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuCommand {
    
    /**
     * 菜单名称
     */
    private String menuName;
    
    /**
     * 父级菜单ID
     */
    private Long parentId;
    
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
     * 备注
     */
    private String remark;
    
    /**
     * 外链状态：0否，1是
     */
    private Integer frameStatus;
    
    /**
     * 外链地址
     */
    private String frameUrl;
} 