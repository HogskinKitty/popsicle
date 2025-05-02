package cn.culpro.domain.system.model.valobj;

import lombok.Getter;

/**
 * 用户状态值对象
 *
 * @author HogskinKitty
 * @date 2025/04/21
 */
@Getter
public enum UserStatusVO {
    /**
     * 正常状态
     */
    NORMAL(0, "正常"),
    
    /**
     * 禁用状态
     */
    DISABLED(1, "禁用");
    
    /**
     * 状态码
     */
    private final Integer code;
    
    /**
     * 状态描述
     */
    private final String description;
    
    UserStatusVO(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据状态码获取状态值对象
     *
     * @param code 状态码
     * @return 状态值对象
     */
    public static UserStatusVO fromCode(Integer code) {
        for (UserStatusVO status : UserStatusVO.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return NORMAL; // 默认返回正常状态
    }
} 