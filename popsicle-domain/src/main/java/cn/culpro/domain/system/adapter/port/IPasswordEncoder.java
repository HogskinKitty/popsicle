package cn.culpro.domain.system.adapter.port;

/**
 * 密码编码器接口
 * <p>
 * 定义密码加密和验证功能，作为领域层的端口
 *
 * @author HogskinKitty
 * @date 2024/10/29
 */
public interface IPasswordEncoder {
    
    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    String encode(String rawPassword);
    
    /**
     * 验证密码
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean matches(String rawPassword, String encodedPassword);
} 