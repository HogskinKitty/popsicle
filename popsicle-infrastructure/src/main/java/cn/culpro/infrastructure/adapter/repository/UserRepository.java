package cn.culpro.infrastructure.adapter.repository;

import cn.culpro.domain.system.adapter.repository.IUserRepository;
import cn.culpro.domain.system.model.aggregate.UserAggregate;
import cn.culpro.domain.system.model.entity.UserRoleEntity;
import cn.culpro.infrastructure.dao.IUserDao;
import cn.culpro.infrastructure.dao.po.UserPO;
import cn.culpro.infrastructure.dao.po.UserRolePO;
import cn.culpro.infrastructure.dao.po.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户仓储实现
 * <p>
 * 实现领域层定义的用户仓储接口，负责用户聚合根的持久化和查询操作
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {
    
    private final IUserDao userDao;
    
    @Override
    public Long save(UserAggregate userAggregate) {
        // 转换为PO
        UserPO userPO = UserConverter.INSTANCE.toUserPO(userAggregate);
        
        // 保存用户
        if (userPO.getUserId() == null) {
            userDao.insert(userPO);
        } else {
            userDao.update(userPO);
            // 删除旧的角色关联
            userDao.deleteUserRoleByUserId(userPO.getUserId());
        }
        
        // 保存用户角色关系
        List<UserRoleEntity> userRoles = userAggregate.getUserRoles();
        if (userRoles != null && !userRoles.isEmpty()) {
            userRoles.forEach(userRole -> {
                UserRolePO userRolePO = UserRolePO.builder().userId(userPO.getUserId()).roleId(userRole.getRoleId()).build();
                userDao.insertUserRole(userRolePO);
            });
        }
        
        return userPO.getUserId();
    }
    
    @Override
    public Optional<UserAggregate> findById(Long userId) {
        // 查询用户
        return userDao.selectById(userId).map(userPO -> {
            // 查询用户角色关系
            List<UserRolePO> userRolePOs = userDao.selectUserRoleByUserId(userId);
            
            // 转换为领域对象
            return UserConverter.INSTANCE.toUserAggregate(userPO, userRolePOs);
        });
    }
    
    @Override
    public Optional<UserAggregate> findByUsername(String username) {
        // 查询用户
        return userDao.selectByUsername(username).map(userPO -> {
            // 查询用户角色关系
            List<UserRolePO> userRolePOs = userDao.selectUserRoleByUserId(userPO.getUserId());
            
            // 转换为领域对象
            return UserConverter.INSTANCE.toUserAggregate(userPO, userRolePOs);
        });
    }
    
    @Override
    public List<UserAggregate> findAll() {
        // 查询所有用户
        List<UserPO> userPOs = userDao.selectAll();
        
        // 转换为领域对象
        List<UserAggregate> userAggregates = new ArrayList<>();
        for (UserPO userPO : userPOs) {
            // 查询用户角色关系
            List<UserRolePO> userRolePOs = userDao.selectUserRoleByUserId(userPO.getUserId());
            
            // 转换为领域对象
            UserAggregate userAggregate = UserConverter.INSTANCE.toUserAggregate(userPO, userRolePOs);
            
            userAggregates.add(userAggregate);
        }
        
        return userAggregates;
    }
    
    @Override
    public List<UserAggregate> findByPage(int pageNum, int pageSize) {
        // 计算偏移量
        int offset = (pageNum - 1) * pageSize;
        
        // 查询分页用户
        List<UserPO> userPOs = userDao.selectByPage(offset, pageSize);
        
        // 转换为领域对象
        List<UserAggregate> userAggregates = new ArrayList<>();
        for (UserPO userPO : userPOs) {
            // 查询用户角色关系
            List<UserRolePO> userRolePOs = userDao.selectUserRoleByUserId(userPO.getUserId());
            
            // 转换为领域对象
            UserAggregate userAggregate = UserConverter.INSTANCE.toUserAggregate(userPO, userRolePOs);
            
            userAggregates.add(userAggregate);
        }
        
        return userAggregates;
    }
    
    @Override
    public List<UserAggregate> findByRoleId(Long roleId) {
        // 查询角色下的所有用户
        List<UserPO> userPOs = userDao.selectByRoleId(roleId);
        
        // 转换为领域对象
        List<UserAggregate> userAggregates = new ArrayList<>();
        for (UserPO userPO : userPOs) {
            // 查询用户角色关系
            List<UserRolePO> userRolePOs = userDao.selectUserRoleByUserId(userPO.getUserId());
            
            // 转换为领域对象
            UserAggregate userAggregate = UserConverter.INSTANCE.toUserAggregate(userPO, userRolePOs);
            
            userAggregates.add(userAggregate);
        }
        
        return userAggregates;
    }
} 