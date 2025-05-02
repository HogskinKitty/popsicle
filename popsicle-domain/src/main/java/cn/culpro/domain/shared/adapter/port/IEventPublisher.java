package cn.culpro.domain.shared.adapter.port;

import cn.culpro.types.event.BaseEvent;

/**
 * 事件发布接口
 *
 * @author HogskinKitty
 * @date 2025/4/27
 */
public interface IEventPublisher {
    
    void publish(String topic, BaseEvent.EventMessage<?> eventMessage);
    
    void publish(String topic, String eventMessageJSON);
}
