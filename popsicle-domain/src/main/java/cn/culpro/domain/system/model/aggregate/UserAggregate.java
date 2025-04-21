package cn.culpro.domain.system.model.aggregate;

import cn.culpro.domain.system.model.entity.UserRoleEntity;
import cn.culpro.domain.system.model.valobj.UserStatusVO;
import cn.culpro.types.model.Aggregate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户聚合根
 * <p>
 * 负责维护用户的生命周期和一致性，是外部访问用户相关数据的唯一入口
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Getter
@Builder
public class UserAggregate implements Aggregate<Long> {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码（加密后）
     */
    private String password;
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 性别：1男，2女，3未知
     */
    private Integer gender;
    
    /**
     * 头像地址
     */
    private String avatar;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机号码
     */
    private String phoneNumber;
    
    /**
     * 用户状态
     */
    private UserStatusVO status;
    
    /**
     * 删除状态：0否，1是
     */
    private Integer deleteStatus;
    
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
    
    /**
     * 用户关联的角色列表
     */
    private List<UserRoleEntity> userRoles;
    
    /**
     * 创建新用户
     *
     * @param username 用户名
     * @param password 加密后的密码
     * @param realName 真实姓名
     * @return 用户聚合根
     */
    public static UserAggregate create(String username, String password, String realName) {
        return UserAggregate.builder().username(username).password(password).realName(realName).gender(3) // 默认未知
                .status(UserStatusVO.NORMAL) // 默认正常状态
                .deleteStatus(0) // 默认未删除
                .createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).userRoles(new ArrayList<>()).build();
    }
    
    /**
     * 修改用户基本信息
     *
     * @param realName    真实姓名
     * @param gender      性别
     * @param email       邮箱
     * @param phoneNumber 手机号码
     */
    public void updateBasicInfo(String realName, Integer gender, String email, String phoneNumber) {
        this.realName = realName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 修改用户密码
     *
     * @param newPassword 新密码（加密后）
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 禁用用户
     */
    public void disable() {
        this.status = UserStatusVO.DISABLED;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 启用用户
     */
    public void enable() {
        this.status = UserStatusVO.NORMAL;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 标记用户为已删除
     */
    public void markAsDeleted() {
        this.deleteStatus = 1;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 为用户分配角色
     *
     * @param roleId 角色ID
     */
    public void assignRole(Long roleId) {
        // 检查是否已分配该角色
        boolean exists = userRoles.stream().anyMatch(userRole -> Objects.equals(userRole.getRoleId(), roleId));
        
        if (!exists) {
            UserRoleEntity userRole = UserRoleEntity.create(this.userId, roleId);
            userRoles.add(userRole);
        }
    }
    
    /**
     * 移除用户角色
     *
     * @param roleId 角色ID
     */
    public void removeRole(Long roleId) {
        userRoles.removeIf(userRole -> Objects.equals(userRole.getRoleId(), roleId));
    }
    
    /**
     * 清空所有角色
     */
    public void clearRoles() {
        userRoles.clear();
    }
    
    /**
     * 获取聚合ID
     *
     * @return 用户ID
     */
    @Override
    public Long getId() {
        return this.userId;
    }
} 