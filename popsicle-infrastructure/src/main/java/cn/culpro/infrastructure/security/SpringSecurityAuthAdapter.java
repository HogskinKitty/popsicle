package cn.culpro.infrastructure.security;

import cn.culpro.domain.system.adapter.port.IAuthenticationAdapter;
import cn.culpro.domain.system.adapter.repository.IUserRepository;
import cn.culpro.domain.system.model.aggregate.UserAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Spring Security认证适配器实现
 * <p>
 * 使用Spring Security进行认证并设置安全上下文
 *
 * @author HogskinKitty
 * @date 2024/10/29
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpringSecurityAuthAdapter implements IAuthenticationAdapter {
    
    private final AuthenticationManager authenticationManager;
    
    private final IUserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserAggregate authenticateAndSetContext(String username, String password) {
        try {
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            
            // 执行认证
            Authentication authentication = authenticationManager.authenticate(authToken);
            
            // 设置认证上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 直接从仓储获取用户信息
            Optional<UserAggregate> userOpt = userRepository.findByUsername(username);
            if (!userOpt.isPresent()) {
                log.warn("用户认证成功但在仓储中未找到: {}", username);
                return null;
            }
            
            UserAggregate user = userOpt.get();
            
            // 验证密码
            if (!passwordEncoder.matches(password, user.getPassword())) {
                log.warn("密码不匹配: {}", username);
                return null;
            }
            
            return user;
        } catch (Exception e) {
            log.error("认证失败", e);
            return null;
        }
    }
} 