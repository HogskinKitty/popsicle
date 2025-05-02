package cn.culpro.domain.system.service.notification.strategy;

import cn.culpro.domain.system.adapter.port.IEmailPort;
import cn.culpro.domain.system.service.notification.INotificationStrategy;
import cn.culpro.types.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 邮件通知策略实现
 * <p>
 * 基于EmailPort实现发送邮件功能
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationStrategy implements INotificationStrategy {
    
    private final IEmailPort emailPort;
    
    @Override
    public void send(String recipient, String subject, String content) {
        boolean success = emailPort.sendSimpleEmail(recipient, subject, content);
        if (success) {
            log.info("通过策略发送邮件成功，收件人: {}", recipient);
        } else {
            log.error("通过策略发送邮件失败，收件人: {}", recipient);
        }
    }
    
    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }
} 