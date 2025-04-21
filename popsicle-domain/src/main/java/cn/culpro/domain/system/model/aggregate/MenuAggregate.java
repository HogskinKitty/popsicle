package cn.culpro.domain.system.model.aggregate;

import cn.culpro.domain.system.model.valobj.MenuStatusVO;
import cn.culpro.domain.system.model.valobj.MenuTypeVO;
import cn.culpro.types.model.Aggregate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 菜单聚合根
 * <p>
 * 负责维护菜单的生命周期和一致性，是外部访问菜单相关数据的唯一入口
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Getter
@Builder
public class MenuAggregate implements Aggregate<Long> {
    
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
    private MenuTypeVO menuType;
    
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
    
    /**
     * 状态
     */
    private MenuStatusVO status;
    
    /**
     * 删除状态：0否，1是
     */
    private Integer deleteStatus;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建目录
     *
     * @param menuName 菜单名称
     * @param parentId 父级菜单ID
     * @param icon     图标
     * @param sort     排序
     * @return 菜单聚合根
     */
    public static MenuAggregate createDirectory(String menuName, Long parentId, String icon, Integer sort) {
        return MenuAggregate.builder()
                .menuName(menuName)
                .parentId(parentId)
                .menuType(MenuTypeVO.DIRECTORY)
                .icon(icon)
                .sort(sort)
                .status(MenuStatusVO.NORMAL)
                .deleteStatus(0)
                .frameStatus(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }
    
    /**
     * 创建菜单
     *
     * @param menuName      菜单名称
     * @param parentId      父级菜单ID
     * @param icon          图标
     * @param sort          排序
     * @param routeName     路由名称
     * @param routePath     路由地址
     * @param componentPath 组件地址
     * @return 菜单聚合根
     */
    public static MenuAggregate createMenu(String menuName, Long parentId, String icon, Integer sort, String routeName,
            String routePath, String componentPath) {
        return MenuAggregate.builder()
                .menuName(menuName)
                .parentId(parentId)
                .menuType(MenuTypeVO.MENU)
                .icon(icon)
                .sort(sort)
                .routeName(routeName)
                .routePath(routePath)
                .componentPath(componentPath)
                .status(MenuStatusVO.NORMAL)
                .deleteStatus(0)
                .frameStatus(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }
    
    /**
     * 创建按钮
     *
     * @param menuName   菜单名称
     * @param parentId   父级菜单ID
     * @param permission 权限标识
     * @param sort       排序
     * @return 菜单聚合根
     */
    public static MenuAggregate createButton(String menuName, Long parentId, String permission, Integer sort) {
        return MenuAggregate.builder()
                .menuName(menuName)
                .parentId(parentId)
                .menuType(MenuTypeVO.BUTTON)
                .permission(permission)
                .sort(sort)
                .status(MenuStatusVO.NORMAL)
                .deleteStatus(0)
                .frameStatus(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }
    
    /**
     * 更新基本信息
     *
     * @param menuName 菜单名称
     * @param icon     图标
     * @param sort     排序
     */
    public void updateBasicInfo(String menuName, String icon, Integer sort) {
        this.menuName = menuName;
        this.icon = icon;
        this.sort = sort;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 更新路由信息
     *
     * @param routeName     路由名称
     * @param routePath     路由地址
     * @param componentPath 组件地址
     */
    public void updateRouteInfo(String routeName, String routePath, String componentPath) {
        this.routeName = routeName;
        this.routePath = routePath;
        this.componentPath = componentPath;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 更新权限标识
     *
     * @param permission 权限标识
     */
    public void updatePermission(String permission) {
        this.permission = permission;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 设置为外链
     *
     * @param frameUrl 外链地址
     */
    public void setAsFrame(String frameUrl) {
        this.frameStatus = 1;
        this.frameUrl = frameUrl;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 取消外链
     */
    public void cancelFrame() {
        this.frameStatus = 0;
        this.frameUrl = null;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 禁用菜单
     */
    public void disable() {
        this.status = MenuStatusVO.DISABLED;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 启用菜单
     */
    public void enable() {
        this.status = MenuStatusVO.NORMAL;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 标记为已删除
     */
    public void markAsDeleted() {
        this.deleteStatus = 1;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 获取聚合ID
     *
     * @return 菜单ID
     */
    @Override
    public Long getId() {
        return this.menuId;
    }
} 