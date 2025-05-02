package cn.culpro.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 响应状态码枚举
 * <p>
 * 定义系统中使用的所有响应状态码
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {
    
    // 成功相关状态码 (0xxx)
    SUCCESS("0000", "操作成功"),
    
    // 客户端错误 (1xxx)
    CLIENT_ERROR("1000", "客户端错误"),
    ILLEGAL_PARAMETER("1001", "非法参数"),
    MISSING_PARAMETER("1002", "缺少必要参数"),
    INVALID_REQUEST("1003", "无效的请求"),
    UNAUTHORIZED("1100", "未授权访问"),
    TOKEN_EXPIRED("1101", "令牌已过期"),
    TOKEN_INVALID("1102", "无效的令牌"),
    PERMISSION_DENIED("1103", "权限不足"),
    RESOURCE_NOT_FOUND("1200", "请求的资源不存在"),
    DUPLICATE_RESOURCE("1201", "资源已存在"),
    
    // 服务器错误 (2xxx)
    SERVER_ERROR("2000", "服务器内部错误"),
    SERVICE_UNAVAILABLE("2001", "服务不可用"),
    DATABASE_ERROR("2100", "数据库操作错误"),
    
    // 业务逻辑错误 (3xxx)
    BUSINESS_ERROR("3000", "业务逻辑错误"),
    OPERATION_FAILED("3001", "操作失败"),
    VALIDATION_ERROR("3002", "数据校验失败"),
    DATA_CONFLICT("3003", "数据冲突"),
    
    // 认证相关错误 (4xxx)
    AUTH_ERROR("4000", "认证错误"),
    USERNAME_PASSWORD_ERROR("4001", "用户名或密码错误"),
    ACCOUNT_LOCKED("4002", "账号已锁定"),
    ACCOUNT_DISABLED("4003", "账号已禁用"),
    CAPTCHA_ERROR("4004", "验证码错误"),
    
    // 系统管理相关错误 (5xxx)
    SYSTEM_ERROR("5000", "系统管理错误"),
    USER_EXISTS("5001", "用户已存在"),
    ROLE_EXISTS("5002", "角色已存在"),
    MENU_EXISTS("5003", "菜单已存在"),
    ROLE_IN_USE("5004", "角色正在使用中，无法删除"),
    MENU_IN_USE("5005", "菜单正在使用中，无法删除"),
    PARENT_MENU_NOT_EXISTS("5006", "父级菜单不存在"),
    PHONE_NUMBER_EXISTS("5007", "手机号已存在"),
    
    // 未知错误
    UNKNOWN_ERROR("9999", "未知错误");
    
    private String code;
    
    private String info;
}
