package cn.culpro.types.exception;

import cn.culpro.types.enums.ResponseCode;
import lombok.Getter;

/**
 * 认证异常
 * <p>
 * 表示在认证过程中发生的业务异常
 *
 * @author HogskinKitty
 * @date 2024/10/31
 */
@Getter
public class AuthenticationException extends BusinessException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 创建认证异常
     *
     * @param responseCode 响应码
     */
    public AuthenticationException(ResponseCode responseCode) {
        super(responseCode);
    }
    
    /**
     * 创建认证异常
     *
     * @param responseCode 响应码
     * @param message      自定义信息
     */
    public AuthenticationException(ResponseCode responseCode, String message) {
        super(responseCode, message);
    }
    
    /**
     * 创建无效凭证异常
     *
     * @return 异常实例
     */
    public static AuthenticationException invalidCredentials() {
        return new AuthenticationException(ResponseCode.USERNAME_PASSWORD_ERROR);
    }
    
    /**
     * 创建无效令牌异常
     *
     * @return 异常实例
     */
    public static AuthenticationException invalidToken() {
        return new AuthenticationException(ResponseCode.TOKEN_INVALID);
    }
    
    /**
     * 创建令牌过期异常
     *
     * @return 异常实例
     */
    public static AuthenticationException expiredToken() {
        return new AuthenticationException(ResponseCode.TOKEN_EXPIRED);
    }
    
    /**
     * 创建未授权异常
     *
     * @return 异常实例
     */
    public static AuthenticationException unauthorized() {
        return new AuthenticationException(ResponseCode.UNAUTHORIZED);
    }
} 