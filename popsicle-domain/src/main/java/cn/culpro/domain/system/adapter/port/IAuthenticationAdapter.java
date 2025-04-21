package cn.culpro.domain.system.adapter.port;

import cn.culpro.domain.system.model.aggregate.UserAggregate;

/**
 * 身份认证适配器接口
 * <p>
 * 定义身份认证和上下文设置功能，作为领域层的端口
 *
 * @author HogskinKitty
 * @date 2024/10/29
 */
public interface IAuthenticationAdapter {
    
    /**
     * 验证用户凭证并设置认证上下文
     *
     * @param username 用户名
     * @param password 密码
     * @return 认证成功的用户，认证失败返回null
     */
    UserAggregate authenticateAndSetContext(String username, String password);
} 