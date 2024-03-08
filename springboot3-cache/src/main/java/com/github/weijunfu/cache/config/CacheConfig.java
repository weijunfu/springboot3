package com.github.weijunfu.cache.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/8 14:29
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class CacheConfig {

    private final CacheKeyGenerator cacheKeyGenerator;

    @Bean
    public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer(){
        return (cacheManager) -> cacheManager.setAllowNullValues(Boolean.TRUE);     // 允许缓存null值
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return cacheKeyGenerator;
    }
}
