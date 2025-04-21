package cn.culpro.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 菜单持久化对象
 * <p>
 * 对应系统菜单表，用于菜单数据的持久化和查询操作
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuPO {
    
    /**
     * 菜单ID
     */
    private Long menuId;
    
    /**
     * 菜单名称
     */
    private String menuName;
    
    /**
     * 父菜单ID
     */
    private Long parentId;
    
    /**
     * 显示顺序
     */
    private Integer orderNum;
    
    /**
     * 路由地址
     */
    private String path;
    
    /**
     * 组件路径
     */
    private String component;
    
    /**
     * 路由参数
     */
    private String query;
    
    /**
     * 是否为外链（0是 1否）
     */
    private Integer isFrame;
    
    /**
     * 是否缓存（0缓存 1不缓存）
     */
    private Integer isCache;
    
    /**
     * 菜单类型（1目录 2菜单 3按钮）
     */
    private Integer menuType;
    
    /**
     * 菜单状态（0显示 1隐藏）
     */
    private Integer visible;
    
    /**
     * 菜单状态（0正常 1停用）
     */
    private Integer status;
    
    /**
     * 权限标识
     */
    private String permission;
    
    /**
     * 菜单图标
     */
    private String icon;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 