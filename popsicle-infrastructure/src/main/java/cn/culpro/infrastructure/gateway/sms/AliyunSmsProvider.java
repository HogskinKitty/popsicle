package cn.culpro.infrastructure.gateway.sms;

import cn.culpro.infrastructure.gateway.ISmsProvider;
import com.aliyun.credentials.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 阿里云短信服务提供商实现
 *
 * @author HogskinKitty
 * @date 2025/4/27
 */
@Slf4j
@Component
public class AliyunSmsProvider implements ISmsProvider {
    
    @Value("${sms.aliyun.access-key:}")
    private String accessKey;
    
    @Value("${sms.aliyun.secret-key:}")
    private String secretKey;
    
    @Value("${sms.aliyun.sign-name:}")
    private String signName;
    
    @Value("${sms.aliyun.endpoint:dysmsapi.aliyuncs.com}")
    private String endpoint;
    
    @Override
    public String getProviderName() {
        return "aliyun";
    }
    
    @Override
    public boolean sendSms(String phoneNumber, String templateId, Map<String, String> params) {
        try {
            log.info("阿里云短信 - 发送短信, 手机号: {}, 模板ID: {}, 参数: {}", phoneNumber, templateId, params);
            //            Config config = new Config()
            //                    // 您的AccessKey ID
            //                    .setAccessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"))
            //                    // 您的AccessKey Secret
            //                    .setAccessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
            
            Client credential = new Client();
            Config config = new Config().setCredential(credential);
            
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";
            
            com.aliyun.dysmsapi20170525.Client client = new com.aliyun.dysmsapi20170525.Client(config);
            
            SendSmsRequest request = new SendSmsRequest().setPhoneNumbers(phoneNumber)
                    .setSignName(signName)
                    .setTemplateCode(templateId)
                    .setTemplateParam(new Gson().toJson(params));
            
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
            
            SendSmsResponse response = client.sendSmsWithOptions(request, runtime);
            return "OK".equals(response.getBody().getCode());
        } catch (Exception e) {
            log.error("阿里云短信 - 发送短信失败, 手机号: {}, 错误: {}", phoneNumber, e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public String[] batchSendSms(String[] phoneNumbers, String templateId, Map<String, String> params) {
        List<String> successList = new ArrayList<>();
        
        for (String phoneNumber : phoneNumbers) {
            if (sendSms(phoneNumber, templateId, params)) {
                successList.add(phoneNumber);
            }
        }
        
        return successList.toArray(new String[0]);
    }
} 