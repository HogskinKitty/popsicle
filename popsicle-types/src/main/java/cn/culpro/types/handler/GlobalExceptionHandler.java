package cn.culpro.types.handler;

import cn.culpro.types.enums.ResponseCode;
import cn.culpro.types.exception.AppException;
import cn.culpro.types.model.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author HogskinKitty
 * @date 2024/10/04
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 获取当前请求 URL
     */
    private String getCurrentRequestUrl() {
        RequestAttributes request = RequestContextHolder.getRequestAttributes();
        if (null == request) {
            return null;
        }
        ServletRequestAttributes servletRequest = (ServletRequestAttributes) request;
        return servletRequest.getRequest().getRequestURI();
    }
    
    /**
     * 处理全局的异常
     *
     * @param e e 全局异常
     * @return {@link ResponseDTO }<{@link ? }>
     */
    @ExceptionHandler(Exception.class)
    public ResponseDTO<String> handleException(Exception e) {
        log.error("捕获全局异常,URL:{}", getCurrentRequestUrl(), e);
        return ResponseDTO.<String>builder().code(ResponseCode.UN_ERROR.getCode()).info(ResponseCode.UN_ERROR.getInfo()).build();
    }
    
    /**
     * 处理自定义应用程序异常
     *
     * @param e e 自定义应用程序异常
     * @return {@link ResponseDTO }<{@link String }>
     */
    @ExceptionHandler(AppException.class)
    public ResponseDTO<String> handleAppException(AppException e) {
        log.error("捕获应用程序异常,URL:{}", getCurrentRequestUrl(), e);
        return ResponseDTO.<String>builder().code(e.getCode()).info(e.getInfo()).build();
    }
    
    /**
     * 处理参数校验异常
     *
     * @param e 方法参数校验异常
     * @return {@link ResponseDTO }<{@link Map }<{@link String }, {@link String }>>具体的错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> infoMap = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        
        return ResponseDTO.<Map<String, String>>builder()
                .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                .data(infoMap)
                .build();
    }
}
