
# Spring Boot 中的定时任务详解：实现灵活可靠的定时器

在实际开发中，我们经常需要处理一些周期性或定时执行的任务，例如数据备份、报表生成、系统清理等。Spring Boot 提供了一种简单易用的方式来实现这些定时任务，它基于强大的 Quartz 或者 Spring 自带的 @Scheduled 注解来完成定时调度功能。本文将详细阐述如何在 Spring Boot 应用中设置和使用定时器。

## 1. 引入依赖

首先，在 Spring Boot 项目中引入 spring-boot-starter-scheduling 依赖，这使得我们可以直接使用 Spring 的定时任务功能：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <!-- Spring Boot 默认已经包含了 scheduling 功能 -->
</dependency>
```
如果你希望使用更加强大的 Quartz 定时器，可以添加 Quartz 相关的依赖：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-quartz</artifactId>
</dependency>
```

## 2. 配置定时任务

### 2.1 使用 @EnableScheduling 启用定时任务支持

在应用启动类上添加 @EnableScheduling 注解，以启用定时任务的全局支持：
```java
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 2.2 创建定时任务方法

在任何 Spring 管理的 Bean 类中，可以通过 @Scheduled 注解标记需要定时执行的方法：
```java
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTaskScheduler {

    // 每隔5秒执行一次
    @Scheduled(fixedRate = 5000)
    public void executeTask() {
        System.out.println("定时任务被执行 - " + new Date());
        // 在这里编写你的业务逻辑代码
    }

    // 在每天凌晨0点执行一次
    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyTask() {
        System.out.println("每日定时任务被执行 - " + new Date());
    }
}
```
解释：
+ fixedRate 参数指定了方法执行的固定频率（单位为毫秒），上面的例子表示每隔5秒执行一次。
+ cron 参数用于设置 Cron 表达式，这是一种强大且灵活的定时表达方式，可以精确地定义任务的执行时间间隔。

## 3. Cron 表达式详解

Cron 表达式是一个字符串，由7个子表达式组成，分别代表秒、分钟、小时、日、月、周和年（Spring Boot 中通常不包含年份字段）。各个字段之间用空格分隔，格式如下：

例如，`0 0 0 * * ?` 指定在每天的0点（即凌晨）执行任务。

## 4. 异步执行定时任务

默认情况下，@Scheduled 注解修饰的方法会在同一个线程中同步执行。如果想要异步执行定时任务以避免阻塞其他任务，可以在配置类中开启异步调度：
```java
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableScheduling
@EnableAsync
public class SchedulingConfig {

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
```
然后在定时任务类上添加 @Async 注解：
```java
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTaskScheduler {

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
    public void reportCurrentTime() {
        log.info("cron: The current time is {}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }
}
```

通过上述步骤，您就可以在 Spring Boot 应用中轻松创建并管理各种定时任务了。根据具体需求选择合适的调度策略，并合理配置线程池以确保系统的稳定性和效率。对于复杂的定时任务场景，还可以考虑结合 Quartz 进行更加精细化的管理和控制。

完整示例代码参考：[TimerConfig.java](./src/main/java/com/github/weijunfu/timer/config/TimerConfig.java)
