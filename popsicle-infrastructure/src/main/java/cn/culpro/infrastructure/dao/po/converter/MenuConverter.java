package cn.culpro.infrastructure.dao.po.converter;

import cn.culpro.domain.system.model.aggregate.MenuAggregate;
import cn.culpro.domain.system.model.valobj.MenuStatusVO;
import cn.culpro.domain.system.model.valobj.MenuTypeVO;
import cn.culpro.infrastructure.dao.po.MenuPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 菜单PO与领域对象转换器
 * <p>
 * 负责菜单持久化对象与领域对象之间的相互转换
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Mapper
public interface MenuConverter {
    
    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);
    
    /**
     * 将菜单PO转换为菜单聚合根
     *
     * @param menuPO 菜单PO
     * @return 菜单聚合根
     */
    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "toMenuTypeVO")
    @Mapping(target = "status", source = "status", qualifiedByName = "toMenuStatusVO")
    MenuAggregate toMenuAggregate(MenuPO menuPO);
    
    /**
     * 将菜单聚合根转换为菜单PO
     *
     * @param menuAggregate 菜单聚合根
     * @return 菜单PO
     */
    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "fromMenuTypeVO")
    @Mapping(target = "status", source = "status", qualifiedByName = "fromMenuStatusVO")
    MenuPO toMenuPO(MenuAggregate menuAggregate);
    
    /**
     * 将菜单PO列表转换为菜单聚合根列表
     *
     * @param menuPOs 菜单PO列表
     * @return 菜单聚合根列表
     */
    List<MenuAggregate> toMenuAggregates(List<MenuPO> menuPOs);
    
    /**
     * 将菜单聚合根列表转换为菜单PO列表
     *
     * @param menuAggregates 菜单聚合根列表
     * @return 菜单PO列表
     */
    List<MenuPO> toMenuPOs(List<MenuAggregate> menuAggregates);
    
    /**
     * 将菜单类型码转换为菜单类型值对象
     *
     * @param menuType 菜单类型码
     * @return 菜单类型值对象
     */
    @Named("toMenuTypeVO")
    default MenuTypeVO toMenuTypeVO(Integer menuType) {
        if (menuType == null) {
            return MenuTypeVO.MENU;
        }
        return MenuTypeVO.fromCode(menuType);
    }
    
    /**
     * 将菜单类型值对象转换为菜单类型码
     *
     * @param menuTypeVO 菜单类型值对象
     * @return 菜单类型码
     */
    @Named("fromMenuTypeVO")
    default Integer fromMenuTypeVO(MenuTypeVO menuTypeVO) {
        if (menuTypeVO == null) {
            return MenuTypeVO.MENU.getCode();
        }
        return menuTypeVO.getCode();
    }
    
    /**
     * 将菜单状态码转换为菜单状态值对象
     *
     * @param status 菜单状态码
     * @return 菜单状态值对象
     */
    @Named("toMenuStatusVO")
    default MenuStatusVO toMenuStatusVO(Integer status) {
        if (status == null) {
            return MenuStatusVO.NORMAL;
        }
        return status == 0 ? MenuStatusVO.NORMAL : MenuStatusVO.DISABLED;
    }
    
    /**
     * 将菜单状态值对象转换为菜单状态码
     *
     * @param menuStatusVO 菜单状态值对象
     * @return 菜单状态码
     */
    @Named("fromMenuStatusVO")
    default Integer fromMenuStatusVO(MenuStatusVO menuStatusVO) {
        if (menuStatusVO == null) {
            return 0;
        }
        return menuStatusVO == MenuStatusVO.NORMAL ? 0 : 1;
    }
} 