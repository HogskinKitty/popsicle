package run.threedog.types.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 基础事件
 *
 * @author HogskinKitty
 * @date 2024/10/02
 */
@Data
public abstract class BaseEvent<T> {
    
    public abstract EventMessage<T> buildEventMessage(T data);
    
    public abstract String topic();
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventMessage<T> {
        
        private String id;
        
        private Date timestamp;
        
        private T data;
    }
    
}
