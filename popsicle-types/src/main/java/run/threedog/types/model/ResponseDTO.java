package run.threedog.types.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.threedog.types.enums.ResponseCode;

import java.io.Serializable;

/**
 * 统一结果响应对象
 *
 * @author HogskinKitty
 * @date 2024/10/02
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
    
    public static <T> ResponseDTO<T> success() {
        return ResponseDTO.<T>builder().code(ResponseCode.SUCCESS.getCode()).info(ResponseCode.SUCCESS.getInfo()).build();
    }
    
    public static <T> ResponseDTO<T> success(T data) {
        return ResponseDTO.<T>builder().code(ResponseCode.SUCCESS.getCode()).info(ResponseCode.SUCCESS.getInfo()).data(data).build();
    }
}
