package com.github.weijunfu.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.weijunfu.utils.Contents;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/8 22:17
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        // 创建一个名为"caffeineCache"的Caffeine缓存实例，并设置过期时间为5分钟
        Caffeine<Object, Object> caffeineBuilder = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(100); // 可选，设置最大缓存容量

        CaffeineCache usersCache = new CaffeineCache(Contents.CACHE_NAME, caffeineBuilder.build());

        // 添加多个缓存实例，根据实际需求配置不同的缓存参数
        cacheManager.setCaches(Arrays.asList(usersCache));

        return cacheManager;
    }

}
