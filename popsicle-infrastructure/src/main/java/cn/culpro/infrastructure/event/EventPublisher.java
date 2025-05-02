package cn.culpro.infrastructure.event;

import cn.culpro.domain.shared.adapter.port.IEventPublisher;
import cn.culpro.types.event.BaseEvent;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 事件发布实现
 *
 * @author HogskinKitty
 * @date 2025/04/25
 */
@Slf4j
@Component
public class EventPublisher implements IEventPublisher {
    
    @Resource
    private RabbitTemplate rabbitTemplate;
    
    @Override
    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage) {
        try {
            String messageJson = JSON.toJSONString(eventMessage);
            rabbitTemplate.convertAndSend(topic, messageJson);
            log.info("发送MQ消息 topic:{} message:{}", topic, messageJson);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(eventMessage), e);
            throw e;
        }
    }
    
    @Override
    public void publish(String topic, String eventMessageJSON) {
        try {
            rabbitTemplate.convertAndSend(topic, eventMessageJSON);
            log.info("发送MQ消息 topic:{} message:{}", topic, eventMessageJSON);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, eventMessageJSON, e);
            throw e;
        }
    }
    
}
