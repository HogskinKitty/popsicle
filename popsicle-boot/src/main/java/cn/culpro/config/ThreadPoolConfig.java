package cn.culpro.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置类
 * <p>
 * 配置系统线程池，支持异步任务执行
 *
 * @author HogskinKitty
 * @date 2025/04/19
 */
@Slf4j
@EnableAsync
@Configuration
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class ThreadPoolConfig {
    
    /**
     * 创建线程池执行器
     * <p>
     * 根据配置参数创建自定义线程池，支持不同的拒绝策略
     *
     * @param properties 线程池配置属性
     * @return 线程池执行器实例
     * @throws ClassNotFoundException 找不到类异常
     * @throws InstantiationException 实例化异常
     * @throws IllegalAccessException 非法访问异常
     */
    @Bean
    @ConditionalOnMissingBean(ThreadPoolExecutor.class)
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties properties)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 实例化策略
        RejectedExecutionHandler handler;
        switch (properties.getPolicy()) {
            case "AbortPolicy":
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
            case "DiscardPolicy":
                handler = new ThreadPoolExecutor.DiscardPolicy();
                break;
            case "DiscardOldestPolicy":
                handler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            case "CallerRunsPolicy":
                handler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            default:
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
        }
        // 创建线程池
        return new ThreadPoolExecutor(properties.getCorePoolSize(), properties.getMaxPoolSize(), properties.getKeepAliveTime(),
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(properties.getBlockQueueSize()), Executors.defaultThreadFactory(),
                handler);
    }
    
}
