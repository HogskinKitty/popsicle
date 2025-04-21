package cn.culpro.types.handler;

import cn.culpro.types.enums.ResponseCode;
import cn.culpro.types.exception.BusinessException;
import cn.culpro.types.model.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * <p>
 * 统一处理系统中的异常，转换为标准响应格式
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 处理业务异常
     *
     * @param e 业务异常
     * @return 统一响应
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return ResponseDTO.<Void>builder().code(e.getCode()).info(e.getInfo()).build();
    }
    
    /**
     * 处理参数校验异常 (JSR-303 注解校验)
     *
     * @param e 参数校验异常
     * @return 统一响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        log.error("参数校验失败: {}", errorMessage);
        return ResponseDTO.fail(ResponseCode.ILLEGAL_PARAMETER, errorMessage);
    }
    
    /**
     * 处理参数绑定异常
     *
     * @param e 参数绑定异常
     * @return 统一响应
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Void> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        log.error("参数绑定失败: {}", errorMessage);
        return ResponseDTO.fail(ResponseCode.ILLEGAL_PARAMETER, errorMessage);
    }
    
    /**
     * 处理约束违反异常
     *
     * @param e 约束违反异常
     * @return 统一响应
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Void> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String errorMessage = violations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        
        log.error("约束违反异常: {}", errorMessage);
        return ResponseDTO.fail(ResponseCode.ILLEGAL_PARAMETER, errorMessage);
    }
    
    /**
     * 处理缺少请求参数异常
     *
     * @param e 缺少请求参数异常
     * @return 统一响应
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少必要参数: {}", e.getMessage());
        return ResponseDTO.fail(ResponseCode.MISSING_PARAMETER, "缺少必要参数: " + e.getParameterName());
    }
    
    /**
     * 处理其他未知异常
     *
     * @param e 未知异常
     * @return 统一响应
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return ResponseDTO.fail(ResponseCode.SERVER_ERROR);
    }
} 