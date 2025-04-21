package cn.culpro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;

/**
 * Web安全Bean配置
 * <p>
 * 配置Spring Security相关的Bean
 *
 * @author HogskinKitty
 * @date 2024/10/30
 */
@Configuration
public class WebSecurityBeanConfig {
    
    /**
     * 方法安全表达式处理器
     * <p>
     * 处理方法级别的安全表达式
     */
    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setDefaultRolePrefix(""); // 不使用"ROLE_"前缀
        return expressionHandler;
    }
} 