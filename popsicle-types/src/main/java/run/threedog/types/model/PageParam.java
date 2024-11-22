package run.threedog.types.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 分页参数
 *
 * @author HogskinKitty
 * @date 2024/10/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageParam {
    
    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    private Long pageNum;
    
    /**
     * 每页数量
     */
    
    @NotNull(message = "每页数量不能为空")
    @Max(value = 500, message = "每页数量最大为500")
    private Long pageSize;
}
