package cn.culpro.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户持久化对象
 *
 * @author HogskinKitty
 * @date 2025/04/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPO {
    
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
     * 用户状态：0正常，1禁用
     */
    private Integer status;
    
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
} 