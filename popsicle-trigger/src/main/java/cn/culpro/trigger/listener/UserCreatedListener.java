package cn.culpro.trigger.listener;

import cn.culpro.domain.system.adapter.event.UserCreatedMessageEvent;
import cn.culpro.domain.system.adapter.port.IEmailPort;
import cn.culpro.domain.system.adapter.port.ISmsPort;
import cn.culpro.types.event.BaseEvent;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 用户创建监听器
 *
 * @author HogskinKitty
 * @date 2025/4/28
 */
@Slf4j
@Component
public class UserCreatedListener {
    
    @Value("${spring.rabbitmq.topic.user_created}")
    private String topic;
    
    @Resource
    private IEmailPort emailPort;
    
    @Resource
    private ISmsPort smsPort;
    
    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.user_created}"))
    public void listener(String message) {
        try {
            log.info("监听用户已创建消息 topic: {} message: {}", topic, message);
            // 转换对象
            BaseEvent.EventMessage<UserCreatedMessageEvent.UserCreatedMessage> eventMessage = JSON.parseObject(message,
                    new TypeReference<BaseEvent.EventMessage<UserCreatedMessageEvent.UserCreatedMessage>>() {
                    }.getType());
            UserCreatedMessageEvent.UserCreatedMessage userCreatedMessage = eventMessage.getData();
            
            // 发送邮件通知
            log.info("用户已创建完成，发送初始化密码邮件给用户 {}，邮箱{}", userCreatedMessage.getUsername(),
                    userCreatedMessage.getEmail());
            HashMap<String, String> variables = new HashMap<>();
            variables.put("username", userCreatedMessage.getUsername());
            variables.put("password", userCreatedMessage.getPassword());
            emailPort.sendTemplateEmail(userCreatedMessage.getEmail(), "账户初始密码", "user-created-mail", variables);
            
            // 发送短信通知
            log.info("用户已创建完成，发送初始化密码短信给用户 {}，手机号{}", userCreatedMessage.getUsername(),
                    userCreatedMessage.getPhoneNumber());
            smsPort.sendVerificationCode(userCreatedMessage.getPhoneNumber(), "101323", null, 5);
            
        } catch (Exception e) {
            log.error("监听用户已创建消息，消费失败 topic: {} message: {}", topic, message);
            throw e;
        }
    }
}
