package cn.culpro.domain.system.service.notification;

import cn.culpro.types.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 通知服务实现
 * <p>
 * 基于策略模式和工厂模式实现通知服务
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {
    
    private final NotificationStrategyFactory strategyFactory;
    
    @Override
    public void sendNotification(NotificationType type, String recipient, String subject, String content) {
        INotificationStrategy strategy = strategyFactory.getStrategy(type);
        if (strategy == null) {
            log.error("未找到通知策略类型: {}", type);
            return;
        }
        strategy.send(recipient, subject, content);
    }
} 