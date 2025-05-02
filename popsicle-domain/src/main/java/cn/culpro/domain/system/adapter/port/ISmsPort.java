package cn.culpro.domain.system.adapter.port;

/**
 * 短信端口
 *
 * @author HogskinKitty
 * @date 2025/4/26
 */
public interface ISmsPort {
    
    /**
     * 发送验证码短信
     *
     * @param phoneNumber   手机号码
     * @param code          验证码
     * @param templateId    短信模板ID
     * @param expireMinutes 验证码有效期（分钟）
     * @return 发送是否成功
     */
    boolean sendVerificationCode(String phoneNumber, String code, String templateId, int expireMinutes);
    
    /**
     * 发送通知短信
     *
     * @param phoneNumber 手机号码
     * @param content     短信内容
     * @param templateId  短信模板ID
     * @return 发送是否成功
     */
    boolean sendNotification(String phoneNumber, String content, String templateId);
    
    /**
     * 发送营销短信
     *
     * @param phoneNumbers 手机号码列表
     * @param content      短信内容
     * @param templateId   短信模板ID
     * @return 成功发送的手机号码列表
     */
    String[] sendMarketingMessage(String[] phoneNumbers, String content, String templateId);
    
    /**
     * 验证短信验证码
     *
     * @param phoneNumber 手机号码
     * @param code        验证码
     * @return 验证是否成功
     */
    boolean verifyCode(String phoneNumber, String code);
}
