package com.github.weijunfu.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.weijunfu.common.Contents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/9 9:39
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
@Slf4j
@EnableCaching
@Configuration(enforceUniqueMethods = false)
@RequiredArgsConstructor
public class CacheConfiguration {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        Caffeine<Object, Object> caffeineBuilder = Caffeine.newBuilder()
                .expireAfterAccess(Contents.CACHE_EXPIRE_DURATION)  // 设置全局默认过期时间
                .maximumSize(100_000)
                .removalListener((key, value, cause) -> {
                    log.warn("key:{},value:{},cause:{}", key, value, cause);
                });

        CaffeineCache caffeineCache = new CaffeineCache(Contents.CACHE_LOCAL_NAME, caffeineBuilder.build());

        cacheManager.setCaches(Arrays.asList(caffeineCache));

        return cacheManager;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheConfiguration redisConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Contents.CACHE_EXPIRE_DURATION) // 设置全局默认过期时间
                .computePrefixWith((cacheName -> Contents.CACHE_REDIS_PREFIX + cacheName))      // 设置Redis Key前缀
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));


        return RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration(Contents.CACHE_REDIS_NAME, redisConfig)
                .build();
    }
}
