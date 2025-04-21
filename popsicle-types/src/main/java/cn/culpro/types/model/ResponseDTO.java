package cn.culpro.types.model;

import cn.culpro.types.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应DTO
 * <p>
 * 用于封装所有HTTP接口响应的标准格式
 *
 * @param <T> 响应数据类型
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> implements Serializable {
    
    private static final long serialVersionUID = 7000723935764546321L;
    
    /**
     * 响应码
     */
    private String code;
    
    /**
     * 响应信息
     */
    private String info;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 构建成功响应
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 响应对象
     */
    public static <T> ResponseDTO<T> success(T data) {
        return ResponseDTO.<T>builder().code(ResponseCode.SUCCESS.getCode()).info(ResponseCode.SUCCESS.getInfo()).data(data).build();
    }
    
    /**
     * 构建成功响应（无数据）
     *
     * @param <T> 数据类型
     * @return 响应对象
     */
    public static <T> ResponseDTO<T> success() {
        return ResponseDTO.<T>builder().code(ResponseCode.SUCCESS.getCode()).info(ResponseCode.SUCCESS.getInfo()).build();
    }
    
    /**
     * 构建失败响应
     *
     * @param message 错误信息
     * @param <T>     数据类型
     * @return 响应对象
     */
    public static <T> ResponseDTO<T> fail(String message) {
        return ResponseDTO.<T>builder().code(ResponseCode.BUSINESS_ERROR.getCode()).info(message).build();
    }
    
    /**
     * 构建失败响应
     *
     * @param code    错误码
     * @param message 错误信息
     * @param <T>     数据类型
     * @return 响应对象
     */
    public static <T> ResponseDTO<T> fail(String code, String message) {
        return ResponseDTO.<T>builder().code(code).info(message).build();
    }
    
    /**
     * 使用指定的响应码构建失败响应
     *
     * @param responseCode 响应码枚举
     * @param <T>          数据类型
     * @return 响应对象
     */
    public static <T> ResponseDTO<T> fail(ResponseCode responseCode) {
        return ResponseDTO.<T>builder().code(responseCode.getCode()).info(responseCode.getInfo()).build();
    }
    
    /**
     * 使用自定义信息覆盖枚举中的默认信息
     *
     * @param responseCode 响应码枚举
     * @param message      自定义错误信息
     * @param <T>          数据类型
     * @return 响应对象
     */
    public static <T> ResponseDTO<T> fail(ResponseCode responseCode, String message) {
        return ResponseDTO.<T>builder().code(responseCode.getCode()).info(message).build();
    }
    
    /**
     * 使用指定的响应码和数据构建响应
     *
     * @param responseCode 响应码枚举
     * @param data         响应数据
     * @param <T>          数据类型
     * @return 响应对象
     */
    public static <T> ResponseDTO<T> of(ResponseCode responseCode, T data) {
        return ResponseDTO.<T>builder().code(responseCode.getCode()).info(responseCode.getInfo()).data(data).build();
    }
    
    /**
     * 判断响应是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return ResponseCode.SUCCESS.getCode().equals(this.code);
    }
}