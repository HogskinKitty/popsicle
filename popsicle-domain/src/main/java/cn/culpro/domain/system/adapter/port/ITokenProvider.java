package cn.culpro.domain.system.adapter.port;

/**
 * 令牌提供者接口
 * <p>
 * 定义令牌生成和验证功能，作为领域层的端口
 *
 * @author HogskinKitty
 * @date 2024/10/29
 */
public interface ITokenProvider {
    
    /**
     * 生成令牌
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return 令牌字符串
     */
    String generateToken(Long userId, String username);
    
    /**
     * 验证令牌有效性
     *
     * @param token 令牌字符串
     * @return 是否有效
     */
    boolean validateToken(String token);
    
    /**
     * 从令牌中获取用户ID
     *
     * @param token 令牌字符串
     * @return 用户ID
     */
    Long getUserIdFromToken(String token);
    
    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌字符串
     * @return 用户名
     */
    String getUsernameFromToken(String token);
} 