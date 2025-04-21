package cn.culpro;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用程序启动类
 * <p>
 * 系统入口，负责启动Spring Boot应用
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@SpringBootApplication
@Configurable
public class Application {
    
    /**
     * 应用程序主方法
     * <p>
     * 启动Spring Boot应用
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
    
}
