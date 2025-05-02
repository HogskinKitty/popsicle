package cn.culpro.infrastructure.gateway.sms;

import cn.culpro.infrastructure.gateway.ISmsProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信提供商工厂类
 *
 * @author HogskinKitty
 * @date 2025/4/27
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SmsProviderFactory {
    
    private final List<ISmsProvider> smsProviders;
    
    @Value("${sms.default-provider:aliyun}")
    private String defaultProvider;
    
    private final Map<String, ISmsProvider> providerMap = new HashMap<>();
    
    @PostConstruct
    public void init() {
        for (ISmsProvider provider : smsProviders) {
            providerMap.put(provider.getProviderName(), provider);
            log.info("注册短信提供商: {}", provider.getProviderName());
        }
        
        if (providerMap.isEmpty()) {
            log.warn("没有可用的短信提供商");
        } else {
            log.info("短信提供商注册完成，默认提供商: {}", defaultProvider);
        }
    }
    
    /**
     * 获取短信提供商
     *
     * @param providerName 提供商名称
     * @return 短信提供商
     */
    public ISmsProvider getProvider(String providerName) {
        ISmsProvider provider = providerMap.get(providerName);
        if (provider == null) {
            log.warn("未找到指定的短信提供商: {}，将使用默认提供商: {}", providerName, defaultProvider);
            provider = providerMap.get(defaultProvider);
            
            if (provider == null) {
                log.error("默认短信提供商: {} 不存在", defaultProvider);
                if (!providerMap.isEmpty()) {
                    // 如果有其他提供商，返回第一个
                    String firstProvider = providerMap.keySet().iterator().next();
                    log.warn("将使用第一个可用的短信提供商: {}", firstProvider);
                    return providerMap.get(firstProvider);
                }
            }
        }
        return provider;
    }
    
    /**
     * 获取默认短信提供商
     *
     * @return 默认短信提供商
     */
    public ISmsProvider getDefaultProvider() {
        return getProvider(defaultProvider);
    }
} 