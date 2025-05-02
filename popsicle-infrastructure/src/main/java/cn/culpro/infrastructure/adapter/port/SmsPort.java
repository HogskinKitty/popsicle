package cn.culpro.infrastructure.adapter.port;

import cn.culpro.domain.system.adapter.port.ISmsPort;
import cn.culpro.infrastructure.gateway.sms.SmsProviderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信端口实现
 *
 * @author HogskinKitty
 * @date 2025/4/26
 */
@Slf4j
@Component
public class SmsPort implements ISmsPort {
    
    private final SmsProviderFactory smsProviderFactory;
    
    @Value("${sms.default-template-id.verification:}")
    private String defaultVerificationTemplateId;
    
    @Value("${sms.default-template-id.notification:}")
    private String defaultNotificationTemplateId;
    
    @Value("${sms.default-template-id.marketing:}")
    private String defaultMarketingTemplateId;
    
    public SmsPort(SmsProviderFactory smsProviderFactory) {
        this.smsProviderFactory = smsProviderFactory;
    }
    
    @Override
    public boolean sendVerificationCode(String phoneNumber, String code, String templateId, int expireMinutes) {
        try {
            // 使用默认模板ID（如果未提供）
            String actualTemplateId = templateId != null && !templateId.isEmpty() ? templateId : defaultVerificationTemplateId;
            
            // 构造短信参数
            Map<String, String> params = new HashMap<>();
            params.put("code", code);
            params.put("expire", String.valueOf(expireMinutes));
            
            // 使用默认提供商发送短信
            boolean success = smsProviderFactory.getDefaultProvider().sendSms(phoneNumber, actualTemplateId, params);
            
            if (success) {
                // 将验证码存入Redis，设置过期时间
//                smsCodeRedisRepository.saveCode(phoneNumber, code, expireMinutes);
                log.info("短信验证码发送成功，手机号: {}", phoneNumber);
            }
            
            return success;
        } catch (Exception e) {
            log.error("短信验证码发送失败，手机号: {}, 错误: {}", phoneNumber, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean sendNotification(String phoneNumber, String content, String templateId) {
        try {
            // 使用默认模板ID（如果未提供）
            String actualTemplateId = templateId != null && !templateId.isEmpty() ? templateId : defaultNotificationTemplateId;
            
            // 构造参数
            Map<String, String> params = new HashMap<>();
            params.put("content", content);
            
            // 使用默认提供商发送短信
            boolean success = smsProviderFactory.getDefaultProvider().sendSms(phoneNumber, actualTemplateId, params);
            
            if (success) {
                log.info("通知短信发送成功，手机号: {}", phoneNumber);
            }
            
            return success;
        } catch (Exception e) {
            log.error("通知短信发送失败，手机号: {}, 错误: {}", phoneNumber, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public String[] sendMarketingMessage(String[] phoneNumbers, String content, String templateId) {
        try {
            // 使用默认模板ID（如果未提供）
            String actualTemplateId = templateId != null && !templateId.isEmpty() ? templateId : defaultMarketingTemplateId;
            
            // 构造参数
            Map<String, String> params = new HashMap<>();
            params.put("content", content);
            
            // 使用默认提供商批量发送短信
            return smsProviderFactory.getDefaultProvider().batchSendSms(phoneNumbers, actualTemplateId, params);
        } catch (Exception e) {
            log.error("批量营销短信发送失败，错误: {}", e.getMessage(), e);
            return new String[0];
        }
    }
    
    @Override
    public boolean verifyCode(String phoneNumber, String code) {
        return false;
    }
    
    //    @Override
//    public boolean verifyCode(String phoneNumber, String code) {
//        try {
//            // 使用Redis验证并删除验证码
////            return smsCodeRedisRepository.verifyAndDelete(phoneNumber, code);
//        } catch (Exception e) {
//            log.error("短信验证码验证异常，手机号: {}, 错误: {}", phoneNumber, e.getMessage(), e);
//            return false;
//        }
//    }
}