package com.github.weijunfu.timer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/8 10:29
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
@Slf4j
@Configuration
@EnableScheduling
@EnableAsync
public class TimerConfig {


    /**
     * @Title  : 固定频率：每5秒打印下当前时间
     * @Param	:
     * @Return : void
     * @Author : ijunfu <ijunfu@163.com>
     * @Date   : 2024/3/8 10:38
     * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("rate: The current time is {}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }

    /**
     * @Title  : cron：每5秒打印下当前时间
     * @Param	:
     * @Return : void
     * @Author : ijunfu <ijunfu@163.com>
     * @Date   : 2024/3/8 10:38
     * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    @Async
    @Scheduled(cron = "0/5 * * * * ?")
    public void reportCurrentTime2() {
        log.info("cron: The current time is {}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        int cores = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(cores+1);      // 核心线程数
        taskExecutor.setMaxPoolSize(cores << 1);    // 最大线程数
        taskExecutor.setQueueCapacity(100);        // 队列容量
        taskExecutor.setThreadNamePrefix("task_");  // 线程名前缀
        taskExecutor.initialize();
        return taskExecutor;
    }
}
