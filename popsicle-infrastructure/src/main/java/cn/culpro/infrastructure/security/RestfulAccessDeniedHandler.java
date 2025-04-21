package cn.culpro.infrastructure.security;

import cn.culpro.types.model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义访问被拒绝处理器
 * <p>
 * 处理没有权限访问的情况，返回JSON格式的错误信息
 *
 * @author HogskinKitty
 * @date 2024/10/29
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    
    private final ObjectMapper objectMapper;
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        log.error("访问被拒绝：{}", e.getMessage());
        
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        ResponseDTO<Void> result = ResponseDTO.fail("403", "拒绝访问，权限不足");
        
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
} 