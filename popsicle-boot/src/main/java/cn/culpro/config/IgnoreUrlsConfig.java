package cn.culpro.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SpringSecurity 白名单资源路径配置
 *
 * @author HogskinKitty
 * @date 2024/10/29
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "security.ignore")
public class IgnoreUrlsConfig {
    
    private List<String> urls = new ArrayList<>();
}
