package cn.culpro.domain.system.service.auth;

import cn.culpro.domain.system.adapter.port.IAuthenticationAdapter;
import cn.culpro.domain.system.adapter.port.ITokenProvider;
import cn.culpro.domain.system.model.aggregate.UserAggregate;
import cn.culpro.types.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户认证领域服务实现
 * <p>
 * 提供用户认证、JWT令牌的生成、验证和刷新等功能
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    
    private final ITokenProvider tokenProvider;
    
    private final IAuthenticationAdapter authenticationAdapter;
    
    @Override
    public UserAggregate authenticate(String username, String password) {
        // 使用认证适配器接口进行认证并设置上下文
        UserAggregate user = authenticationAdapter.authenticateAndSetContext(username, password);
        
        if (user == null) {
            throw AuthenticationException.invalidCredentials();
        }
        
        return user;
    }
    
    @Override
    public String generateToken(UserAggregate userAggregate) {
        return tokenProvider.generateToken(userAggregate.getUserId(), userAggregate.getUsername());
    }
    
    @Override
    public Long validateToken(String token) {
        if (!tokenProvider.validateToken(token)) {
            throw AuthenticationException.invalidToken();
        }
        
        return tokenProvider.getUserIdFromToken(token);
    }
    
    @Override
    public String refreshToken(String token) {
        if (!tokenProvider.validateToken(token)) {
            throw AuthenticationException.invalidToken();
        }
        
        Long userId = tokenProvider.getUserIdFromToken(token);
        String username = tokenProvider.getUsernameFromToken(token);
        
        return tokenProvider.generateToken(userId, username);
    }
    
    @Override
    public String getUsernameFromToken(String token) {
        if (!tokenProvider.validateToken(token)) {
            throw AuthenticationException.invalidToken();
        }
        
        return tokenProvider.getUsernameFromToken(token);
    }
} 