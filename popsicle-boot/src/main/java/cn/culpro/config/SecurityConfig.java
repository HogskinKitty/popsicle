package cn.culpro.config;

import cn.culpro.infrastructure.security.JwtAuthenticationFilter;
import cn.culpro.infrastructure.security.RestAuthenticationEntryPoint;
import cn.culpro.infrastructure.security.RestfulAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
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
        // 获取配置的白名单URL
        String[] ignoreUrlArray = ignoreUrlsConfig.getUrls().toArray(new String[0]);
        
        return httpSecurity
                // 基于JWT，不需要csrf保护
                .csrf(AbstractHttpConfigurer::disable)
                // 基于token，不需要session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 未登录和未授权处理
                .exceptionHandling(handling -> handling.accessDeniedHandler(restfulAccessDeniedHandler)
                        .authenticationEntryPoint(restAuthenticationEntryPoint))
                // 请求授权配置
                .authorizeRequests(authorize -> authorize
                        // 白名单放行
                        .antMatchers(ignoreUrlArray).permitAll()
                        // 允许跨域请求的OPTIONS
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
                        // 其余所有请求全部需要鉴权认证
                        .anyRequest().authenticated())
                // 添加JWT过滤器
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
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