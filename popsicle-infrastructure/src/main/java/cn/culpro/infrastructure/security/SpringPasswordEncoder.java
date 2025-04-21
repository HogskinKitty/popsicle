package cn.culpro.infrastructure.security;

import cn.culpro.domain.system.adapter.port.IPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Spring密码编码器实现
 * <p>
 * 使用Spring Security的BCryptPasswordEncoder实现密码加密和验证
 *
 * @author HogskinKitty
 * @date 2024/10/29
 */
@Component
public class SpringPasswordEncoder implements IPasswordEncoder {
    
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
} 