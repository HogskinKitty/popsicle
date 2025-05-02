package cn.culpro.domain.system.service.notification.strategy;

import cn.culpro.domain.system.adapter.port.ISmsPort;
import cn.culpro.domain.system.service.notification.INotificationStrategy;
import cn.culpro.types.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 短信通知策略实现
 * <p>
 * 短信发送功能实现
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SmsNotificationStrategy implements INotificationStrategy {
    
    private final ISmsPort smsPort;
    
    @Override
    public void send(String recipient, String subject, String content) {
        log.info("发送短信到 {}: {}", recipient, content);
        smsPort.sendNotification(recipient, content, null);
    }
    
    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }
} 