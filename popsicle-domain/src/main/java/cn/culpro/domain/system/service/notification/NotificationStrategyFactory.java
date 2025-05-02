package cn.culpro.domain.system.service.notification;

import cn.culpro.types.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通知策略工厂
 * <p>
 * 负责创建和管理不同类型的通知策略
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Component
@RequiredArgsConstructor
public class NotificationStrategyFactory {
    
    private final List<INotificationStrategy> notificationStrategies;
    
    private final Map<NotificationType, INotificationStrategy> strategyMap = new ConcurrentHashMap<>();
    
    /**
     * 初始化策略映射
     * <p>
     * 在构造后自动执行，将所有策略存入Map中以便快速查找
     */
    @PostConstruct
    public void init() {
        notificationStrategies.forEach(strategy -> {
            if (strategy != null) {
                strategyMap.put(strategy.getType(), strategy);
            }
        });
    }
    
    /**
     * 获取策略
     *
     * @param type 类型
     * @return 具体的通知策略
     */
    public INotificationStrategy getStrategy(NotificationType type) {
        return Optional.ofNullable(strategyMap.get(type)).orElseThrow(() -> new IllegalArgumentException("无效通知策略类型"));
    }
} 