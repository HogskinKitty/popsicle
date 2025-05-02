package cn.culpro.domain.system.service.auth;

import cn.culpro.domain.system.model.aggregate.UserAggregate;

/**
 * 用户认证领域服务接口
 * <p>
 * 定义用户认证、JWT令牌的生成、验证和刷新等功能
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
public interface IAuthService {
    
    /**
     * 验证用户名和密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 认证成功返回用户聚合根，认证失败返回null
     */
    UserAggregate authenticate(String username, String password);
    
    /**
     * 生成用户令牌
     *
     * @param userAggregate 用户聚合根
     * @return 令牌字符串
     */
    String generateToken(UserAggregate userAggregate);
    
    /**
     * 验证令牌有效性
     *
     * @param token 令牌字符串
     * @return 验证成功返回用户ID，验证失败返回null
     */
    Long validateToken(String token);
    
    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    String refreshToken(String token);
    
    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌字符串
     * @return 用户名，令牌无效时返回null
     */
    String getUsernameFromToken(String token);
} 