package cn.culpro.types.exception;

import cn.culpro.types.enums.ResponseCode;

/**
 * 业务异常
 * <p>
 * 用于包装业务逻辑异常，关联响应码
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
public class BusinessException extends AppException {
    
    private static final long serialVersionUID = 6358553459456002745L;
    
    /**
     * 使用响应码构造异常
     *
     * @param responseCode 响应码
     */
    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getCode(), responseCode.getInfo());
    }
    
    /**
     * 使用响应码和自定义信息构造异常
     *
     * @param responseCode 响应码
     * @param message      自定义信息
     */
    public BusinessException(ResponseCode responseCode, String message) {
        super(responseCode.getCode(), message);
    }
    
    /**
     * 使用自定义错误码和信息构造异常
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public BusinessException(String code, String message) {
        super(code, message);
    }
    
    /**
     * 使用已有异常和响应码构造异常
     *
     * @param cause        原始异常
     * @param responseCode 响应码
     */
    public BusinessException(Throwable cause, ResponseCode responseCode) {
        super(responseCode.getCode(), responseCode.getInfo(), cause);
    }
    
    /**
     * 使用已有异常、响应码和自定义信息构造异常
     *
     * @param cause        原始异常
     * @param responseCode 响应码
     * @param message      自定义信息
     */
    public BusinessException(Throwable cause, ResponseCode responseCode, String message) {
        super(responseCode.getCode(), message, cause);
    }
} 