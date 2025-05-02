package cn.culpro.test;

import cn.culpro.domain.system.adapter.port.IEmailPort;
import cn.culpro.domain.system.adapter.port.ISmsPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {
    
    @Resource
    private IEmailPort emailPort;
    
    @Resource
    private ISmsPort smsPort;
    
    @Test
    public void test_sendSimpleEmail() {
        emailPort.sendSimpleEmail("18011090588@163.com", "测试邮件", "测试邮件内容");
    }
    
    @Test
    public void test_sendTemplateEmail() {
        HashMap<String, String> variables = new HashMap<>();
        variables.put("username", "zym");
        variables.put("tempPassword", "123456");
        emailPort.sendTemplateEmail("18011090588@163.com", "测试邮件", "init-password-mail", variables);
    }
    
    @Test
    public void test_sendEmailWithAttachments() {
        emailPort.sendEmailWithAttachments("18011090588@163.com", "测试邮件", "测试邮件内容",
                new String[] {"/Users/zym/Desktop/test.txt"});
    }
    
    @Test
    public void test_sendSms() {
        boolean success = smsPort.sendNotification("19130610587", "123456", "SMS_154950909");
        
        log.info("发送短信结果：{}", success);
    }
}
