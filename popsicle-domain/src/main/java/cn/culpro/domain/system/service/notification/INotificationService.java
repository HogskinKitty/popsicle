package cn.culpro.domain.system.service.notification;

import cn.culpro.types.enums.NotificationType;

/**
 * 通知服务接口
 * <p>
 * 定义发送各类通知的方法，内部使用策略模式实现不同通知渠道
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
public interface INotificationService {
    
    /**
     * 发送指定类型的通知
     *
     * @param type      通知类型
     * @param recipient 接收人
     * @param subject   主题（可选，某些通知类型不需要）
     * @param content   内容
     */
    void sendNotification(NotificationType type, String recipient, String subject, String content);
}