package cn.culpro.infrastructure.gateway.sms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信配置类
 *
 * @author HogskinKitty
 * @date 2025/4/27
 */
@Getter
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {
    
    /**
     * 默认短信提供商
     */
    @Setter
    private String defaultProvider = "aliyun";
    
    /**
     * 默认模板ID配置
     */
    private final TemplateConfig defaultTemplateId = new TemplateConfig();
    
    /**
     * 阿里云配置
     */
    private final AliyunConfig aliyun = new AliyunConfig();
    
    /**
     * 其他运营商配置
     */
    private final Map<String, ProviderConfig> providers = new HashMap<>();
    
    /**
     * 模板配置
     */
    @Setter
    @Getter
    public static class TemplateConfig {
        
        /**
         * 验证码短信模板ID
         */
        private String verification;
        
        /**
         * 通知短信模板ID
         */
        private String notification;
        
        /**
         * 营销短信模板ID
         */
        private String marketing;
        
    }
    
    /**
     * 阿里云配置
     */
    @Setter
    @Getter
    public static class AliyunConfig extends ProviderConfig {
        
        /**
         * 接入端点
         */
        private String endpoint = "dysmsapi.aliyuncs.com";
        
    }
    
    /**
     * 提供商通用配置
     */
    @Getter
    public static class ProviderConfig {
        
        /**
         * 访问密钥
         */
        @Setter
        private String accessKey;
        
        /**
         * 秘密密钥
         */
        @Setter
        private String secretKey;
        
        /**
         * 签名名称
         */
        @Setter
        private String signName;
        
        /**
         * 模板配置
         */
        private final TemplateConfig templateId = new TemplateConfig();
        
    }
} 