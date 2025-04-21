package cn.culpro.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * <p>
 * 配置MyBatis相关的扫描路径和其他配置
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Configuration
@MapperScan(basePackages = {"cn.culpro.infrastructure.dao"})
public class MyBatisConfig {
    // 可以在这里添加额外的配置，如分页插件等
} 