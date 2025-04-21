package cn.culpro.types.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用异常
 * <p>
 * 系统自定义异常基类，所有业务异常应继承此类，提供错误码和错误信息，用于统一异常处理
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException {
    
    private static final long serialVersionUID = 5317680961212299217L;
    
    /**
     * 异常码
     */
    private String code;
    
    /**
     * 异常信息
     */
    private String info;
    
    /**
     * 使用异常码构造异常
     *
     * @param code 异常码
     */
    public AppException(String code) {
        this.code = code;
    }
    
    /**
     * 使用异常码和原因构造异常
     *
     * @param code  异常码
     * @param cause 原因
     */
    public AppException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }
    
    /**
     * 使用异常码和消息构造异常
     *
     * @param code    异常码
     * @param message 异常消息
     */
    public AppException(String code, String message) {
        super(message);
        this.code = code;
        this.info = message;
    }
    
    /**
     * 使用异常码、消息和原因构造异常
     *
     * @param code    异常码
     * @param message 异常消息
     * @param cause   原因
     */
    public AppException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.info = message;
    }
    
    /**
     * 返回异常的字符串表示
     *
     * @return 异常的字符串表示
     */
    @Override
    public String toString() {
        return "cn.culpro.types.exception.AppException{code='" + code + '\'' + ", info='" + info + '\'' + '}';
    }
    
}
