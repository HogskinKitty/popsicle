package cn.culpro.infrastructure.adapter.port;

import cn.culpro.domain.system.adapter.port.IEmailPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * 电子邮件端口实现
 *
 * @author HogskinKitty
 * @date 2025/4/25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailPort implements IEmailPort {
    
    @Resource
    private final JavaMailSender mailSender;
    
    private final TemplateEngine templateEngine;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Override
    public boolean sendSimpleEmail(String recipient, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(recipient);
            message.setSubject(subject);
            message.setText(content);
            
            mailSender.send(message);
            log.info("简单邮件发送成功，收件人: {}", recipient);
            return true;
        } catch (Exception e) {
            log.error("简单邮件发送失败，收件人: {}, 错误: {}", recipient, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean sendTemplateEmail(String recipient, String subject, String templateName, Object variables) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(recipient);
            helper.setSubject(subject);
            
            // 设置模板变量
            Context context = new Context();
            if (variables instanceof Map) {
                Map<String, Object> variableMap = (Map<String, Object>) variables;
                variableMap.forEach(context::setVariable);
            }
            
            // 处理模板
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            
            mailSender.send(mimeMessage);
            log.info("模板邮件发送成功，收件人: {}, 模板: {}", recipient, templateName);
            return true;
        } catch (Exception e) {
            log.error("模板邮件发送失败，收件人: {}, 模板: {}, 错误: {}", recipient, templateName, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean sendEmailWithAttachments(String recipient, String subject, String content, String[] attachments) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(fromEmail);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content, false);
            
            // 添加附件
            if (attachments != null && attachments.length > 0) {
                for (String attachmentPath : attachments) {
                    File file = new File(attachmentPath);
                    if (file.exists()) {
                        FileSystemResource resource = new FileSystemResource(file);
                        helper.addAttachment(file.getName(), resource);
                        log.info("添加附件: {}", file.getName());
                    } else {
                        log.warn("附件不存在: {}", attachmentPath);
                    }
                }
            }
            
            mailSender.send(mimeMessage);
            log.info("带附件邮件发送成功，收件人: {}", recipient);
            return true;
        } catch (MessagingException e) {
            log.error("带附件邮件发送失败，收件人: {}, 错误: {}", recipient, e.getMessage(), e);
            return false;
        }
    }
}
