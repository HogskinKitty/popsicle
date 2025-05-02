package cn.culpro.trigger.http.dto.user.converter;

import cn.culpro.application.user.command.CreateUserCommand;
import cn.culpro.domain.system.model.aggregate.UserAggregate;
import cn.culpro.trigger.http.dto.user.UserRequest;
import cn.culpro.trigger.http.dto.user.UserResponse;
import org.springframework.stereotype.Component;

/**
 * 用户DTO转换器
 * <p>
 * 负责DTO与领域对象之间的转换
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Component
public class UserRequestResponseConverter {
    
    /**
     * 将请求DTO转换为创建用户命令
     *
     * @param request 用户请求DTO
     * @return 创建用户命令
     */
    public CreateUserCommand toCreateUserCommand(UserRequest request) {
        return CreateUserCommand.builder()
                .username(request.getUsername())
                .realName(request.getRealName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
    
    /**
     * 将用户聚合转换为响应DTO
     *
     * @param aggregate 用户聚合
     * @return 用户响应DTO
     */
    public UserResponse toUserResponse(UserAggregate aggregate) {
        if (aggregate == null) {
            return null;
        }
        
        return UserResponse.builder()
                .id(aggregate.getId())
                .username(aggregate.getUsername())
                .name(aggregate.getRealName())
                .email(aggregate.getEmail())
                .phone(aggregate.getPhoneNumber())
                .createdTime(aggregate.getCreateTime())
                .build();
    }
} 