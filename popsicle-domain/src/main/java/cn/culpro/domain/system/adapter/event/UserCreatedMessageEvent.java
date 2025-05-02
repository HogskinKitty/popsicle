package cn.culpro.domain.system.adapter.event;

import cn.culpro.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 用户创建事件
 *
 * @author HogskinKitty
 * @date 2025/4/26
 */
@Component
public class UserCreatedMessageEvent extends BaseEvent<UserCreatedMessageEvent.UserCreatedMessage> {
    
    @Value("${spring.rabbitmq.topic.user_created}")
    private String topic;
    
    @Override
    public EventMessage<UserCreatedMessage> buildEventMessage(UserCreatedMessage data) {
        return EventMessage.<UserCreatedMessage>builder()
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(data)
                .build();
    }
    
    @Override
    public String topic() {
        return topic;
    }
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserCreatedMessage {
        
        /**
         * 用户ID
         */
        private Long userId;
        
        /**
         * 用户名
         */
        private String username;
        
        /**
         * 密码
         */
        private String password;
        
        /**
         * 手机号
         */
        private String phoneNumber;
        
        /**
         * 邮箱
         */
        private String email;
    }
}
