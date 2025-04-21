package cn.culpro.infrastructure.dao.po.converter;

import cn.culpro.domain.system.model.aggregate.UserAggregate;
import cn.culpro.domain.system.model.entity.UserRoleEntity;
import cn.culpro.domain.system.model.valobj.UserStatusVO;
import cn.culpro.infrastructure.dao.po.UserPO;
import cn.culpro.infrastructure.dao.po.UserRolePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户PO与领域对象转换器
 * <p>
 * 负责用户持久化对象与领域对象之间的相互转换
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Mapper
public interface UserConverter {
    
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);
    
    /**
     * 将用户PO转换为用户聚合根
     *
     * @param userPO      用户PO
     * @param userRolePOs 用户角色关联PO列表
     * @return 用户聚合根
     */
    @Mapping(target = "status", source = "userPO.status", qualifiedByName = "toUserStatusVO")
    @Mapping(target = "userRoles", source = "userRolePOs", qualifiedByName = "toUserRoleEntities")
    UserAggregate toUserAggregate(UserPO userPO, List<UserRolePO> userRolePOs);
    
    /**
     * 将用户聚合根转换为用户PO
     *
     * @param userAggregate 用户聚合根
     * @return 用户PO
     */
    @Mapping(target = "status", source = "status", qualifiedByName = "fromUserStatusVO")
    UserPO toUserPO(UserAggregate userAggregate);
    
    /**
     * 将用户角色实体列表转换为用户角色关联PO列表
     *
     * @param userRoleEntities 用户角色实体列表
     * @return 用户角色关联PO列表
     */
    List<UserRolePO> toUserRolePOs(List<UserRoleEntity> userRoleEntities);
    
    /**
     * 将用户角色关联PO列表转换为用户角色实体列表
     *
     * @param userRolePOs 用户角色关联PO列表
     * @return 用户角色实体列表
     */
    @Named("toUserRoleEntities")
    List<UserRoleEntity> toUserRoleEntities(List<UserRolePO> userRolePOs);
    
    /**
     * 将用户角色关联PO转换为用户角色实体
     *
     * @param userRolePO 用户角色关联PO
     * @return 用户角色实体
     */
    UserRoleEntity toUserRoleEntity(UserRolePO userRolePO);
    
    /**
     * 将用户角色实体转换为用户角色关联PO
     *
     * @param userRoleEntity 用户角色实体
     * @return 用户角色关联PO
     */
    UserRolePO toUserRolePO(UserRoleEntity userRoleEntity);
    
    /**
     * 将状态码转换为用户状态值对象
     *
     * @param status 状态码
     * @return 用户状态值对象
     */
    @Named("toUserStatusVO")
    default UserStatusVO toUserStatusVO(Integer status) {
        if (status == null) {
            return UserStatusVO.NORMAL;
        }
        return status == 0 ? UserStatusVO.NORMAL : UserStatusVO.DISABLED;
    }
    
    /**
     * 将用户状态值对象转换为状态码
     *
     * @param userStatusVO 用户状态值对象
     * @return 状态码
     */
    @Named("fromUserStatusVO")
    default Integer fromUserStatusVO(UserStatusVO userStatusVO) {
        if (userStatusVO == null) {
            return 0;
        }
        return userStatusVO == UserStatusVO.NORMAL ? 0 : 1;
    }
} 