package cn.culpro.config;

import cn.culpro.infrastructure.security.JwtAuthenticationFilter;
import cn.culpro.infrastructure.security.RestAuthenticationEntryPoint;
import cn.culpro.infrastructure.security.RestfulAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Security 配置
 *
 * @author HogskinKitty
 * @date 2024/10/27
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Resource
    private JwtAuthenticationFilter jwtAuthFilter;
    
    @Resource
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    
    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    
    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;
    
    /**
     * 认证管理器
     * <p>
     * 用于处理认证请求
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    /**
     * 安全过滤链
     * <p>
     * 配置Spring Security过滤器链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        
        // 不需要保护的资源路径允许访问
        for (String url : ignoreUrlsConfig.getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        
        // 允许跨域请求的 OPTIONS 请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();
        
        // 任何请求都需要身份认证
        registry.anyRequest()
                .authenticated()
                
                // 关闭跨站请求防护及不使用 Session
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                
                // 自定义权限拒绝处理类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                
                // 自定义权限拦截器 JWT 过滤器
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return httpSecurity.build();
    }
    
    /**
     * 密码编码器
     * <p>
     * 使用BCrypt算法实现密码的加密和验证
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}