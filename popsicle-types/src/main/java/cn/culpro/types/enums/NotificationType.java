package cn.culpro.types.enums;

/**
 * 通知类型枚举
 *
 * @author HogskinKitty
 * @date 2025/04/25
 */
public enum NotificationType {
    /**
     * 邮件通知
     */
    EMAIL,
    
    /**
     * 短信通知
     */
    SMS,
    
    /**
     * 应用内通知
     */
    APP_PUSH,
    
    /**
     * 微信通知
     */
    WECHAT;
}