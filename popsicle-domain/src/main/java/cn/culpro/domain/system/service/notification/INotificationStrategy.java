package cn.culpro.domain.system.service.notification;

import cn.culpro.types.enums.NotificationType;

/**
 * 通知策略接口
 * <p>
 * 定义通知发送策略，遵循策略模式
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
public interface INotificationStrategy {
    
    /**
     * 发送通知
     *
     * @param recipient 接收人信息（手机号或邮箱等）
     * @param subject   通知主题（可选，某些通知方式不需要）
     * @param content   通知内容
     */
    void send(String recipient, String subject, String content);
    
    /**
     * 获取策略类型
     *
     * @return 通知策略类型
     */
    NotificationType getType();
} 