package cn.culpro.infrastructure.dao.po.converter;

import cn.culpro.domain.system.model.aggregate.RoleAggregate;
import cn.culpro.infrastructure.dao.po.RolePO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 角色PO与领域对象转换器
 * <p>
 * 负责角色持久化对象与领域对象之间的相互转换
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Mapper
public interface RoleConverter {
    
    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);
    
    /**
     * 将角色PO转换为角色聚合根
     *
     * @param rolePO 角色PO
     * @return 角色聚合根
     */
    RoleAggregate toRoleAggregate(RolePO rolePO);
    
    /**
     * 将角色聚合根转换为角色PO
     *
     * @param roleAggregate 角色聚合根
     * @return 角色PO
     */
    RolePO toRolePO(RoleAggregate roleAggregate);
    
    /**
     * 将角色PO列表转换为角色聚合根列表
     *
     * @param rolePOs 角色PO列表
     * @return 角色聚合根列表
     */
    List<RoleAggregate> toRoleAggregates(List<RolePO> rolePOs);
    
    /**
     * 将角色聚合根列表转换为角色PO列表
     *
     * @param roleAggregates 角色聚合根列表
     * @return 角色PO列表
     */
    List<RolePO> toRolePOs(List<RoleAggregate> roleAggregates);
} 