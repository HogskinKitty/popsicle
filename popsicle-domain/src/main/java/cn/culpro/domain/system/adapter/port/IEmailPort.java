package cn.culpro.domain.system.adapter.port;

/**
 * 电子邮件发送端口
 *
 * @author HogskinKitty
 * @date 2025/4/25
 */
public interface IEmailPort {
    
    /**
     * 发送简单文本邮件
     *
     * @param recipient 收件人邮箱
     * @param subject   邮件主题
     * @param content   邮件内容(文本)
     * @return 发送是否成功
     */
    boolean sendSimpleEmail(String recipient, String subject, String content);
    
    /**
     * 使用HTML模板发送邮件
     *
     * @param recipient    收件人邮箱
     * @param subject      邮件主题
     * @param templateName 模板名称
     * @param variables    模板变量
     * @return 发送是否成功
     */
    boolean sendTemplateEmail(String recipient, String subject, String templateName, Object variables);
    
    /**
     * 发送带附件的邮件
     *
     * @param recipient   收件人邮箱
     * @param subject     邮件主题
     * @param content     邮件内容
     * @param attachments 附件路径数组
     * @return 发送是否成功
     */
    boolean sendEmailWithAttachments(String recipient, String subject, String content, String[] attachments);
}
