package cn.culpro.infrastructure.gateway;

import java.util.Map;

/**
 * 短信服务提供商接口
 *
 * @author HogskinKitty
 * @date 2025/4/27
 */
public interface ISmsProvider {
    
    /**
     * 获取提供商名称
     *
     * @return 提供商名称
     */
    String getProviderName();
    
    /**
     * 发送短信
     *
     * @param phoneNumber 手机号码
     * @param templateId  短信模板ID
     * @param params      模板参数
     * @return 发送是否成功
     */
    boolean sendSms(String phoneNumber, String templateId, Map<String, String> params);
    
    /**
     * 批量发送短信
     *
     * @param phoneNumbers 手机号码列表
     * @param templateId   短信模板ID
     * @param params       模板参数
     * @return 成功发送的手机号码列表
     */
    String[] batchSendSms(String[] phoneNumbers, String templateId, Map<String, String> params);
} 