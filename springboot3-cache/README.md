
# 基于SpringBoot详解缓存机制及其应用

## 1.引言
在现代Web应用程序开发中，优化数据访问性能是提升系统响应速度和用户体验的关键。缓存技术作为一项重要策略，能够显著减少对数据库的直接访问，从而提高系统的整体性能。本文将深入探讨Spring Boot框架中的缓存机制，并展示如何有效地在实际项目中应用缓存。

## 2.理解SpringBoot缓存原理

Spring Framework提供了强大的缓存支持，基于注解的抽象层允许开发者轻松地为方法级别的结果进行缓存。在Spring Boot中，这一特性被进一步简化和增强，通过自动配置和扩展性极高的缓存抽象，可以灵活地集成多种缓存解决方案，如Redis、EHCache等。

### 2.1 引入依赖
```xml
<dependencies>
    <!-- Web应用依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- 缓存依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>

    <!-- lombok依赖 -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- 测试依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### 2.2 启用缓存
```java
@EnableCaching  // 开启缓存
@Configuration(proxyBeanMethods = false)
public class CacheConfig {

    @Bean
    public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer(){
        return (cacheManager) -> cacheManager.setAllowNullValues(Boolean.FALSE);    // 不缓存空值
    }
}
```

### 2.3 使用缓存

```java
public class AuthorServiceImpl implements AuthorService {

    static Map<String, Author> db = new HashMap<>();

    static {
        db.put("1", new Author("1", "ijunfu"));
        db.put("2", new Author("2", "wei"));
    }

    @Override
    @Cacheable(value = "author", key ="'author:all'" )
    public List<Author> getAllAuthor() {
        log.info("query db");
        return db.values().stream().toList();
    }
}
```

### 2.4 使用自定义KeyGenerator

#### 2.4.1 自定义KeyGenerator
```java
@Component
public class CacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        // 这里可以根据实际需求生成自定义的key，比如将方法名、参数等组合起来
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(target.getClass().getCanonicalName());
        keyBuilder.append(method.getName());
        for (Object param : params) {
            if (param != null) {
                keyBuilder.append("-").append(param.toString());
            }
        }
        return keyBuilder.toString();
    }
}
```

#### 2.4.2 配置自定义KeyGenerator
```java
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
```

#### 2.4.3 使用自定义KeyGenerator
```java
public class AuthorServiceImpl implements AuthorService {

    static Map<String, Author> db = new HashMap<>();

    static {
        db.put("1", new Author("1", "ijunfu"));
        db.put("2", new Author("2", "wei"));
    }

    @Override
    @Cacheable(value = "author", keyGenerator ="cacheKeyGenerator" )
    public List<Author> getAllAuthor() {
        log.info("query db");
        return db.values().stream().toList();
    }
}
```